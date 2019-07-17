package lists_common_utilities;

import common_base.BaseClass;
import common_utilities.Utilities;

public class RequestDataforGetCustomFieldStructure extends BaseClass {

	private Utilities utils = new Utilities();

	public String requestdata(String[] body) {
		String request = "";

		StringBuilder builder = new StringBuilder();

		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<xmlrequest>");
		builder.append("<username>" +  username  +"</username>");
		builder.append("<usertoken>" + token + "</usertoken>");
		builder.append("<requesttype>lists</requesttype>");
		builder.append("<requestmethod>GetCustomFields</requestmethod>");
		builder.append("<details>");		
		builder.append("<listids>"+ body[1] +"</listids>");
		builder.append("</details>");
		builder.append("</xmlrequest>");

		request = builder.toString();
		return request;
	}

}

