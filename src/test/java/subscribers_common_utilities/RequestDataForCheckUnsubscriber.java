package subscribers_common_utilities;

import common_base.BaseClass;
import common_utilities.Utilities;

public class RequestDataForCheckUnsubscriber extends BaseClass {

	private Utilities utils = new Utilities();

	public String requestdata(String[] body) {
		String request = "";

		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<xmlrequest>");
		builder.append("<username>"+ username +"</username>");
		builder.append("<usertoken>"+ token +"</usertoken>");
		builder.append("<requesttype>subscribers</requesttype>");
		builder.append("<requestmethod>IsUnSubscriber</requestmethod>");
		builder.append("<details>");
		builder.append("<emailaddress>"+ body[1] +"</emailaddress>");	
		builder.append("<listId>"+ body[2] +"</listId>");		
		builder.append("</details>");
		builder.append("</xmlrequest>");

		request = builder.toString();
		return request;
	}

}

