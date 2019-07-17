package validation;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import common_base.BaseClass;
import common_utilities.Utilities;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CreateTagsValidation extends BaseClass {
	Utilities utils = new Utilities();
	public boolean parameter_Validation(String paraString, HashMap<String, String> error) {
		boolean status = true;

		String username ="";
		String userToken ="";

		String tagName = "";
		String tagDescription = "";
		List<String> allAvailabletags = getTags();
		List<Element>  ele_tags = new ArrayList<Element>();

		int totalcount = 0;
		int totalsuccess = 0;
		int totalfail = 0;

		String username_format = utils.getproperty("format", "username_format").trim();
		String usertoken_format = utils.getproperty("format", "usertoken_format").trim();
		String tagName_format = utils.getproperty("format", "Create_tags_TagName").trim();
		String tagDescription_format = utils.getproperty("format", "Create_tags_TagDescription").trim();

		String username_ismandatory = utils.getproperty("mandatoryfield", "username").trim();
		String usertoken_ismandatory = utils.getproperty("mandatoryfield", "usertoken").trim();
		String tagName__ismandatory = utils.getproperty("mandatoryfield", "Create_tags_TagName").trim();
		String tagDescription__ismandatory = utils.getproperty("mandatoryfield", "Create_tags_TagDescription").trim();


		try {
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(new InputSource(new StringReader(paraString)));
			Element classElement = document.getRootElement();
			Element ele_username = classElement.getChild("username");
			username = ele_username.getText().toString();
			Element ele_usertoken = classElement.getChild("usertoken");
			userToken = ele_usertoken.getText().toString();

			Element ele_details = classElement.getChild("details");
			ele_tags = ele_details.getChildren("tag");




		}
		catch(Exception e) {

		}
		int errorcount = 0;
		String errormessage ="";
		String errormessage1 ="";



		if(ele_tags.size() > 0) {
			for (Element tag : ele_tags) {
				errorcount = 0;
				Element tag_name = tag.getChild("name");
				Element tag_description = tag.getChild("description");

				String _tagName = tag_name.getText().toString().trim();
				String _tagdescription = tag_name.getText().toString().trim();

				if(!isvalidtag(_tagName, tagName__ismandatory, allAvailabletags)) {
					errorcount = errorcount + 1; 

					if(_tagName.isEmpty() || _tagName.trim()=="") {
						errormessage1 =  utils.getproperty("errormessages", "tag_creation_failed");
					}
					else {
						errormessage1 =  "tag '" + tagName + "' already exists";
					}

				}


				if(!isvalid(_tagdescription, tagDescription__ismandatory, tagDescription_format)) {
					errorcount = errorcount + 1; 
				}
				totalcount = totalcount + 1;

				if(errorcount >0) {
					totalfail = totalfail + 1;
				}
				else {
					totalsuccess = totalsuccess + 1; 
				}
			}
		}





		if(!isvalid(username, username_ismandatory, username_format)) {
			errorcount = errorcount + 1;
			if(username.isEmpty() || username.trim()=="") {
				errormessage = utils.getproperty("errormessages", "error_missing_username");
			}
			else {
				errormessage = utils.getproperty("errormessages", "error_incorrect_credentials");
			}

		}


		if(!isvalid(userToken, usertoken_ismandatory, usertoken_format)) {
			errorcount = errorcount + 1; 
			if(errormessage.isEmpty()) {
				if(userToken.isEmpty() || userToken.trim()=="") {
					errormessage =  utils.getproperty("errormessages", "error_missing_usertoken");;
				}
				else {
					errormessage = utils.getproperty("errormessages", "error_incorrect_credentials");
				}
			}
		}




		if(errorcount >0) {
			error.put("apiStatus", "Failed");
			status = false;
		}
		else {
			error.put("apiStatus", "Success");
			status = true;
		}

		if(totalcount == 1 && totalfail ==1) {
			error.put("apiStatus", "Failed");
			status = false;
			if(errormessage.isEmpty()) {
				errormessage = errormessage1;
			}

		}


		error.put("errorMessage", errormessage);
		return status;
	}

	public boolean isvalid(String value, String ismandatory, String format) {
		boolean result = true;
		boolean mandatory = false;
		if(ismandatory.toLowerCase().contains("true") || ismandatory.toLowerCase().contains("yes") || ismandatory.toLowerCase().contains("1")) {
			mandatory = true;
		}

		if(mandatory) {
			if(value.trim().isEmpty()) {
				result = false;
			}
			else {
				if(value.matches(format)) {
					result = true;
				}
				else {
					result = false;
				}
			}
		}
		else {
			if(!value.trim().isEmpty()) {
				if(value.matches(format)) {
					result = true;
				}
				else {
					result = false;
				}
			}
		}		
		return result;
	}


	public boolean isvalidtag(String value,String ismandatory, List<String> tags) {
		boolean result = true;
		boolean mandatory = false;
		if(ismandatory.toLowerCase().contains("true") || ismandatory.toLowerCase().contains("yes") || ismandatory.toLowerCase().contains("1")) {
			mandatory = true;
		}

		if(mandatory) {
			if(value.trim().isEmpty()) {
				result = false;
			}
			else {
				if(tags.contains(value.trim())) {
					result = false;
				}
				else {
					result = true;
				}
			}
		}
		else {
			if(!value.trim().isEmpty()) {
				if(tags.contains(value.trim())) {
					result = false;
				}
				else {
					result = true;
				}
			}
		}		
		return result;

	}


	public List<String> getTags(){
		List<String> tags = new ArrayList<String>();


		String request_para = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<xmlrequest>\r\n" + 
				"	<username>"+ username +"</username>\r\n" + 
				"	<usertoken>"+ token +"</usertoken>\r\n" + 
				"    <requesttype>tags</requesttype>\r\n" + 
				"    <requestmethod>GetTags</requestmethod>\r\n" + 
				"    <details>\r\n" + 
				"    </details>\r\n" + 
				"</xmlrequest>";


		RestAssured.baseURI=uri;
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/xml");
		httpRequest.body(request_para);
		Response response = httpRequest.request(Method.GET);

		String bodyString = response.getBody().asString();
		try {

			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(new InputSource(new StringReader(bodyString)));
			Element classElement = document.getRootElement();

			Element ele_Status = classElement.getChild("status");
			String tagapi_status = ele_Status.getText().toString();

			if(tagapi_status.toLowerCase().contains("success")) {
				Element ele_data = classElement.getChild("data");
				List<Element> ele_Items = ele_data.getChildren("item");

				for (Element item : ele_Items) {
					Element ele_tagid = item.getChild("tagid");
					Element ele_tagname = item.getChild("name");

					String tagid ="";
					String tagname ="";
					try {
						tagid = ele_tagid.getText().toString();
						tagname = ele_tagname.getText().toString();
					}
					catch (Exception e) {
					}

					tags.add(tagid);
					tags.add(tagname);
				}

			}


		}
		catch(Exception e) {

		}



		return tags;
	}


}
