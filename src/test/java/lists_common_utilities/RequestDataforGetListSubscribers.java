package lists_common_utilities;

import common_base.BaseClass;
import common_utilities.Utilities;

public class RequestDataforGetListSubscribers extends BaseClass {

	private Utilities utils = new Utilities();
	
	public String requestdata(String[] body) {
		String request = "";
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<xmlrequest>");
		builder.append("<username>" +  username  +"</username>");
		builder.append("<usertoken>" + token + "</usertoken>");
		builder.append("<requesttype>lists</requesttype>");
		builder.append("<requestmethod>GetListSubscribers</requestmethod>");
		builder.append("<details>");
		if(body[1] != null && body[1].trim() != "") {
			builder.append("<List>"+ body[1] +"</List>");
		}
		builder.append("</details>");
		builder.append("</xmlrequest>");
		
		request = builder.toString();
		return request;
	}

}

