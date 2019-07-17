package tags_negative;

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
import tags_common_utilities.RequestDataforCreateTags;
import validation.CreateTagsValidation;

public class CreateTags extends BaseClass {

	String methodName = "Create Tags";
	String responseCode = "";
	String responseTime = "";
	String api_version ="";
	String status = "";
	String expected, actual;
	String subs_count ="";
	String errormessage ="";
	HashMap<String, String> responseDataparsed = null;
	String expectedErrorMessage = "";

	TestData data = new TestData();	
	Utilities utils = new Utilities();
	RequestDataforCreateTags request_xml = new RequestDataforCreateTags();
	CreateTagsValidation validation = new CreateTagsValidation();

	@Test(testName = "Create Tags", dataProviderClass=TestData.class, dataProvider="gettags_Negative_CreateTags")
	public void createTags(String[] body) {
		ArrayList<String[]> tdata_array = data.getNegativeCreateTagsData();

		logger = extent.createTest(methodName + " - Negative - " + body[0]);

		System.out.println("processing "+ methodName +" record - "+ body[0]);

		api_version = utils.getproperty("config", "api_version");
		String request_para = request_xml.requestdata(body, tdata_array);	

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

		HashMap<String, String> _errormessage = new HashMap<String, String>();
		boolean api_status = validation.parameter_Validation(request_para, _errormessage);
		expectedErrorMessage = _errormessage.get("errorMessage");


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

				String count = responseelement.get("count");
				reportDetails = reportDetails + "\nRESPONSE ->Success Count: " + count;
				String err_count = responseelement.get("err_count");
				reportDetails = reportDetails + "\nRESPONSE ->Failed Count: " + err_count;
				String res_data = responseelement.get("res_data");
				reportDetails = reportDetails + "\n======================\nResponse Data\n====================== " + res_data;


				String error_data = responseelement.get("error");

				reportDetails = reportDetails + "\n\n====================\nError Details:\n====================\n" + error_data;
			}
		}

		if(status == null) {
			status = "";
		}

		if(!api_status) {
			String validationstatus = "failed";
			if(expectedErrorMessage !=null && expectedErrorMessage.trim() != "") {
			reportDetails = reportDetails + "\nExpected Error Message: " + expectedErrorMessage;
			}
			reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(request_para);
			reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responsedata);	
		}
		else {
			String validationstatus = "success";

			reportDetails = reportDetails + "\n\nREQUEST BODY:\n" + utils.prettyFormat(request_para);
			reportDetails = reportDetails + "\n\nRESPONSE BODY:\n" + utils.prettyFormat(responsedata);
			Assert.assertTrue(status.toLowerCase().trim().equals(validationstatus), "Status in response are not in SUCCESS");
			Assert.assertTrue(version.toLowerCase().trim().equals("3.0"), "Version in response are not in SUCCESS");		

		}	

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
			responseelement.put("status", status);
			if(status.toLowerCase().trim().contains("success") ) {

				Element ele_data = classElement.getChild("data");
				Element ele_count = ele_data.getChild("count");


				String count = "";
				try {
					count = ele_count.getText().toString();
				}
				catch(Exception e) {

				}



				responseelement.put("count", count);


				String res_data = "";
				Element ele_tags = ele_data.getChild("tags");
				List<Element> ele_tag = ele_tags.getChildren("tag");	

				if(ele_tag.size() >0) {
					for (Element tag : ele_tag) {

						Element ele_tagid = tag.getChild("tagid");
						Element ele_userid = tag.getChild("userid");
						Element ele_username = tag.getChild("username");
						Element ele_name = tag.getChild("name");
						Element ele_description = tag.getChild("description");


						String tagid = ele_tagid.getText().toString();
						String userid = ele_userid.getText().toString();
						String username = ele_username.getText().toString();
						String name = ele_name.getText().toString();
						String description = ele_description.getText().toString();

						res_data = res_data + "\n Tag Id: "+ tagid; 
						res_data = res_data + "\n User Id: "+ userid; 
						res_data = res_data + "\n Username: "+ username; 
						res_data = res_data + "\n Name: "+ name; 
						res_data = res_data + "\n Description: "+ description; 
						res_data = res_data + "\n";


					}

				}


				responseelement.put("res_data", res_data);
				List<Element> ele_error = null;
				try {
					Element ele_errors = ele_data.getChild("errors");
					ele_error = ele_errors.getChildren("error");
				}
				catch (Exception e) {
					ele_error = null;
				}

				String err_count = "0";
				try {
					err_count = Integer.toString(ele_error.size());
				}
				catch (Exception e) {
					err_count = "0";
				}

				String error = "";

				if(ele_error != null) {
					if(ele_error.size() >0) {
						for (Element errors : ele_error) {
							String err = errors.getText().toString();
							error = error + "\n" + err ;
						}
					}
				}


				responseelement.put("err_count", err_count);
				responseelement.put("error", error);



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
