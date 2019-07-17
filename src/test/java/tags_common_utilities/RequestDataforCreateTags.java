package tags_common_utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import common_base.BaseClass;
import common_utilities.Utilities;

public class RequestDataforCreateTags extends BaseClass {

	private Utilities utils = new Utilities();

	public String requestdata(ArrayList<String[]> body) {
		String request = "";
		
		String dateformat = "ddMMyyyyHHmmss";
    	DateFormat dateFormat = new SimpleDateFormat(dateformat);
    	Date date = new Date();
    	String suffix = dateFormat.format(date).toString();

		StringBuilder builder = new StringBuilder();

		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<xmlrequest>");
		builder.append("<username>" +  username  +"</username>");
		builder.append("<usertoken>" + token + "</usertoken>");
		builder.append("<requesttype>tags</requesttype>");
		builder.append("<requestmethod>CreateTags</requestmethod>");
		builder.append("<details>");
		if(body.size() > 0) {
			for (String[] strings : body) {
				builder.append("<tag>");
				strings[1] = strings[1]+suffix;
				builder.append("<name>"+ strings[1] + "</name>");
				builder.append("<description>" + strings[2] +"</description>");
				builder.append("</tag>");
			}
		}
		builder.append("</details>");
		builder.append("</xmlrequest>");

		request = builder.toString();
		return request;
	}
	

	public String requestdata(String[] body, ArrayList<String[]> tags) {
		String request = "";

		StringBuilder builder = new StringBuilder();

		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<xmlrequest>");
		builder.append("<username>" +  username  +"</username>");
		builder.append("<usertoken>" + token + "</usertoken>");
		builder.append("<requesttype>tags</requesttype>");
		builder.append("<requestmethod>CreateTags</requestmethod>");
		builder.append("<details>");
		
		for (String[] tag : tags) {
			if(body[1].trim().toLowerCase().equals(tag[0].trim().toLowerCase())) {
				String[] tags_data = new  String[3];
				
				try {
					tags_data[0] = tag[0];
					tags_data[1] = tag[1];
					tags_data[2] = tag[2];
					
				}
				catch (ArrayIndexOutOfBoundsException e) {

					tags_data[2] = "";
				}
				
				builder.append("<tag>");
				builder.append("<name>"+ tags_data[1] + "</name>");
				builder.append("<description>" + tags_data[2] +"</description>");
				builder.append("</tag>");
			}
		}
		
		builder.append("</details>");
		builder.append("</xmlrequest>");

		request = builder.toString();
		return request;
	}
}
