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
import subscribers_common_utilities.ParseGetSubscribersResponse;
import subscribers_common_utilities.RequestDataForCheckListsubscriber;
import subscribers_common_utilities.ResponseGeneral;
import subscribers_common_utilities.VerifySubscriberExists;

public class CheckListforContact extends BaseClass {

	String methodName = "Check List for Contact";
	String responseCode = "";
	String responseTime = "";
	String api_version ="";
	String status = "";
	String expected, actual;
	String response_data ="";
	String errormessage ="";

	boolean isalreadyExists = false;

	TestData data = new TestData();	
	Utilities utils = new Utilities();
	RequestDataForCheckListsubscriber request_xml = new RequestDataForCheckListsubscriber();
	VerifySubscriberExists verifyexists = new VerifySubscriberExists();
	ResponseGeneral responseGeneralvalid = new ResponseGeneral();
	ParseGetSubscribersResponse parseGetSubResponse = new ParseGetSubscribersResponse();

	@Test
	public void DeleteSubscribers() {
		ArrayList<String[]> tdata_array = data.getdeleteSubscriberData();
		String[] tdata = tdata_array.get(1);

		logger = extent.createTest(methodName + " - Positive Testcase ");

		System.out.println("processing "+ methodName +" record - "+ tdata[0]);

		api_version = utils.getproperty("config", "api_version");
		String request_para = request_xml.requestdata(tdata);	
		
		HashMap<String, String> getsubs_response = parseGetSubResponse.callGetSubscriberAndParse(tdata[2], tdata[1], uri);
		
		int count = Integer.parseInt(getsubs_response.get("count"));

		if(count>0) {
		isalreadyExists = true;
		} else {
			isalreadyExists = false;
		}

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
			reportDetails = reportDetails + "\n\nTest Case Status:\n===========================================\nSubscriber found in the list.\n\n";
		}

		
		reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(request_para);
		reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responsedata);		


		if(!isalreadyExists) {
			String validationstatus = "failed";
			Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status in response are not in SUCCESS");
		}
		else {			
				String validationstatus = "success";
				Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status in response are not in SUCCESS");
		
		}
	}
}

