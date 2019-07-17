package lists_negative;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.InputSource;

import common_base.BaseClass;
import common_utilities.ResponseGeneral;
import common_utilities.TestData;
import common_utilities.Utilities;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lists_common_utilities.RequestDataforFindList;
import validation.FindListValidation;

public class FindList extends BaseClass {

	String methodName = "Find List";
	String responseCode = "";
	String responseTime = "";
	String api_version ="";
	String status = "";
	String expected, actual;
	String subs_count ="";
	String errormessage ="";

	String expectedErrorMessage = "";
	HashMap<String, String> responseDataparsed = null;

	TestData data = new TestData();	
	Utilities utils = new Utilities();
	RequestDataforFindList request_xml = new RequestDataforFindList();
	FindListValidation validation = new FindListValidation();

	@Test(testName = "Find List", dataProviderClass=TestData.class, dataProvider="getLists_Negative_FindLists")
	public void FindList(String[] body) {
		String[] tdata = new String[3];
		try {
			tdata[0] = body[0];
			tdata[1] = body[1];
			tdata[2] = body[2];
		}
		catch(IndexOutOfBoundsException e) {

			tdata[2] = "";
		}


		logger = extent.createTest(methodName + " - Negative Testcase- "+ tdata[0]);

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
			reportDetails = reportDetails + "\nRESPONSE -> Actual Error Message: " + errormessage;
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
				Element ele_item = ele_data.getChild("item");

				Element ele_listid = ele_item.getChild("listid");
				Element ele_name = ele_item.getChild("name");
				Element ele_createdate = ele_item.getChild("createdate");
				Element ele_subscribecount = ele_item.getChild("subscribecount");
				Element ele_unsubscribecount = ele_item.getChild("unsubscribecount");

				String listid = ele_listid.getText().toString();
				String name = ele_name.getText().toString();
				String createdate = ele_createdate.getText().toString();
				String subscribecount = ele_subscribecount.getText().toString();
				String unsubscribecount = ele_unsubscribecount.getText().toString();

				responseelement.put("listid", listid);
				responseelement.put("name", name);
				responseelement.put("createdate", createdate);
				responseelement.put("subscribecount", subscribecount);
				responseelement.put("unsubscribecount", unsubscribecount); 

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
