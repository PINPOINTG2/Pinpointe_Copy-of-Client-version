package campaign_common_utilities;

import java.util.ArrayList;

import common_base.BaseClass;
import common_utilities.Utilities;

public class RequestDataforCreateNewCampaign extends BaseClass {

	private Utilities utils = new Utilities();

	public String requestdata(String[] body) {
		String request = "";

		StringBuilder builder = new StringBuilder();

		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<xmlrequest>");
		builder.append("<username>" +  username  +"</username>");
		builder.append("<usertoken>" + token + "</usertoken>");
		builder.append("<requesttype>newsletters</requesttype>");
		builder.append("<requestmethod>UploadCampaign</requestmethod>");
		builder.append("<details>");
		builder.append("<name>" + body[1] + "</name>");
		builder.append("<subject>" + body[2] + "</subject>");
		builder.append("<htmlbody>" + body[3] + "</htmlbody>");
		builder.append("<textbody>" +  body[4] + "</textbody>");
		builder.append("<format>" +  body[5] + "</format>");
		builder.append("<active>" +  body[6] + "</active>");
		builder.append("<archive>" +  body[7] + "</archive>");
		builder.append("</details>");
		builder.append("</xmlrequest>");

		request = builder.toString();
		return request;
	}
}
