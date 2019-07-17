package tags_negative;

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
import common_utilities.TestData;
import common_utilities.Utilities;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tags_common_utilities.RequestDataforGetTagSubscribers;
import validation.GetTagSubscribersValidation;

public class GetTagSubscribers extends BaseClass {

	String methodName = "Get Tag Subscribers";
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
	RequestDataforGetTagSubscribers request_xml = new RequestDataforGetTagSubscribers();
	GetTagSubscribersValidation validation = new GetTagSubscribersValidation();

	@Test(testName = "Get Tag Subscribers", dataProviderClass=TestData.class, dataProvider="gettags_negative_GetTagSubscribers")
	public void gettagSubscribers(String[] body) {
		String[] tdata = new String[2];
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
		if(status != null && status.trim() !="") {
			if(!status.toLowerCase().trim().equals("success")) {

				String errordetail = responseelement.get("errordetail");
				reportDetails = reportDetails + "\nRESPONSE -> Error Detail: " + errordetail;
				errormessage = responseelement.get("errormessage");
				reportDetails = reportDetails + "\nRESPONSE -> Error Message: " + errormessage;
			}
			else {

				String count = responseelement.get("count");
				reportDetails = reportDetails + "\nRESPONSE -> Count: " + count;
				String res_data = responseelement.get("res_data");
				reportDetails = reportDetails + "\nRESPONSE -> Status: " + res_data;

			
			}
		}
		if(status == null) {
			status = "";
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
			try {
				status = ele_status.getText().toString();
			}
			catch(Exception e) {

			}
			if(status.toLowerCase().trim().contains("success") ) {

				Element ele_data = classElement.getChild("data");
				Element ele_count = ele_data.getChild("count");


				String count = "";
				try {
					count = ele_count.getText().toString();
				}
				catch(Exception e) {

				}



				responseelement.put("count", count);


			}
			else {
				Element ele_errormessage = classElement.getChild("errormessage");
				Element ele_errordetail = classElement.getChild("errordetail");

				String errormessage = "";
				try {
					errormessage = ele_errormessage.getText().toString();
				}
				catch(Exception e) {

				}

				String errordetail = "";
				try {
					errordetail = ele_errordetail.getText().toString();
				}
				catch(Exception e) {

				}

				responseelement.put("errormessage", errormessage);
				responseelement.put("errordetail", errordetail);

			}
			responseelement.put("status", status);

			Element ele_version = classElement.getChild("version");
			Element ele_elapsed = classElement.getChild("elapsed");

			String version = "";
			try {
				version = ele_version.getText().toString();
			}
			catch(Exception e) {

			}

			String elapsed = "";
			try {
				elapsed = ele_elapsed.getText().toString();
			}
			catch(Exception e) {

			}


			responseelement.put("version", version);
			responseelement.put("elapsed", elapsed);

		}
		catch(Exception e) {

		}

		return responseelement;
	}

}
