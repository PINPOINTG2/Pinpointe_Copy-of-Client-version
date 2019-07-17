package lists_common_utilities;

import common_base.BaseClass;
import common_utilities.Utilities;

public class RequestDataforFindList extends BaseClass {

	private Utilities utils = new Utilities();
	
	public String requestdata(String[] body) {
		String request = "";
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<xmlrequest>");
		builder.append("<username>" +  username  +"</username>");
		builder.append("<usertoken>" + token + "</usertoken>");
		builder.append("<requesttype>lists</requesttype>");
		builder.append("<requestmethod>findlist</requestmethod>");
		builder.append("<details>");
		if(body[1] != null && body[1].trim() != "") {
			builder.append("<listid>"+ body[1] +"</listid>");
		}
		if(body[2] != null && body[2].trim() != "") {
			builder.append("<name>"+ body[2] +"</name>");
		}
		builder.append("</details>");
		builder.append("</xmlrequest>");
		
		request = builder.toString();
		return request;
	}

}

