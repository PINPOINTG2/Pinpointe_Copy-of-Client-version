package tags_common_utilities;

import common_base.BaseClass;
import common_utilities.Utilities;

public class RequestDataforGetTags extends BaseClass {

	private Utilities utils = new Utilities();

	public String requestdata(String[] body) {
		String request = "";

		StringBuilder builder = new StringBuilder();

		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<xmlrequest>");
		builder.append("<username>" +  username  +"</username>");
		builder.append("<usertoken>" + token + "</usertoken>");
		builder.append("<requesttype>tags</requesttype>");
		builder.append("<requestmethod>GetTags</requestmethod>");
		builder.append("<details>");
		builder.append("<searchinfo>");
		if(body[1].trim() != "" && body[1].trim() != null) {
			builder.append("<List>"+ body[1] +"</List>");
		}	
		if(body[2].trim() != "" && body[2].trim() != null) {
			builder.append("<Email>" + body[2] + "</Email>");
		}
		
		
		builder.append("</searchinfo>");
		builder.append("</details>");
		builder.append("</xmlrequest>");

		request = builder.toString();
		return request;
	}

}

