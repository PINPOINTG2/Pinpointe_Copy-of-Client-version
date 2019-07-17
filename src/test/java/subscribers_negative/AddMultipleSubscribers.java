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
import subscribers_common_utilities.ParseAddMultipleSubscriberResponse;
import subscribers_common_utilities.ParseGetSubscribersResponse;
import subscribers_common_utilities.RequestDataForAddMultipleSubscribers;
import subscribers_common_utilities.ResponseGeneral;
import subscribers_common_utilities.VerifySubscriberExists;
import validation.AddMultipleSubscribersValidation;
import validation.AddSubscriberValidation;

public class AddMultipleSubscribers extends BaseClass{

	Utilities utils = new Utilities();

	String methodName = "Add Multiple Subscriber";
	String responseCode = "";
	String responseTime = "";
	String api_version ="";
	String status = "";
	String expected, actual;
	String response_data ="";
	String errormessage ="";
	String response_status = "";
	String response_success_quantity = "";
	String response_success_ItemCount = "";
	String response_failed_quantity = "";
	String response_failed_ItemCount = "";
	String expectedErrorMessage = "";


	boolean isalreadyExists = false;
	RequestDataForAddMultipleSubscribers request = new RequestDataForAddMultipleSubscribers();
	TestData data = new TestData();
	ResponseGeneral responseGeneralvalid = new ResponseGeneral();
	ParseAddMultipleSubscriberResponse parseResponse = new ParseAddMultipleSubscriberResponse();
	AddMultipleSubscribersValidation addSubscriberValidation = new AddMultipleSubscribersValidation();
	VerifySubscriberExists verifyexists = new VerifySubscriberExists();

	@Test(testName = "Post Add Multiple Subscriber", dataProviderClass=TestData.class, dataProvider="getaddmultipleSubscribers_negData")
	public void apicall(String[] body) {
		logger = extent.createTest("Add Multiple Subscriber - TestCase "+ body[0]);

		System.out.println("processing "+ methodName +" record - "+ body[0]);

		ArrayList<String[]> contact = data.getContactforAddMultipleSubscribers_negData();

		ArrayList<String[]> customField = data.getCustomeFieldforAddMultipleSubscriber_negData();

		api_version = utils.getproperty("config", "api_version");
		String request_para = request.requestNegativedata(body,contact, customField);


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
			reportDetails = reportDetails + "\nRESPONSE -> Actual Error Message: " + errormessage;
		}
		else {
			HashMap<String, String> datafromResponse = parseResponse.parse(response.getBody().asString());
			response_status = datafromResponse.get("status");
			response_success_quantity = datafromResponse.get("success_quantity");
			response_success_ItemCount = datafromResponse.get("success_ItemCount");
			response_failed_quantity = datafromResponse.get("failed_quantity");
			response_failed_ItemCount = datafromResponse.get("failed_ItemCount");

			reportDetails = reportDetails + "\nSuccess Count : " + response_success_quantity;
			reportDetails = reportDetails + "\nFailed Count : " + response_failed_quantity;
		}

		Assert.assertTrue(version.toLowerCase().trim().equals(api_version), "Version in response are not in SUCCESS");


		if(!api_status) {
			String validationstatus = "failed";
			Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status has to be failed");
			Assert.assertTrue(errormessage.toLowerCase().trim().equals(expectedErrorMessage.toLowerCase().trim()), "Expected error message are not appeared.\n");

			reportDetails = reportDetails + "\nExpected Error Message: " + expectedErrorMessage;
			reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(request_para);
			reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responsedata);	
		}
		else {
			reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(request_para);
			reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responsedata);

			String validationstatus = _errormessage.get("apiStatus").trim().toLowerCase();
			Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status in response are not in SUCCESS");
			Assert.assertTrue(response_success_quantity.trim().equalsIgnoreCase(response_success_ItemCount.trim()), "Success Quantity and success items displayed in response are not matching.");
			Assert.assertTrue(response_failed_quantity.trim().equalsIgnoreCase(response_failed_ItemCount.trim()), "Failed Quantity and failed items displayed in response are not matching.");

			String expectedSuccess = _errormessage.get("totalsuccess");
			String expectedFailed = _errormessage.get("totalerror");

			Assert.assertTrue(response_success_quantity.trim().equalsIgnoreCase(expectedSuccess.trim()), "No.of Expected Success items are not matching.");
			Assert.assertTrue(response_failed_quantity.trim().equalsIgnoreCase(expectedFailed.trim()), "No.of Expected Failed items are not matching.");

			reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(request_para);
			reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responsedata);
		}
	}
}
