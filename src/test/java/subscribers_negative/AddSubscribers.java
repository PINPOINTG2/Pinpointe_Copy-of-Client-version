package subscribers_negative;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import common_base.BaseClass;
import common_utilities.TestData;
import common_utilities.Utilities;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import subscribers_common_utilities.ParseGetSubscribersResponse;
import subscribers_common_utilities.RequestDataForAddSubscriber;
import subscribers_common_utilities.ResponseGeneral;
import subscribers_common_utilities.VerifySubscriberExists;
import validation.AddSubscriberValidation;

public class AddSubscribers extends BaseClass{

	Utilities utils = new Utilities();

	String methodName = "Add Subscriber";
	String responseCode = "";
	String responseTime = "";
	String api_version ="";
	String status = "";
	String expected, actual;
	String response_data ="";
	String errormessage ="";
	String expectedErrorMessage = "";

	boolean isalreadyExists = false;
	RequestDataForAddSubscriber request = new RequestDataForAddSubscriber();
	TestData data = new TestData();
	ResponseGeneral responseGeneralvalid = new ResponseGeneral();
	ParseGetSubscribersResponse parseGetSubResponse = new ParseGetSubscribersResponse();
	AddSubscriberValidation addSubscriberValidation = new AddSubscriberValidation();
	VerifySubscriberExists verifyexists = new VerifySubscriberExists();

	@Test(testName = "Post Add Subscriber", dataProviderClass=TestData.class, dataProvider="getaddSubscriber_negData")
	public void apicall(String[] body) {
		logger = extent.createTest(methodName +" - TestCase "+ body[0]);

		System.out.println("processing "+ methodName +" record - "+ body[0]);

		ArrayList<String[]> customField = data.getCustomeFieldforAddSubscriber_negData();

		api_version = utils.getproperty("config", "api_version");
		String request_para = request.requestdata(body,customField);	

		isalreadyExists = verifyexists.VerifyGetSubscribers(body[3], body[1]);

		RestAssured.baseURI=uri;
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/xml");
		httpRequest.body(request_para);
		Response response = httpRequest.request(Method.GET);

		String responsedata = response.getBody().asString();	
		responseCode = Integer.toString(response.getStatusCode());
		responseTime = Long.toString(response.getTimeIn(TimeUnit.MILLISECONDS)) + "ms";

		reportDetails = reportDetails + "METHOD NAME: " + methodName;
		reportDetails = reportDetails + "\nRESPONSE CODE: " + responseCode;
		reportDetails = reportDetails + "\nRESPONSE TIME: " + responseTime;

		HashMap<String, String> _errormessage = new HashMap<String, String>();
		boolean api_status = addSubscriberValidation.parameter_Validation(request_para, _errormessage);
		expectedErrorMessage = _errormessage.get("errorMessage");

		String autoresponder = _errormessage.get("add_to_autoresponders");

		HashMap<String, String> responseelement = responseGeneralvalid.response_Status(response);
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
			reportDetails = reportDetails + "\nActual Error Message: " + errormessage;
		}
		
		Assert.assertTrue(version.toLowerCase().trim().equals(api_version), "Version in response are not in SUCCESS");


		if(!api_status) {
			if(isalreadyExists) {
				String validationstatus = "failed";
				expectedErrorMessage =  utils.getproperty("errormessages", "error_adding_failed");
				Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status has to be failed");
				Assert.assertTrue(errormessage.toLowerCase().trim().equals(expectedErrorMessage.toLowerCase().trim()), "Expected error message are not appeared.\n");
				
				reportDetails = reportDetails + "\nExpected Error Message: " + expectedErrorMessage;
				reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(request_para);
				reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responsedata);		

			}
			else {
				String validationstatus = "failed";
				if(autoresponder.toLowerCase().contains("yes") && !expectedErrorMessage.contains("field id")  && !expectedErrorMessage.contains("no access to list")) {
					expectedErrorMessage =  utils.getproperty("errormessages", "error_adding_failed");
				}
				else {
					if(!expectedErrorMessage.contains("field id") && !expectedErrorMessage.contains("no access to list")) {
						expectedErrorMessage =  utils.getproperty("errormessages", "error_subs_exists");
					}
				}
				Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status has to be failed");
				Assert.assertTrue(errormessage.toLowerCase().trim().contains(expectedErrorMessage.toLowerCase().trim()), "Expected error message are not appeared.\n");
				reportDetails = reportDetails + "\nExpected Error Message: " + expectedErrorMessage;
				reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(request_para);
				reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responsedata);	
			}
		}
		else {
			if(isalreadyExists) {
				String validationstatus = "failed";
				Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status has to be failed");
				if(autoresponder.toLowerCase().contains("yes")) {
					expectedErrorMessage =  utils.getproperty("errormessages", "error_adding_failed");
				}
				else {
					expectedErrorMessage = utils.getproperty("errormessages", "error_subs_exists");
				}
				Assert.assertTrue(errormessage.toLowerCase().trim().contains(expectedErrorMessage.toLowerCase().trim()), "Expected error message are not appeared.\n");

				reportDetails = reportDetails + "\nExpected Error Message: " + expectedErrorMessage;
				reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(request_para);
				reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responsedata);	
			}
			else {
				String validationstatus = _errormessage.get("apiStatus").trim().toLowerCase();
				Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status in response are not in SUCCESS");

				reportDetails = reportDetails + "\nExpected Error Message: " + expectedErrorMessage;
				reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(request_para);
				reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responsedata);	
			}
		}
	}

}
