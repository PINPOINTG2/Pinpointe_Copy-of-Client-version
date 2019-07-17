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

public class FindListValidation extends BaseClass{

	Utilities utils = new Utilities();
	public boolean parameter_Validation(String paraString, HashMap<String, String> error) {
		
		boolean status = true;
		
		String username ="";
		String userToken ="";
		String listId ="";
		String listName ="";
		List<String> allAvailableLists = getLists();

		String username_format = utils.getproperty("format", "username_format").trim();
		String usertoken_format = utils.getproperty("format", "usertoken_format").trim();
		String listId_format = utils.getproperty("format", "listId_format").trim();
		String listName_format = utils.getproperty("format", "listName_format").trim();


		String username_ismandatory = utils.getproperty("mandatoryfield", "username").trim();
		String usertoken_ismandatory = utils.getproperty("mandatoryfield", "usertoken").trim();

		String listId_ismandatory = utils.getproperty("mandatoryfield", "Find_list_listId").trim();
		String listName_ismandatory = utils.getproperty("mandatoryfield", "Find_list_listName").trim();

		try {
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(new InputSource(new StringReader(paraString)));
			Element classElement = document.getRootElement();
			Element ele_username = classElement.getChild("username");
			username = ele_username.getText().toString();
			Element ele_usertoken = classElement.getChild("usertoken");
			userToken = ele_usertoken.getText().toString();

			Element ele_details = classElement.getChild("details");
			Element  ele_listid = ele_details.getChild("listid")
					;
			try {
				listId = ele_listid.getText().toString().trim();
			}
			catch(Exception e) {
				listId = "";
			}
			
			
			Element  ele_listName = ele_details.getChild("name")
					;
			try {
				listName = ele_listName.getText().toString().trim();
			}
			catch(Exception e) {
				listName = "";
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

		if(!isvalidlist(listId, listId_ismandatory, allAvailableLists)) {
			errorcount = errorcount + 1; 
			if(errormessage.isEmpty()) {
				if(listId.isEmpty() || listId.trim()=="") {
					errormessage =  utils.getproperty("errormessages", "");
				}
				else {
					errormessage =  utils.getproperty("errormessages", "error_no_list_match")+ " " +listId.trim();
				}
			}
		}

		if(!isvalidlist(listName, listName_ismandatory, allAvailableLists)) {
			errorcount = errorcount + 1; 
			if(errormessage.isEmpty()) {
				if(listName.isEmpty() || listName.trim()=="") {
					errormessage =  utils.getproperty("errormessages", "");
				}
				else {
					errormessage =  utils.getproperty("errormessages", "error_no_list_match")+ " '" + listName.trim()+"'";
				}
			}
		}
		
		if(!listId.trim().isEmpty()) {
			if(!listName.trim().isEmpty()) {
				errorcount = errorcount + 1; 
				errormessage =  utils.getproperty("errormessages", "error_list_given_id_name");
			}
		}
	
		if(listId.trim().isEmpty()) {
			if(listName.trim().isEmpty()) {
				errorcount = errorcount + 1; 
				errormessage =  utils.getproperty("errormessages", "error_list_missing_id_name");
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
