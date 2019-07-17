package lists_negative;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jdom2.Document;
import org.jdom2.Element;
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
import lists_common_utilities.RequestDataforGetCustomFieldStructure;
import validation.GetCustomFieldStructureValidation;

public class GetCustomFieldStructure extends BaseClass {

	String methodName = "Get Custom Field Structure";
	String responseCode = "";
	String responseTime = "";
	String api_version ="";
	String status = "";
	String expected, actual;
	String subs_count ="";
	String errormessage ="";
	HashMap<String, String> responseDataparsed = null;
	String expectedErrorMessage = "";

	TestData data = new TestData();	
	Utilities utils = new Utilities();
	RequestDataforGetCustomFieldStructure request_xml = new RequestDataforGetCustomFieldStructure();
	GetCustomFieldStructureValidation validation = new GetCustomFieldStructureValidation(); 
	
	
	@Test(testName = "Get Custom Field Structure", dataProviderClass=TestData.class, dataProvider="getLists_Negative_GetCustomFieldStructure")
	public void GetCustomFieldStructure(String[] body) {


		String[] tdata = new String[3];
		try {
			tdata[0] = body[0];
			tdata[1] = body[1];
		}
		catch(IndexOutOfBoundsException e) {

			tdata[1] = "";
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

		try {

			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(new InputSource(new StringReader(response.getBody().asString())));
			Element classElement = document.getRootElement();

			Element ele_status = classElement.getChild("status");
			status = ele_status.getText().toString();
			if(status.toLowerCase().trim().contains("success") ) {

				Element ele_data = classElement.getChild("data");
				List<Element> ele_items = ele_data.getChildren("item");		

				String items = "";

				for (Element item : ele_items) {
					Element ele_fieldid = item.getChild("fieldid");
					Element ele_name = item.getChild("name");
					Element ele_required = item.getChild("required");

					String fieldid = ele_fieldid.getText().toString();
					String name = ele_name.getText().toString();
					String required = ele_required.getText().toString();
					
					if(required.trim().contains("0")) {
						required = "(Is Required: FALSE)";
					}
					else {
						required = "(Is Required: FALSE)";
					}

					items = items + "\n" + fieldid + "-" + name + required;
				}


				responseelement.put("items", items);

			}
			else {
				Element ele_errormessage = classElement.getChild("errormessage");
				Element ele_errordetail = classElement.getChild("errordetail");

				String errormessage = ele_errormessage.getText().toString();
				String errordetail = ele_errordetail.getText().toString();

				responseelement.put("errormessage", errormessage);
				responseelement.put("errordetail", errordetail);

			}
			responseelement.put("status", status);

			Element ele_version = classElement.getChild("version");
			Element ele_elapsed = classElement.getChild("elapsed");

			String version = ele_version.getText().toString();
			String elapsed = ele_elapsed.getText().toString();


			responseelement.put("version", version);
			responseelement.put("elapsed", elapsed);

		}
		catch(Exception e) {

		}

		return responseelement;
	}

}
