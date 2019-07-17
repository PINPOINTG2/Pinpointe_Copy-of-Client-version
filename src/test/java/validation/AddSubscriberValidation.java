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

public class AddSubscriberValidation extends BaseClass {
	Utilities utils = new Utilities();
	public boolean parameter_Validation(String paraString, HashMap<String, String> error) {
		boolean status = true;

		String username ="";
		String userToken ="";
		String emailaddress ="";
		String add_to_autoresponders ="";
		String mailinglist ="";
		String format ="";
		String confirmed ="";
		List<Element> items = null;
		List<Element> tags = null;
		List<String> allAvailabletags = getTags();
		List<String> allAvailableLists = getLists();

		String username_format = utils.getproperty("format", "username_format").trim();
		String usertoken_format = utils.getproperty("format", "usertoken_format").trim();
		String emailaddress_format = utils.getproperty("format", "emailaddress_format").trim();
		String add_to_autoresponders_format = utils.getproperty("format", "add_to_autoresponders_format").trim();
		String mailinglist_format = utils.getproperty("format", "mailinglist_format").trim();
		String format_format = utils.getproperty("format", "format_format").trim();
		String confirmed_format = utils.getproperty("format", "confirmed_format").trim();
		String fieldid_format = utils.getproperty("format", "fieldid_format").trim();
		String value_format = utils.getproperty("format", "value_format").trim();
		String tag_format = utils.getproperty("format", "tag_format").trim();


		String username_ismandatory = utils.getproperty("mandatoryfield", "username").trim();
		String usertoken_ismandatory = utils.getproperty("mandatoryfield", "usertoken").trim();

		String emailaddress_ismandatory = utils.getproperty("mandatoryfield", "Add_Subscribers_emailaddress").trim();
		String add_to_autoresponders_ismandatory = utils.getproperty("mandatoryfield", "Add_Subscribers_add_to_autoresponders").trim();
		String mailinglist_ismandatory = utils.getproperty("mandatoryfield", "Add_Subscribers_mailinglist").trim();
		String format_ismandatory = utils.getproperty("mandatoryfield", "Add_Subscribers_format").trim();
		String confirmed_ismandatory = utils.getproperty("mandatoryfield", "Add_Subscribers_confirmed").trim();
		String customfields_ismandatory = utils.getproperty("mandatoryfield", "Add_Subscribers_customfields").trim();
		String tag_ismandatory = utils.getproperty("mandatoryfield", "Add_Subscribers_tag").trim();

		try {

			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(new InputSource(new StringReader(paraString)));
			Element classElement = document.getRootElement();
			Element ele_username = classElement.getChild("username");
			username = ele_username.getText().toString();
			Element ele_usertoken = classElement.getChild("usertoken");
			userToken = ele_usertoken.getText().toString();

			Element ele_details = classElement.getChild("details");
			Element  ele_emailaddress = ele_details.getChild("emailaddress");
			emailaddress = ele_emailaddress.getText().toString();
			try {
				Element  ele_add_to_autoresponders = ele_details.getChild("add_to_autoresponders");
				add_to_autoresponders = ele_add_to_autoresponders.getText().toString();
			}
			catch(Exception e) {
				add_to_autoresponders = "";
			}

			Element  ele_mailinglist = ele_details.getChild("mailinglist");
			mailinglist = ele_mailinglist.getText().toString();

			Element  ele_format = ele_details.getChild("format");
			format = ele_format.getText().toString();

			Element  ele_confirmed = ele_details.getChild("confirmed");
			confirmed = ele_confirmed.getText().toString();

			try {
				Element  ele_customfields = ele_details.getChild("customfields");
				items = ele_customfields.getChildren("item");
			}
			catch(Exception e) {
				items = null;
			}

			try {
				tags = ele_details.getChildren("tag");
			}
			catch(Exception e) {
				tags = null;
			}

		}
		catch(Exception e) {

		}
		int errorcount = 0;
		String errormessage ="";


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

		if(!isvalid(emailaddress, emailaddress_ismandatory, emailaddress_format)) {
			errorcount = errorcount + 1; 
			if(errormessage.isEmpty()) {
				if(emailaddress.isEmpty() || emailaddress.trim()=="") {
					errormessage = utils.getproperty("errormessages", "error_adding_failed");
				}
				else {
					errormessage = utils.getproperty("errormessages", "error_adding_failed")+ mailinglist;
				}
			}
		}
		
		if(add_to_autoresponders.toLowerCase().contains("true") || add_to_autoresponders.toLowerCase().contains("yes") || add_to_autoresponders.toLowerCase().contains("1")) {
			error.put("add_to_autoresponders", "yes");
		}
		else {
			error.put("add_to_autoresponders", "");
		}

		if(!isvalid(add_to_autoresponders, add_to_autoresponders_ismandatory, add_to_autoresponders_format)) {
			errorcount = errorcount + 1;
			errormessage ="";
		}

		if(!isvalidlist(mailinglist, mailinglist_ismandatory, allAvailableLists)) {
			errorcount = errorcount + 1; 
			if(errormessage.isEmpty()) {
				if(mailinglist.isEmpty() || mailinglist.trim()=="") {
					errormessage = utils.getproperty("errormessages", "error_invalid_mailinglist");
				}
				else {
					errormessage = utils.getproperty("errormessages", "error_no_access_to_list")+ " " + mailinglist;
				}
			}
		}

		if(!isvalid(format, format_ismandatory , format_format)) {
			errorcount = errorcount + 1; 
			errormessage ="";
		}

		if(!isvalid(confirmed, confirmed_ismandatory, confirmed_format)) {
			errorcount = errorcount + 1; 
			errormessage ="";
		}

		if(customfields_ismandatory.toLowerCase().contains("true") || customfields_ismandatory.toLowerCase().contains("yes") || customfields_ismandatory.toLowerCase().contains("1")) {
			if (items.size() > 0) {
				errormessage ="";

			}
			else {
				errorcount = errorcount + 1; 
			}				
		}

		if(items !=null) {
			if(items.size() >0) {

				for (Element ele_item : items) {
					Element  ele_fieldid = ele_item.getChild("fieldid");
					String fieldid = ele_fieldid.getText().toString();
					if(!isvalid(fieldid, "true", fieldid_format)) {
						errorcount = errorcount + 1; 
						if(errormessage.isEmpty()) {
							errormessage =  utils.getproperty("errormessages", "error_partial_unable_load_fieldid") +" '"+ fieldid.trim() +"'";
						}
					}
					Element  ele_value = ele_item.getChild("value");
					String value = ele_value.getText().toString();
					if(!isvalid(value, "false", value_format)) {
						errorcount = errorcount + 1; 
						errormessage ="";
					}

				}
			}
		}

		if(tag_ismandatory.toLowerCase().contains("true") || tag_ismandatory.toLowerCase().contains("yes") || tag_ismandatory.toLowerCase().contains("1")) {
			if (tags.size() > 0) {

			}
			else {
				errorcount = errorcount + 1; 
			}				
		}

		if(tags !=null) {
			if(tags.size() >0) {
				for (Element ele_tag : tags) {
					String tag = ele_tag.getText().toString();
					if(!isvalidtag(tag, "false", allAvailabletags)) {
						errorcount = errorcount + 1; 
					}
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
				if(value.trim().matches(format)) {
					result = true;
				}
				else {
					result = false;
				}
			}
		}
		else {
			if(!value.trim().isEmpty()) {
				if(value.trim().matches(format)) {
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
					result = true;
				}
				else {
					result = false;
				}
			}
		}
		else {
			if(!value.trim().isEmpty()) {
				if(tags.contains(value.trim())) {
					result = true;
				}
				else {
					result = false;
				}
			}
		}		
		return result;

	}

	public boolean isvalidlist(String value,String ismandatory, List<String> lists) {
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
				if(lists.contains(value.trim())) {
					result = true;
				}
				else {
					result = false;
				}
			}
		}
		else {
			if(!value.trim().isEmpty()) {
				if(lists.contains(value.trim())) {
					result = true;
				}
				else {
					result = false;
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

	public List<String> getLists(){
		List<String> lists = new ArrayList<String>();


		String request_para = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<xmlrequest>\r\n" + 
				"	<username>"+ username +"</username>\r\n" + 
				"	<usertoken>"+ token +"</usertoken>\r\n" + 
				"	<requesttype>lists</requesttype>\r\n" + 
				"   <requestmethod>getlists</requestmethod>\r\n" + 
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
					Element ele_listid = item.getChild("listid");
					Element ele_listname = item.getChild("name");

					String listid ="";
					String listname ="";
					try {
						listid = ele_listid.getText().toString();
						listname = ele_listname.getText().toString();
					}
					catch (Exception e) {
					}

					lists.add(listid);
					lists.add(listname);
				}

			}


		}
		catch(Exception e) {

		}

		return lists;
	}

}
