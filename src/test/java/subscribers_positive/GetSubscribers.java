package subscribers_positive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import common_base.BaseClass;
import common_utilities.TestData;
import common_utilities.Utilities;
import subscribers_common_utilities.ParseGetSubscribersResponse;
import subscribers_common_utilities.RequestDataForGetSubscriber;
import subscribers_common_utilities.ResponseGeneral;
import subscribers_common_utilities.SubscribersGetDataFromResponse;
import subscribers_common_utilities.VerifySubscriberExists;

public class GetSubscribers extends BaseClass {

	String methodName = "Get Subscriber";
	String responseCode = "";
	String responseTime = "";
	String api_version ="";
	String status = "";
	String expected, actual;
	String subs_count ="";
	String errormessage ="";
	HashMap<String, String> responseDataparsed = null;

	TestData data = new TestData();	
	Utilities utils = new Utilities();
	RequestDataForGetSubscriber request_xml = new RequestDataForGetSubscriber();
	VerifySubscriberExists verifyexists = new VerifySubscriberExists();
	ResponseGeneral responseGeneralvalid = new ResponseGeneral();
	ParseGetSubscribersResponse parseGetSubResponse = new ParseGetSubscribersResponse();

	@Test
	public void GetSubscribers() {
		ArrayList<String[]> tdata_array = data.getSubscriberData();
		String[] tdata = tdata_array.get(1);

		logger = extent.createTest(methodName + " - Positive Testcase ");

		System.out.println("processing "+ methodName +" record - "+ tdata[0]);

		api_version = utils.getproperty("config", "api_version");
		HashMap<String, String> responseDataparsed = parseGetSubResponse.callGetSubscriberAndParse(tdata[2], tdata[1], uri);

		responseCode = responseDataparsed.get("responseCode");
		responseTime = responseDataparsed.get("responseTime");
		reportDetails = reportDetails + "METHOD NAME: " + methodName;
		reportDetails = reportDetails + "\nRESPONSE CODE: " + responseCode;
		reportDetails = reportDetails + "\nRESPONSE TIME: " + responseTime;

		HashMap<String, String> responseelement = responseGeneralvalid.response_Status(responseDataparsed.get("reponse_body").toString());
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
			subs_count = responseDataparsed.get("count");
			reportDetails = reportDetails + "\nRESPONSE -> count: " + subs_count;
			reportDetails = reportDetails + "\n\nTest Case Status:\n===========================================\nSuccessfully fetched the subscriber from the list.\n\n";
		}

		
		reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(responseDataparsed.get("request_body"));
		reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responseDataparsed.get("reponse_body"));		


		String validationstatus = "success";
		Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status in response are not in SUCCESS");
		Assert.assertTrue(status.toLowerCase().trim().equals("success"), "Status in response are not in SUCCESS");
		Assert.assertTrue(version.toLowerCase().trim().equals("3.0"), "Version in response are not in SUCCESS");		

		VerifyGetSubscribers(responseDataparsed);
	}


	private void VerifyGetSubscribers(HashMap<String, String> responseparsed) {

		int totalCount =Integer.parseInt(responseparsed.get("count"));

		if(totalCount >0) {

			String subscriberid = responseparsed.get("subscriberid"+1); 
			String listid = responseparsed.get("listid"+1); 
			String emailaddress = responseparsed.get("emailaddress"+1); 
			String domainname = responseparsed.get("domainname"+1); 
			String format = responseparsed.get("format"+1); 
			String confirmed = responseparsed.get("confirmed"+1); 
			String confirmcode = responseparsed.get("confirmcode"+1); 
			String requestdate = responseparsed.get("requestdate"+1); 
			String requestip = responseparsed.get("requestip"+1); 
			String confirmdate = responseparsed.get("confirmdate"+1); 
			String confirmip = responseparsed.get("confirmip"+1); 
			String subscribedate = responseparsed.get("subscribedate"+1); 
			String updatedate = responseparsed.get("updatedate"+1); 
			String bounced = responseparsed.get("bounced"+1); 
			String unsubscribed = responseparsed.get("unsubscribed"+1); 
			String unsubscribeconfirmed = responseparsed.get("unsubscribeconfirmed"+1); 
			String feedbacklooped = responseparsed.get("feedbacklooped"+1); 
			String feedbackloopconfirmed = responseparsed.get("feedbackloopconfirmed"+1); 
			String status = responseparsed.get("status"+1); 
			String last_visitorid = responseparsed.get("last_visitorid"+1); 
			String formid = responseparsed.get("formid"+1); 
			String total_opens = responseparsed.get("total_opens"+1); 
			String total_clicks = responseparsed.get("total_clicks"+1); 
			String last_open = responseparsed.get("last_open"+1); 
			String last_click = responseparsed.get("last_click"+1); 
			String dateadded = responseparsed.get("dateadded"+1); 
			String activitystatus = responseparsed.get("activitystatus"+1); 
			String listname = responseparsed.get("listname"+1); 
			String expectedTags = responseparsed.get("ExpectedTags"+1); 
			String actualTags =responseparsed.get("ActualTags"+1); 


			reportDetails = reportDetails + "\n GETTING RESPONSE -> subscriberid: " + subscriberid;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> listid: " + listid;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> emailaddress: " + emailaddress;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> domainname: " + domainname;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> format: " + format;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> confirmed: " + confirmed;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> confirmcode: " + confirmcode;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> requestdate: " + requestdate;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> requestip: " + requestip;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> confirmdate: " + confirmdate;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> confirmip: " + confirmip;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> subscribedate: " + subscribedate;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> updatedate: " + updatedate;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> bounced: " + bounced;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> unsubscribed: " + unsubscribed;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> unsubscribeconfirmed: " + unsubscribeconfirmed;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> feedbacklooped: " + feedbacklooped;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> feedbackloopconfirmed: " + feedbackloopconfirmed;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> status: " + status;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> last_visitorid: " + last_visitorid;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> formid: " + formid;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> total_opens: " + total_opens;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> total_clicks: " + total_clicks;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> last_open: " + last_open;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> last_click: " + last_click;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> dateadded: " + dateadded;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> activitystatus: " + activitystatus;
			reportDetails = reportDetails + "\n GETTING RESPONSE -> listname: " + listname;

			expected = responseparsed.get("Elements"+1);  
			actual = "Expected Elements are appeared"; 
			Assert.assertEquals(actual, expected, "Expect tags and actual tags are not matched.");
		}
	}

}
