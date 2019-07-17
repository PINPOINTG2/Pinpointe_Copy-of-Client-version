package lists_positive;

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
import common_utilities.ResponseGeneral;
import common_utilities.TestData;
import common_utilities.Utilities;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lists_common_utilities.RequestDataforFindList;

public class FindListByListId extends BaseClass {

	String methodName = "Find List By List id";
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
	RequestDataforFindList request_xml = new RequestDataforFindList();
	ResponseGeneral responseGeneralvalid = new ResponseGeneral();

	@Test
	public void FindList() {
		ArrayList<String[]> tdata_array = data.getListsData();
		
		String[] tdata_temp = tdata_array.get(0);

		String[] tdata = new String[3];
		try {
			tdata[0] = tdata_temp[0];
			tdata[1] = tdata_temp[1];
			tdata[2] = tdata_temp[2];
		}
		catch(IndexOutOfBoundsException e) {

			tdata[2] = "";
		}


		logger = extent.createTest(methodName + " - Positive Testcase ");

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

		HashMap<String, String> responseelement = parseResponse(response);
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
			reportDetails = reportDetails + "\nRESPONSE -> Error Message: " + errormessage;
		}
		else {
			

			String listid = responseelement.get("listid");
			String name = responseelement.get("name");
			String createdate = responseelement.get("createdate");
			String subscribecount = responseelement.get("subscribecount");
			String unsubscribecount = responseelement.get("unsubscribecount");
			reportDetails = reportDetails + "\nRESPONSE -> List Id: " + listid;
			reportDetails = reportDetails + "\nRESPONSE -> Name: " + name;
			reportDetails = reportDetails + "\nRESPONSE -> Create Date: " + createdate;
			reportDetails = reportDetails + "\nRESPONSE -> Subscribe Count: " + subscribecount;
			reportDetails = reportDetails + "\nRESPONSE -> Unsubscribe Count: " + unsubscribecount;
			reportDetails = reportDetails + "\n\nTest Case Status:\n===========================================\nSuccessfully fetched the list.\n\n";
		}


		reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(request_para);
		reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responsedata);		


		String validationstatus = "success";
		Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status in response are not in SUCCESS");
		Assert.assertTrue(version.toLowerCase().trim().equals("3.0"), "Version in response are not in SUCCESS");		

	}

	public HashMap<String, String> parseResponse(Response response) {
		HashMap<String, String> responseelement = new HashMap<String, String>();
		
		try {

			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(new InputSource(new StringReader(response.getBody().asString())));
			Element classElement = document.getRootElement();

			Element ele_status = classElement.getChild("status");
			status = ele_status.getText().toString();
			if(status.toLowerCase().trim().contains("success") ) {

				Element ele_data = classElement.getChild("data");
				Element ele_item = ele_data.getChild("item");

				Element ele_listid = ele_item.getChild("listid");
				Element ele_name = ele_item.getChild("name");
				Element ele_createdate = ele_item.getChild("createdate");
				Element ele_subscribecount = ele_item.getChild("subscribecount");
				Element ele_unsubscribecount = ele_item.getChild("unsubscribecount");

				String listid = ele_listid.getText().toString();
				String name = ele_name.getText().toString();
				String createdate = ele_createdate.getText().toString();
				String subscribecount = ele_subscribecount.getText().toString();
				String unsubscribecount = ele_unsubscribecount.getText().toString();

				responseelement.put("listid", listid);
				responseelement.put("name", name);
				responseelement.put("createdate", createdate);
				responseelement.put("subscribecount", subscribecount);
				responseelement.put("unsubscribecount", unsubscribecount); 

			}
			else {
				Element ele_errormessage = classElement.getChild("errormessage");
				Element ele_errordetail = classElement.getChild("errordetail");

				String errormessage = ele_errormessage.getText().toString();
				String errordetail = ele_errordetail.getText().toString();

				responseelement.put("errormessage", errormessage);
				responseelement.put("errordetail", errordetail);
				
			}
			responseelement.put("status", status);
			
			Element ele_version = classElement.getChild("version");
			Element ele_elapsed = classElement.getChild("elapsed");
			
			String version = ele_version.getText().toString();
			String elapsed = ele_elapsed.getText().toString();
			

			responseelement.put("version", version);
			responseelement.put("elapsed", elapsed);
			
		}
		catch(Exception e) {

		}
		
		return responseelement;
	}

}
