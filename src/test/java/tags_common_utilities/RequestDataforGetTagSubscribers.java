package tags_common_utilities;

import java.util.Arrays;
import java.util.List;

import common_base.BaseClass;
import common_utilities.Utilities;

public class RequestDataforGetTagSubscribers extends BaseClass {

	private Utilities utils = new Utilities();

	public String requestdata(String[] body) {
		String request = "";

		StringBuilder builder = new StringBuilder();

		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<xmlrequest>");
		builder.append("<username>" +  username  +"</username>");
		builder.append("<usertoken>" + token + "</usertoken>");
		builder.append("<requesttype>tags</requesttype>");
		builder.append("<requestmethod>GetTagSubscribers</requestmethod>");
		builder.append("<details>");
		builder.append("<tag>"+ body[1] +"</tag>");
		builder.append("</details>");
		builder.append("</xmlrequest>");

		request = builder.toString();
		return request;
	}

}

