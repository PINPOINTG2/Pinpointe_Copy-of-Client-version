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
import subscribers_common_utilities.RequestDataForDeleteSubscriber;
import subscribers_common_utilities.ResponseGeneral;
import subscribers_common_utilities.VerifySubscriberExists;
import validation.DeleteSubscriberValidation;

public class DeleteSubscribers extends BaseClass{

	String methodName = "Delete Subscriber";
	String responseCode = "";
	String responseTime = "";
	String api_version ="";
	String status = "";
	String expected, actual;
	String response_data ="";
	String errormessage ="";
	String request_para ="";
	String expectedErrorMessage = "";
	boolean isExists = false;
	boolean isalreadyExists = false;
	
	TestData data = new TestData();	
	Utilities utils = new Utilities();
	RequestDataForDeleteSubscriber request_xml = new RequestDataForDeleteSubscriber();
	VerifySubscriberExists verifyexists = new VerifySubscriberExists();
	ResponseGeneral responseGeneralvalid = new ResponseGeneral();
	DeleteSubscriberValidation SubscriberValidation = new DeleteSubscriberValidation();
	
	
	@Test(testName = "Delete Subscriber", dataProviderClass=TestData.class, dataProvider="getDeleteSubscriber_negData")
	public void DeleteSubscribers(String[] body) {
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
		

		isExists = verifyexists.VerifyGetSubscribers(tdata[2], tdata[1]);
		Response response = callDelete(tdata);
		String responsedata = response.getBody().asString();	

		isalreadyExists = verifyexists.VerifyGetSubscribers(tdata[2], tdata[1]);
		
		responseCode = Integer.toString(response.getStatusCode());
		responseTime = Long.toString(response.getTimeIn(TimeUnit.MILLISECONDS)) + "ms";
		reportDetails = reportDetails + "METHOD NAME: " + methodName;
		reportDetails = reportDetails + "\nRESPONSE CODE: " + responseCode;
		reportDetails = reportDetails + "\nRESPONSE TIME: " + responseTime;
		
		HashMap<String, String> _errormessage = new HashMap<String, String>();
		boolean api_status = SubscriberValidation.parameter_Validation(request_para, _errormessage);
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
				reportDetails = reportDetails + "\nActual Error Message: " + errormessage;

			
		}
		else {
			
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
			if(!isExists) {
				String validationstatus = "failed";
				expectedErrorMessage =  utils.getproperty("errormessages", "error_subs_not_on_list");
				reportDetails = reportDetails + "\nExpected Error Message: " + expectedErrorMessage;
			
				reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(request_para);
				reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responsedata);	
				Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status in response are not in SUCCESS");
				Assert.assertTrue(errormessage.toLowerCase().trim().equals(expectedErrorMessage.toLowerCase().trim()), "Expected error message are not appeared.\n");
			}
			else {
				if(isalreadyExists) {
					String validationstatus = "failed";
					reportDetails = reportDetails + "\nExpected Error Message: " + expectedErrorMessage;
				
					reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(request_para);
					reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responsedata);	
					Assert.assertFalse(isalreadyExists, "The Subscriber still exists in the list.");
					Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status in response are not in SUCCESS");
				}
				else {
					String validationstatus = "success";
					Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status in response are not in SUCCESS");
					
					reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(request_para);
					reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responsedata);	
				}
			}
		}
		
		
	}
	
	public Response callDelete(String[] tdata) {
		Response response = null;
		request_para = request_xml.requestdata(tdata);	
		

		RestAssured.baseURI=uri;
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/xml");
		httpRequest.body(request_para);
		response = httpRequest.request(Method.POST);
		
		return response;
		
	}

}
