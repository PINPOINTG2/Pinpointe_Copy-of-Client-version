package subscribers_common_utilities;

import java.util.ArrayList;

import common_utilities.TestData;
import common_utilities.Utilities;

public class RequestDataForCopyContactList {
	
	
	private Utilities utils = new Utilities();
	private TestData data = new TestData();
	
	public String requestdata(String[] body, ArrayList<String[]> fielddata) {
		String username = utils.getproperty("config", "username");
		String token = utils.getproperty("config", "api-token");
		String request = "";
		
		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<xmlrequest>");
		builder.append("<username>"+ username +"</username>");
		builder.append("<usertoken>"+ token +"</usertoken>");
		builder.append("<requesttype>databases</requesttype>");
		builder.append("<requestmethod>copylist</requestmethod>");
		builder.append("<details>");
		
		builder.append("<listid>"+  body[0] +"</listid>");
		builder.append("<name>"+  body[1] +"</name>");
		builder.append("<ownername>"+ body[2] +"</ownername>");
		builder.append(" <owneremail>"+ body[3] +" </owneremail>");
		builder.append("<replytoemail>"+ body[4] +"</replytoemail>");
		builder.append("</details>");
		builder.append("</xmlrequest>");
		
		request = builder.toString();
		return request;
	}

}
