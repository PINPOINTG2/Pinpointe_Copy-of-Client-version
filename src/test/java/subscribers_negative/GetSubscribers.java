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
import subscribers_common_utilities.RequestDataForGetSubscriber;
import subscribers_common_utilities.ResponseGeneral;
import subscribers_common_utilities.VerifySubscriberExists;
import validation.GetSubscriberValidation;

public class GetSubscribers extends BaseClass {

	String methodName = "Get Subscriber";
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
	RequestDataForGetSubscriber request_xml = new RequestDataForGetSubscriber();
	VerifySubscriberExists verifyexists = new VerifySubscriberExists();
	ResponseGeneral responseGeneralvalid = new ResponseGeneral();
	ParseGetSubscribersResponse parseGetSubResponse = new ParseGetSubscribersResponse();
	GetSubscriberValidation getSubscriberValidationt = new GetSubscriberValidation();
	
	@Test(testName = "Get Subscriber", dataProviderClass=TestData.class, dataProvider="getSubscriber_negData")
	public void GetSubscribers(String[] body) {
		String[] tdata = new String[3];
		try {
			tdata[0] = body[0]; 
			tdata[1] = body[1]; 
			tdata[2] = body[2]; 
		}
		catch (ArrayIndexOutOfBoundsException e) {
			tdata[2] = "";
		}
		
		logger = extent.createTest(methodName + " - TestCase "+ body[0]);

		System.out.println("processing "+ methodName +" record - "+ tdata[0]);

		api_version = utils.getproperty("config", "api_version");
		HashMap<String, String> responseDataparsed = parseGetSubResponse.callGetSubscriberAndParse(tdata[2], tdata[1], uri);

		responseCode = responseDataparsed.get("responseCode");
		responseTime = responseDataparsed.get("responseTime");
		reportDetails = reportDetails + "METHOD NAME: " + methodName;
		reportDetails = reportDetails + "\nRESPONSE CODE: " + responseCode;
		reportDetails = reportDetails + "\nRESPONSE TIME: " + responseTime;

		
		HashMap<String, String> _errormessage = new HashMap<String, String>();
		boolean api_status = getSubscriberValidationt.parameter_Validation(responseDataparsed.get("request_body"), _errormessage);
		expectedErrorMessage = _errormessage.get("errorMessage");

		HashMap<String, String> responseelement = responseGeneralvalid.response_Status(responseDataparsed.get("reponse_body").toString());
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
		else {
			subs_count = responseDataparsed.get("count");
			reportDetails = reportDetails + "\nRESPONSE -> count: " + subs_count;
		}

		

		if(!api_status) {
			String validationstatus = "failed";
			reportDetails = reportDetails + "\nExpected Error Message: " + expectedErrorMessage;
			reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(responseDataparsed.get("request_body"));
			reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responseDataparsed.get("reponse_body"));		

			Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status has to be failed");
			Assert.assertTrue(errormessage.toLowerCase().trim().equals(expectedErrorMessage.toLowerCase().trim()), "Expected error message are not appeared.\n");
		}
		else {
			String validationstatus = _errormessage.get("apiStatus").trim().toLowerCase();
			Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status in response are not in SUCCESS");
			reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(responseDataparsed.get("request_body"));
			reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responseDataparsed.get("reponse_body"));		

		}
	
	}

}
