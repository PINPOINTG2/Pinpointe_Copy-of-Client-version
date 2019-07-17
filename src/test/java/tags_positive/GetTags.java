package tags_positive;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.InputSource;

import common_base.BaseClass;
import common_utilities.TestData;
import common_utilities.Utilities;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tags_common_utilities.RequestDataforGetTags;

public class GetTags extends BaseClass {

	String methodName = "Get Tags";
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
	RequestDataforGetTags request_xml = new RequestDataforGetTags();

	@Test
	public void gettags() {
		ArrayList<String[]> tdata_array = data.getTagsData();

		String[] tdata_temp = tdata_array.get(3);

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
		if(status != null && status.trim() !="") {
			if(!status.toLowerCase().trim().equals("success")) {

				String errordetail = responseelement.get("errordetail");
				reportDetails = reportDetails + "\nRESPONSE -> Error Detail: " + errordetail;
				errormessage = responseelement.get("errormessage");
				reportDetails = reportDetails + "\nRESPONSE -> Error Message: " + errormessage;
			}
			else {

				String res_data = responseelement.get("res_data");
				reportDetails = reportDetails + "\n\n====================\nTags \n====================\n" + res_data;
			}
		}

		reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(request_para);
		reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responsedata);		


		String validationstatus = "success";
		if(status == null) {
			status = "";
		}
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
			try {
				status = ele_status.getText().toString();
			}
			catch(Exception e) {

			}
			if(status.toLowerCase().trim().contains("success") ) {

				Element ele_data = classElement.getChild("data");

				List<Element> items = ele_data.getChildren("item");

				String res_data = "";
				if(items.size() > 0 && items != null) {
					for (Element item : items) {
						Element ele_tagid = item.getChild("tagid");
						Element ele_username = item.getChild("username");
						Element ele_name = item.getChild("name");
						Element ele_description = item.getChild("description");
						
						String tagid = ele_tagid.getText().toString().trim();
						String username = ele_username.getText().toString().trim();
						String name = ele_name.getText().toString().trim();
						String description = ele_description.getText().toString().trim();
						

						res_data = res_data + "\nTag Id:" + tagid;
						res_data = res_data + "\nUsername:" + username;
						res_data = res_data + "\nTag Name:" + name;
						res_data = res_data + "\nTag Description:" + description;
						res_data = res_data + "\n";
						
					}
				}

				responseelement.put("res_data", res_data);

			}
			else {
				Element ele_errormessage = classElement.getChild("errormessage");
				Element ele_errordetail = classElement.getChild("errordetail");

				String errormessage = "";
				try {
					errormessage = ele_errormessage.getText().toString();
				}
				catch(Exception e) {

				}

				String errordetail = "";
				try {
					errordetail = ele_errordetail.getText().toString();
				}
				catch(Exception e) {

				}

				responseelement.put("errormessage", errormessage);
				responseelement.put("errordetail", errordetail);

			}
			responseelement.put("status", status);

			Element ele_version = classElement.getChild("version");
			Element ele_elapsed = classElement.getChild("elapsed");

			String version = "";
			try {
				version = ele_version.getText().toString();
			}
			catch(Exception e) {

			}

			String elapsed = "";
			try {
				elapsed = ele_elapsed.getText().toString();
			}
			catch(Exception e) {

			}


			responseelement.put("version", version);
			responseelement.put("elapsed", elapsed);

		}
		catch(Exception e) {

		}

		return responseelement;
	}

}
