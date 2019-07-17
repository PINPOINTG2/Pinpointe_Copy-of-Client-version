package subscribers_positive;

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
import subscribers_common_utilities.SubscribersGetDataFromResponse;
import subscribers_common_utilities.VerifySubscriberExists;

public class DeleteSubscriber extends BaseClass {

	String methodName = "Delete Subscriber";
	String responseCode = "";
	String responseTime = "";
	String api_version ="";
	String status = "";
	String expected, actual;
	String response_data ="";
	String errormessage ="";
	String request_para ="";
	boolean isExists = false;
	boolean isalreadyExists = false;
	
	TestData data = new TestData();	
	Utilities utils = new Utilities();
	RequestDataForDeleteSubscriber request_xml = new RequestDataForDeleteSubscriber();
	VerifySubscriberExists verifyexists = new VerifySubscriberExists();
	ResponseGeneral responseGeneralvalid = new ResponseGeneral();
	
	@Test
	public void DeleteSubscribers() {
		ArrayList<String[]> tdata_array = data.getdeleteSubscriberData();
		String[] tdata = tdata_array.get(1);
		
		logger = extent.createTest(methodName + " - Positive Testcase ");

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
	
		HashMap<String, String> responseelement = responseGeneralvalid.response_Status(response);
		status = responseelement.get("status");
		reportDetails = reportDetails + "\nRESPONSE -> Status: " + status;
		String version = responseelement.get("version");
		reportDetails = reportDetails + "\nRESPONSE -> Version: " + version;
		String elapsed = responseelement.get("elapsed");
		reportDetails = reportDetails + "\nRESPONSE -> Elapsed: " + elapsed;

		if(!status.toLowerCase().trim().equals("success")) {

				errormessage = responseelement.get("errormessage");
				reportDetails = reportDetails + "\nRESPONSE -> Error Message: " + errormessage;

				String errordetail = responseelement.get("errordetail");
				reportDetails = reportDetails + "\nRESPONSE -> Error Detail: " + errordetail;
			
		}
		else {
			if(isalreadyExists) {
				reportDetails = reportDetails + "\n\n===========\nTEST RESULT\n===========\nIssue Detail: " + "The Subscriber still exists in the list.";
			}
			else {
				reportDetails = reportDetails + "\n\n===========\nTEST RESULT\n===========\nDetail: " + "The Subscriber successfully removed from the list.";
			}
		}


		reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(request_para);
		reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responsedata);		

		
		if(!isExists) {
			String validationstatus = "failed";
			Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status in response are not in SUCCESS");
		}
		else {
			if(isalreadyExists) {
				String validationstatus = "failed";
				Assert.assertFalse(isalreadyExists, "The Subscriber still exists in the list.");
				Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status in response are not in SUCCESS");
			}
			else {
				String validationstatus = "success";
				Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status in response are not in SUCCESS");
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
