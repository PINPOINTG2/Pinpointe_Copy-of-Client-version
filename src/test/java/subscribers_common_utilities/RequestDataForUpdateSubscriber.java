package subscribers_common_utilities;

import java.util.ArrayList;

import common_base.BaseClass;
import common_utilities.TestData;
import common_utilities.Utilities;


public class RequestDataForUpdateSubscriber  extends BaseClass {

	private Utilities utils = new Utilities();
	private TestData data = new TestData();
	
	public String requestdata(String[] body, ArrayList<String[]> fielddata) {
		String request = "";
		
		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<xmlrequest>");
		builder.append("<username>"+ username +"</username>");
		builder.append("<usertoken>"+ token +"</usertoken>");
		builder.append("<requesttype>subscribers</requesttype>");
		builder.append("<requestmethod>UpdateSubscriber</requestmethod>");
		builder.append("<details>");
		
		builder.append("<emailaddress>"+ body[1] +"</emailaddress>");
		if(!body[2].trim().isEmpty()) {
			builder.append("<add_to_autoresponders>"+ body[2] +"</add_to_autoresponders>");
		}
		
		builder.append("<mailinglist>"+ body[3] +"</mailinglist>");
		builder.append("<format>"+ body[4] +"</format>");
		builder.append("<confirmed>"+ body[5] +"</confirmed>");
		if(body[6].toLowerCase().contains("yes")) {
			builder.append("<customfields>");
		
			for (String[] fields : fielddata) {
				if(body[0].toLowerCase().equalsIgnoreCase(fields[0].toLowerCase())) {
					builder.append("<item>");
					builder.append("<fieldid>"+ fields[1] +"</fieldid>");
					try {
						builder.append("<value>"+ fields[2] +"</value>");
						}
						catch (Exception e) {
							builder.append("<value></value>");
						}
					builder.append("</item>");
				}
			}
			
			builder.append("</customfields>");
		}
		String[] tags = body[7].toString().replace("\"", "").split("&");   
		for (String tag : tags) {
			builder.append("<tag>"+ tag.trim() +"</tag>");
		}
		builder.append("</details>");
		builder.append("</xmlrequest>");
		
		request = builder.toString();
		return request;
	}
}