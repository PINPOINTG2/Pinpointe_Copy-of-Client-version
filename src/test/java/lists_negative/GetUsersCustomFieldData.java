package lists_negative;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.InputSource;

import common_base.BaseClass;
import common_utilities.TestData;
import common_utilities.Utilities;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lists_common_utilities.RequestDataforGetUsersCustomFieldData;
import validation.GetUsersCustomFieldDataValidation;

public class GetUsersCustomFieldData extends BaseClass {

	String methodName = "Get User's Custom Field Data";
	String responseCode = "";
	String responseTime = "";
	String api_version ="";
	String status = "";
	String expected, actual;
	String subs_count ="";
	String errormessage ="";
	HashMap<String, String> responseDataparsed = null;
	String expectedErrorMessage = "";

	GetUsersCustomFieldDataValidation validation = new GetUsersCustomFieldDataValidation();

	TestData data = new TestData();	
	Utilities utils = new Utilities();
	RequestDataforGetUsersCustomFieldData request_xml = new RequestDataforGetUsersCustomFieldData();

	@Test(testName = "Get List subscribers", dataProviderClass=TestData.class, dataProvider="getLists_Negative_GetUsersCustomField")
	public void GetUsersCustomFieldData(String[] body) {
		String[] tdata = new String[4];
		try {
			tdata[0] = body[0];
			tdata[1] = body[1];
			tdata[2] = body[2];
			tdata[3] = body[3];
		}
		catch(IndexOutOfBoundsException e) {

			tdata[3] = "";
		}


		logger = extent.createTest(methodName + " - Negative - " + tdata[0]);

		System.out.println("processing "+ methodName +" record - "+ tdata[0]);

		api_version = utils.getproperty("config", "api_version");
		String request_para = request_xml.requestdata(tdata);	

		RestAssured.baseURI=uri;
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/xml");
		httpRequest.body(request_para);
		Response response = httpRequest.request(Method.POST);
		String responsedata = response.getBody().asString();	


		responseCode = Integer.toString(response.getStatusCode());
		responseTime = Long.toString(response.getTimeIn(TimeUnit.MILLISECONDS)) + "ms";
		reportDetails = reportDetails + "METHOD NAME: " + methodName;
		reportDetails = reportDetails + "\nRESPONSE CODE: " + responseCode;
		reportDetails = reportDetails + "\nRESPONSE TIME: " + responseTime;
		

		HashMap<String, String> _errormessage = new HashMap<String, String>();
		boolean api_status = validation.parameter_Validation(request_para, _errormessage);
		expectedErrorMessage = _errormessage.get("errorMessage");

		HashMap<String, String> responseelement = parseResponse(response);
		status = responseelement.get("status");
		reportDetails = reportDetails + "\nRESPONSE -> Status: " + status;
		String version = responseelement.get("version");
		reportDetails = reportDetails + "\nRESPONSE -> Version: " + version;
		String elapsed = responseelement.get("elapsed");
		reportDetails = reportDetails + "\nRESPONSE -> Elapsed: " + elapsed;

		if(!status.toLowerCase().trim().equals("success")) {

			String errordetail = responseelement.get("errordetail");
			reportDetails = reportDetails + "\nRESPONSE -> Error Detail: " + errordetail;
			errormessage = responseelement.get("errormessage");
			reportDetails = reportDetails + "\nRESPONSE -> Error Message: " + errormessage;
		}
		else {
			reportDetails = reportDetails + "\n\nTest Case Status:\n===========================================\nSuccessfully fetched the Customer's Field.\n\n";
			String responseParsedData = responseelement.get("responsedata");
			reportDetails = reportDetails + "\nRESPONSE -> Data: " + responseParsedData;
		}


		if(!api_status) {
			String validationstatus = "failed";
			reportDetails = reportDetails + "\nExpected Error Message: " + expectedErrorMessage;
			reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(request_para);
			reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responsedata);	
			Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status in response are not in SUCCESS");
			Assert.assertTrue(errormessage.toLowerCase().trim().equals(expectedErrorMessage.toLowerCase().trim()), "Expected error message are not appeared.\n");
		}
		else {
			String validationstatus = "success";

			reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(request_para);
			reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responsedata);
			Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status in response are not in SUCCESS");
			Assert.assertTrue(version.toLowerCase().trim().equals("3.0"), "Version in response are not in SUCCESS");		

		}
	}

	public HashMap<String, String> parseResponse(Response response) {
		HashMap<String, String> responseelement = new HashMap<String, String>();
		
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = null;
		try {
			document = saxBuilder.build(new InputSource(new StringReader(response.getBody().asString())));
		} catch (JDOMException | IOException e1) {
			e1.printStackTrace();
		}
		Element classElement = document.getRootElement();

		Element ele_status = classElement.getChild("status");
		status = ele_status.getText().toString();
		responseelement.put("status", status);
		
		try {

			
			
			if(status.toLowerCase().trim().contains("success") ) {

				Element ele_data = classElement.getChild("data");
				Element ele_items = ele_data.getChild("item");
				List<Element> ele_item = ele_items.getChildren("item");
				
				String responsedata = "";
				if(ele_item.size() >0) {
					for (Element item : ele_item) {

						Element ele_fieldid = item.getChild("fieldid");
						Element ele_fieldname = item.getChild("fieldname");
						Element ele_fieldtype = item.getChild("fieldtype");
						Element ele_fieldsettings = item.getChild("fieldsettings");
						Element ele_subscriberid = item.getChild("subscriberid");
						Element ele_item_data = item.getChild("data");

						String fieldid = ele_fieldid.getText().toString();
						String fieldname = ele_fieldname.getText().toString();
						String fieldtype = ele_fieldtype.getText().toString();
						String fieldsettings = ele_fieldsettings.getText().toString();
						String subscriberid = ele_subscriberid.getText().toString();
						String item_data = ele_item_data.getText().toString();

						responsedata = responsedata + "\nField Id: " + fieldid;
						responsedata = responsedata + "\nField Name: " + fieldname;
						responsedata = responsedata + "\nField Type: " + fieldtype;
						responsedata = responsedata + "\nField Settings: " + fieldsettings;
						responsedata = responsedata + "\nSubscriber Id: " + subscriberid;
						responsedata = responsedata + "\nData: " + item_data;
						responsedata = responsedata + "\n";
						
					}
				}
				
				
				responseelement.put("responsedata", responsedata);

				
			}
			else {
				Element ele_errormessage = classElement.getChild("errormessage");
				Element ele_errordetail = classElement.getChild("errordetail");

				String errormessage = ele_errormessage.getText().toString();
				String errordetail = ele_errordetail.getText().toString();

				responseelement.put("errormessage", errormessage);
				responseelement.put("errordetail", errordetail);
				
			}
			
			
			
		}
		catch(Exception e) {
			responseelement.put("responsedata", "");
			

		}
		
		Element ele_version = classElement.getChild("version");
		Element ele_elapsed = classElement.getChild("elapsed");
		
		String version = ele_version.getText().toString();
		String elapsed = ele_elapsed.getText().toString();
		

		responseelement.put("version", version);
		responseelement.put("elapsed", elapsed);
		
		return responseelement;
	}

}
