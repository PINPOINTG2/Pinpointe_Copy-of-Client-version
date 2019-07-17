package subscribers_bulk;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.testng.annotations.Test;
import org.xml.sax.InputSource;

import common_base.BaseClass;
import common_utilities.TestData;
import common_utilities.Utilities;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import subscribers_common_utilities.RequestDataForAddMultipleSubscribers;

public class AddMultipleSubscribersBulk extends BaseClass{

	Utilities utils = new Utilities();
	TestData data = new TestData();

	String methodName = "Add Multiple Subscriber Bulk";


	RequestDataForAddMultipleSubscribers request = new RequestDataForAddMultipleSubscribers();

	@Test(testName = "AddMultipleSubscriberBulk", dataProviderClass=TestData.class, dataProvider="addMultipleSubscriberBulkData")
	public void apicall(String[] body) {
			methodName = "Add Multiple Subscriber Bulk";
			logger = extent.createTest("Add Multiple SubscriberAPI - Bulk Execution");
		
		ArrayList<String[]> contact = data.getContactforAddMultipleSubscribersBulkData();

		ArrayList<String[]> customField = data.getCustomeFieldforAddMultipleSubscriberBulkData();

		System.out.println("processing "+ methodName +" record - "+ body[0]);	
		String request_para = request.requestdata(body,contact,customField);

		RestAssured.baseURI=uri;
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/xml");
		httpRequest.body(request_para);
		Response response = httpRequest.request(Method.POST);

		String responseStatus = response.getBody().asString();
		long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);

		String totalContact = "0";
				try {
					totalContact = Integer.toString(getTotalcontacts(request_para));
				}
				catch (Exception e) {
					totalContact = "0";
				}
				
		reportDetails = reportDetails + "\nMethod Name: " + methodName;
		reportDetails = reportDetails + "\nResponse Time: " + responseTime +"ms";
		reportDetails = reportDetails + "\n\n============================\nTotal Contacts: " + totalContact;

		HashMap<String, String> responseparse = getResponseParse(responseStatus);
		reportDetails = reportDetails + "\nTotal Success: "+ responseparse.get("success_quantity");
		reportDetails = reportDetails + "\nTotal Failed: "+ responseparse.get("failed_quantity");
		reportDetails = reportDetails + "\n==================================\nEmail         -      Failed Reason\n=================================="+ responseparse.get("failedReasons");
		
		
	}
	
	public int getTotalcontacts(String Para_dat) {
		int totalCount = 0;

		try {

			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(new InputSource(new StringReader(Para_dat)));
			Element classElement = document.getRootElement();
			Element ele_details = classElement.getChild("details");
			List<Element> contacts = ele_details.getChildren("contact");
			
			totalCount = contacts.size();

		}
		catch(Exception e) {
		}
		
		
		return totalCount;
	}

	public HashMap<String, String> getResponseParse(String response_Data){
		HashMap<String, String> res_data = new HashMap<String, String>();
		 try {
			 	SAXBuilder saxBuilder = new SAXBuilder();
				Document document = saxBuilder.build(new InputSource(new StringReader(response_Data)));
				Element classElement = document.getRootElement();
				Element ele_data = classElement.getChild("data");
				Element ele_success = ele_data.getChild("success");
				Element ele_Success_quantity = ele_success.getChild("quantity");
				
				String success_quantity = "";
				try {
					success_quantity = ele_Success_quantity.getText().toString();
				}
				catch(Exception e) {
					
				}
				
				String failedReasons = "";

				Element ele_failed = ele_data.getChild("failed");
				Element ele_failed_quantity = ele_failed.getChild("quantity");
				Element ele_failed_subs = ele_failed.getChild("subscribers");
				List<Element> ele_failed_items = ele_failed_subs.getChildren("item");
				if(ele_failed_items.size() > 0) {
					for (Element item : ele_failed_items) {
						Element ele_failed_item_emailaddress = item.getChild("emailaddress");
						Element ele_failed_item_failed_reason = item.getChild("failed_reason");

						String emailaddress =ele_failed_item_emailaddress.getText().toString();
						String reason = ele_failed_item_failed_reason.getText().toString();
						
						failedReasons = failedReasons + "\n" + emailaddress + " - " + reason.trim();
					}
				}
				

				String failed_quantity = "";
				try {
					failed_quantity = ele_failed_quantity.getText().toString();
				}
				catch(Exception e) {
					
				}

				res_data.put("success_quantity", success_quantity);
				res_data.put("failed_quantity", failed_quantity);
				res_data.put("failedReasons",failedReasons);
			 
		 }
		 catch(Exception e) {
			 
		 }
		
		return res_data;
	}
}