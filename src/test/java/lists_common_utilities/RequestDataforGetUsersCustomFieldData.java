package lists_common_utilities;

import common_base.BaseClass;
import common_utilities.Utilities;

public class RequestDataforGetUsersCustomFieldData extends BaseClass {

	private Utilities utils = new Utilities();

	public String requestdata(String[] body) {
		String request = "";

		StringBuilder builder = new StringBuilder();

		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<xmlrequest>");
		builder.append("<username>" +  username  +"</username>");
		builder.append("<usertoken>" + token + "</usertoken>");
		builder.append("<requesttype>lists</requesttype>");
		builder.append("<requestmethod>GetUserCustomFields</requestmethod>");
		builder.append("<details>");
		builder.append("<subscriberid>"+ body[1] +"</subscriberid>");
		builder.append("<mailinglist>"+ body[2] +"</mailinglist>");
		if(body[3].trim() !=null && body[3].trim() !="") {
			body[3] = body[3].replace("&", ",");
		}
		builder.append("<customfield>"+ body[3] +"</customfield>");
		builder.append("</details>");
		builder.append("</xmlrequest>");

		request = builder.toString();
		return request;
	}

}

