package subscribers_common_utilities;

import common_base.BaseClass;
import common_utilities.TestData;
import common_utilities.Utilities;

public class RequestDataForCheckListsubscriber  extends BaseClass {

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
		builder.append("<requestmethod>IsSubscriberOnList</requestmethod>");
		builder.append("<details>");
		builder.append("<Email>"+ body[1] +"</Email>");
		builder.append("<List>"+ body[2] +"</List>");
		builder.append("</details>");
		builder.append("</xmlrequest>");
		
		
		request = builder.toString();
		return request;
	}

}
