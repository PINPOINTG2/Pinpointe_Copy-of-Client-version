package subscribers_common_utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import common_base.BaseClass;
import common_utilities.TestData;
import common_utilities.Utilities;

public class RequestDataForAddMultipleSubscribers extends BaseClass {

	private Utilities utils = new Utilities();
	private TestData data = new TestData();
	
	public String requestdata(String[] body, ArrayList<String[]> contacts, ArrayList<String[]> fields) {
		String request = "";
		

		String dateformat = "ddMMyyyyHHmmss";
    	DateFormat dateFormat = new SimpleDateFormat(dateformat);
    	Date date = new Date();
    	String suffix = dateFormat.format(date).toString();

		
		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<xmlrequest>");
		builder.append("<username>"+ username +"</username>");
		builder.append("<usertoken>"+ token +"</usertoken>");
		builder.append("<requesttype>subscribers</requesttype>");
		builder.append("<requestmethod>addsubscriberstolist</requestmethod>");
		builder.append("<details>");
		
		if(body[3].toLowerCase().contains("yes")) {
			builder.append("<option_update_if_exists>yes</option_update_if_exists>");
		}
		
		if(body[4].toLowerCase().contains("yes")) {
			builder.append("<add_to_autoresponders>yes</add_to_autoresponders>");
		}
		
		
		builder.append("<mailinglist>"+ body[1] +"</mailinglist>");
		
		for (String[] contact : contacts) {
			String[] contact_data = new String[6];
			try {
				contact_data[0] = contact[0];
				contact_data[1] = contact[1];
				contact_data[2] = contact[2];
				contact_data[3] = contact[3];
				contact_data[4] = contact[4];
				contact_data[5] = contact[5];
			}
			catch(IndexOutOfBoundsException e) {
				contact_data[5] = "";
			}

			if(body[0].toLowerCase().equalsIgnoreCase(contact[0].toLowerCase())) {
				builder.append("<contact>");
				contact_data[2] = contact_data[2].replace("@", suffix+"@");
				builder.append("<emailaddress>"+ contact_data[2] +"</emailaddress>");
				builder.append("<format>"+ contact_data[3] +"</format>");
				builder.append("<confirmed>"+ contact_data[4] +"</confirmed>");
			
				if(!contact_data[5].trim().isEmpty()) {
					String[] tags = contact_data[5].toString().replace("\"", "").split("&"); 
					for (String tag : tags) {
						builder.append("<tag>"+ tag.trim() +"</tag>");
					}
				}
				builder.append("<customfields>");
				for (String[] field : fields) {
					if(contact[1].toLowerCase().equalsIgnoreCase(field[0].toLowerCase())) {
						builder.append("<item>");
						builder.append("<fieldid>"+ field[1] +"</fieldid>");
						try {
						builder.append("<value>"+ field[2] +"</value>");
						}
						catch (Exception e) {
							builder.append("<value></value>");
						}
						builder.append("</item>");
					}
				}
				
				
				builder.append("</customfields>");

				builder.append("</contact>");
			}
		}

		
		
		String[] tags = body[2].toString().replace("\"", "").split("&");   
		for (String tag : tags) {
			builder.append("<tag>"+ tag.trim() +"</tag>");
		}
		builder.append("</details>");
		builder.append("</xmlrequest>");
		
		request = builder.toString();
		return request;
	}
	
	public String requestNegativedata(String[] body, ArrayList<String[]> contacts, ArrayList<String[]> fields) {
		String request = "";
		
		
		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<xmlrequest>");
		builder.append("<username>"+ username +"</username>");
		builder.append("<usertoken>"+ token +"</usertoken>");
		builder.append("<requesttype>subscribers</requesttype>");
		builder.append("<requestmethod>addsubscriberstolist</requestmethod>");
		builder.append("<details>");
		
		if(body[3].toLowerCase().contains("yes")) {
			builder.append("<option_update_if_exists>yes</option_update_if_exists>");
		}
		
		if(body[4].toLowerCase().contains("yes")) {
			builder.append("<add_to_autoresponders>yes</add_to_autoresponders>");
		}
			
		builder.append("<mailinglist>"+ body[1] +"</mailinglist>");
		
		for (String[] contact : contacts) {

			if(body[0].toLowerCase().equalsIgnoreCase(contact[0].toLowerCase())) {
				builder.append("<contact>");
				
				builder.append("<emailaddress>"+ contact[2] +"</emailaddress>");
				builder.append("<format>"+ contact[3] +"</format>");
				builder.append("<confirmed>"+ contact[4] +"</confirmed>");
				try {
					contact[5] = contact[5];
				}
				catch(IndexOutOfBoundsException e) {
					contact[5] = "";
				}
				if(!contact[5].trim().isEmpty()) {
					String[] tags = contact[5].toString().replace("\"", "").split("&"); 
					for (String tag : tags) {
						builder.append("<tag>"+ tag.trim() +"</tag>");
					}
				}
				builder.append("<customfields>");
				for (String[] field : fields) {
					if(contact[1].toLowerCase().equalsIgnoreCase(field[0].toLowerCase())) {
						builder.append("<item>");
						builder.append("<fieldid>"+ field[1] +"</fieldid>");
						try {
						builder.append("<value>"+ field[2] +"</value>");
						}
						catch (Exception e) {
							builder.append("<value></value>");
						}
						builder.append("</item>");
					}
				}
				
				
				builder.append("</customfields>");

				builder.append("</contact>");
			}
		}

		
		
		String[] tags = body[2].toString().replace("\"", "").split("&");   
		for (String tag : tags) {
			builder.append("<tag>"+ tag.trim() +"</tag>");
		}
		builder.append("</details>");
		builder.append("</xmlrequest>");
		
		request = builder.toString();
		return request;
	}

}
