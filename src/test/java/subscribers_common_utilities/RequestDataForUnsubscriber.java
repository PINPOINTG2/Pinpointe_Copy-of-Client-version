package subscribers_common_utilities;

import common_base.BaseClass;
import common_utilities.TestData;
import common_utilities.Utilities;

public class RequestDataForUnsubscriber  extends BaseClass {

	private Utilities utils = new Utilities();
	private TestData data = new TestData();
	
	public String requestdata(String[] body) {
		String request = "";
		
		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<xmlrequest>");
		builder.append("<username>"+  username +"</username>");
		builder.append("<usertoken>"+  token +"</usertoken>");
		builder.append("<requesttype>subscribers</requesttype>");
		builder.append("<requestmethod>UnsubscribeSubscriber</requestmethod>");
		builder.append("<details>");
		builder.append("<emailaddress>"+ body[1] +"</emailaddress>");
		builder.append("<list>"+ body[2] +"</list>");
		builder.append("</details>");
		builder.append("</xmlrequest>");
		
		
		request = builder.toString();
		return request;
	}

}
