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

public class GetUsersCustomFieldDataValidation extends BaseClass{

	Utilities utils = new Utilities();
	public boolean parameter_Validation(String paraString, HashMap<String, String> error) {

		boolean status = true;

		String username ="";
		String userToken ="";
		String listId ="";
		String subscriberid = "";
		String customfield = "";
		List<String> allAvailableLists = getLists();

		String username_format = utils.getproperty("format", "username_format").trim();
		String usertoken_format = utils.getproperty("format", "usertoken_format").trim();
		String listId_format = utils.getproperty("format", "listId_format").trim();
		String subscriberid_format = utils.getproperty("format", "subscriberid_format").trim();
		String customfield_format = utils.getproperty("format", "GetUsers_customfield_format").trim();


		String username_ismandatory = utils.getproperty("mandatoryfield", "username").trim();
		String usertoken_ismandatory = utils.getproperty("mandatoryfield", "usertoken").trim();

		String listId_ismandatory = utils.getproperty("mandatoryfield", "Get_Users_Custom_Field_Data_listId").trim();
		String subscriberid_ismandatory = utils.getproperty("mandatoryfield", "Get_Users_Custom_Field_Data__subscriberid").trim();
		String customfield_ismandatory = utils.getproperty("mandatoryfield", "Get_Users_Custom_Field_Data__customfield").trim();

		try {
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(new InputSource(new StringReader(paraString)));
			Element classElement = document.getRootElement();
			Element ele_username = classElement.getChild("username");
			username = ele_username.getText().toString();
			Element ele_usertoken = classElement.getChild("usertoken");
			userToken = ele_usertoken.getText().toString();

			Element ele_details = classElement.getChild("details");
			Element  ele_subscriberid = ele_details.getChild("subscriberid");
			Element  ele_listId = ele_details.getChild("mailinglist");
			Element  ele_customfield = ele_details.getChild("customfield");

			try {
				subscriberid = ele_subscriberid.getText().toString().trim();
			}
			catch(Exception e) {
				subscriberid = "";
			}

			try {
				listId = ele_listId.getText().toString().trim();
			}
			catch(Exception e) {
				listId = "";
			}

			try {
				customfield = ele_customfield.getText().toString().trim();
			}
			catch(Exception e) {
				customfield = "";
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


		if(!isvalid(subscriberid, subscriberid_ismandatory, subscriberid_format)) {
			errorcount = errorcount + 1; 
			if(errormessage.isEmpty()) {
				if(subscriberid.isEmpty() || subscriberid.trim()=="") {
					errormessage =  utils.getproperty("errormessages", "error_missing_subscriberid");
				}
				else {
					errormessage =  utils.getproperty("errormessages", "error_missing_subscriberid");
				}
			}
		}

		if(!isvalidlist(listId, listId_ismandatory, allAvailableLists)) {
			errorcount = errorcount + 1; 
			if(errormessage.isEmpty()) {
				if(listId.isEmpty() || listId.trim()=="") {
					errormessage =  utils.getproperty("errormessages", "error_missing_listid");
				}
				else {
					errormessage =  utils.getproperty("errormessages", "error_no_access_to_list")+ " " +listId.trim();
				}
			}
		}

		if(!isvalid(customfield, customfield_ismandatory, customfield_format)) {
			errorcount = errorcount + 1; 
			if(errormessage.isEmpty()) {
				if(customfield.isEmpty() || customfield.trim()=="") {
					errormessage =  utils.getproperty("errormessages", "error_missing_customfield");
				}
				else {
					errormessage =  utils.getproperty("errormessages", "error_missing_subscriberid");
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


	private boolean issubscriberexists(String subscriberid, String listId) {
		boolean isexists = false;

		String para_getAPI = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xmlrequest>\r\n" + 
				"  <username>"+ username +"</username>\r\n" + 
				"  <usertoken>" + token + "</usertoken>\r\n" + 
				"  <requesttype>subscribers</requesttype>\r\n" + 
				"  <requestmethod>GetSubscribers</requestmethod>\r\n" + 
				"  <details>\r\n" + 
				"    <searchinfo>\r\n" + 
				"      <List>" + listId + "</List>\r\n" + 
				"    </searchinfo>\r\n" + 
				"  </details>\r\n" + 
				"</xmlrequest>";

		RestAssured.baseURI=uri;
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/xml");
		httpRequest.body(para_getAPI);
		Response response = httpRequest.request(Method.POST);
		String responsedata = response.getBody().asString();	


		String status = "";

		try {

			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(new InputSource(new StringReader(response.getBody().asString())));
			Element classElement = document.getRootElement();

			Element ele_status = classElement.getChild("status");
			status = ele_status.getText().toString();
			if(status.toLowerCase().trim().contains("success") ) {

				Element ele_data = classElement.getChild("data");
				Element ele_count = ele_data.getChild("count");

				String _count = ele_count.getText().toString();

				int count = 0;
				try {
					count = Integer.parseInt(_count);
				}
				catch(Exception e) {

				}
				
				if(count > 0) {
					Element ele_subscriberlist = ele_data.getChild("subscriberlist");
					List<Element> ele_items = ele_data.getChildren("item");
					
					int existscount = 0;
					if(ele_items.size() > 0) {
						for (Element items : ele_items) {

							if(existscount >1) {
								break;
							}
							Element ele_subscriberid = items.getChild("subscriberid");
							Element ele_listid = items.getChild("listid");

							String _res_subscriberid = ele_subscriberid.getText().toString();
							String _res_listid = ele_listid.getText().toString();
							
							if(_res_subscriberid.trim().toLowerCase().equals(subscriberid.toLowerCase().trim())) {
								if(_res_listid.trim().toLowerCase().equals(listId.toLowerCase().trim())) {
									isexists = true;
									existscount = existscount + 1;
								}
							}
							
							
						}
					}
				}

			}
		}
		catch(Exception e) {

		}


		if(status.toLowerCase().trim().equals("success")) {

		}

		return isexists;
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
