package tags_bulk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import common_base.BaseClass;
import common_base.BulkBaseClass;
import common_utilities.TestData;
import common_utilities.Utilities;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import subscribers_common_utilities.ResponseGeneral;
import tags_common_utilities.RequestDataforCreateTags;

public class CreateTags extends BaseClass {

	String status = "";
	String methodName = "Create Tags Bulk";

	Utilities utils = new Utilities();
	TestData data = new TestData();
	ResponseGeneral responseGeneralvalid = new ResponseGeneral();



	RequestDataforCreateTags request = new RequestDataforCreateTags();

	@Test
	public void apicall() {
		
			
			logger = extent.createTest("Create Tags API - Bulk Execution");
		
		ArrayList<String[]> contact = data.getContactforCreateTagsBulkData();

		System.out.println("processing "+ methodName +" record  ");	
		String request_para = request.requestdata(contact);

		
		RestAssured.baseURI=uri;
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/xml");
		httpRequest.body(request_para);
		Response response = httpRequest.request(Method.POST);

		String testdata = "";
		String Request = request_para;
		String responseStatus = response.getBody().asString();
		long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);

		
		HashMap<String, String> responseelement = responseGeneralvalid.response_Status(response);
		status = responseelement.get("status");
		reportDetails = reportDetails + "\nRESPONSE -> Status: " + status;
		String version = responseelement.get("version");
		reportDetails = reportDetails + "\nRESPONSE -> Version: " + version;
		String elapsed = responseelement.get("elapsed");
		reportDetails = reportDetails + "\nRESPONSE -> Elapsed: " + elapsed;
		reportDetails = reportDetails + "\nResponse Time: " + responseTime +"ms";
	

		reportDetails = reportDetails + "\n\n";

		reportDetails = reportDetails + "\nRequest Data: " + utils.prettyFormat(request_para);
		reportDetails = reportDetails + "\n\nResponse Data: " + responseStatus;

	

	

	}


}
