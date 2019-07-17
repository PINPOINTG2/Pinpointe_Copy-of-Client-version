package tags_common_utilities;

import java.util.Arrays;
import java.util.List;

import common_base.BaseClass;
import common_utilities.Utilities;

public class RequestDataforTagSubscribers extends BaseClass {

	private Utilities utils = new Utilities();
	
	public String requestdata(String[] body) {
		String request = "";
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<xmlrequest>");
		builder.append("<username>" +  username  +"</username>");
		builder.append("<usertoken>" + token + "</usertoken>");
		builder.append("<requesttype>tags</requesttype>");
		builder.append("<requestmethod>TagSubscribers</requestmethod>");
		builder.append("<details>");
		if(body[1] != null && body[1].trim() != "") {
			
			List<String> subscribers = Arrays.asList(body[1].split("&"));
			for (String string : subscribers) {
				builder.append("<subscriberid>"+ string +"</subscriberid>");
			}
			
			
		}
		if(body[2] != null && body[2].trim() != "") {
			List<String> tags = Arrays.asList(body[2].split("&"));
			for (String string : tags) {
				builder.append("<tag>"+ string +"</tag>");
			}
		}
		builder.append("</details>");
		builder.append("</xmlrequest>");
		
		request = builder.toString();
		return request;
	}

}

