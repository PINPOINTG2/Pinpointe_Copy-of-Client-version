package subscribers_common_utilities;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import common_utilities.Utilities;
import io.restassured.response.Response;

public class ParseGetSubscribersResponse {
	
	Utilities utils = new Utilities();
	RequestDataForGetSubscriber requestDataForGetSubscriber = new RequestDataForGetSubscriber();
	ResponseGeneral responseGeneralvalid = new ResponseGeneral();

	public HashMap<String, String> callGetSubscriberAndParse(String Listid, String emailid, String uri) {

		HashMap<String, String> responseDataparsed = new HashMap<String, String>();
		String[] request_body = new String[3];
		request_body[2] = Listid;
		request_body[1] = emailid;
		String parameter_Body = requestDataForGetSubscriber.requestdata(request_body);
		Response response = utils.api_Call(parameter_Body,uri);

		String responseData = response.getBody().asString();
		
		String responseCode = Integer.toString(response.getStatusCode());
		String responseTime = Long.toString(response.getTimeIn(TimeUnit.MILLISECONDS)) + "ms";

		responseDataparsed.put("request_body", parameter_Body);
		responseDataparsed.put("reponse_body", responseData);
		responseDataparsed.put("responseCode", responseCode);
		responseDataparsed.put("responseTime", responseTime);
		HashMap<String, String> responseelement = responseGeneralvalid.response_Status(response);
		String status = responseelement.get("status");
		String version = responseelement.get("version");
		String elapsed = responseelement.get("elapsed");
		responseDataparsed.put("status", status);
		responseDataparsed.put("version", version);
		responseDataparsed.put("elapsed", elapsed);
		if(status.equalsIgnoreCase("success")) {

			try {
				SAXBuilder saxBuilder = new SAXBuilder();
				Document document = saxBuilder.build(new InputSource(new StringReader(responseData)));
				Element classElement = document.getRootElement();
				Element response_data = classElement.getChild("data");

				Element count = response_data.getChild("count");
				String _count = "0";
				try {
				_count = count.getText().toString();
				}
				catch(Exception e) {
					_count = "0";
				}
				
				responseDataparsed.put("count", _count);

				String expected_tags = utils.getproperty("config", "Get-Subscribers-Items");

				List<String> expectedTags = new ArrayList<String>(Arrays.asList(expected_tags.split(",")));
				List<String> actualTags = new LinkedList();


				int _numberofCounts = Integer.parseInt(count.getText().toString());
				if(_numberofCounts >0) {
					Element subscriberlist = response_data.getChild("subscriberlist");
					List<Element> Items = subscriberlist.getChildren("item");

					int itemcount = 0;
					for (Element Item : Items) {
						itemcount = itemcount + 1;
						List<Element> chils = Item.getChildren();
						for (Element childElement : chils) {
							actualTags.add(childElement.getName().toString());
						}

						if(utils.compareStringArray(expectedTags, actualTags)) {
							responseDataparsed.put("Elements"+itemcount, "Expected Elements are appeared");
						}
						responseDataparsed.put("ExpectedTags"+itemcount, expectedTags.toString());
						responseDataparsed.put("ActualTags"+itemcount, actualTags.toString());


						Element subscriberid = Item.getChild("subscriberid"); 
						responseDataparsed.put("subscriberid"+itemcount, subscriberid.getText().toString());

						Element listid = Item.getChild("listid"); 
						responseDataparsed.put("listid"+itemcount, listid.getText().toString());

						Element emailaddress = Item.getChild("emailaddress"); 
						responseDataparsed.put("emailaddress"+itemcount, emailaddress.getText().toString());

						Element domainname = Item.getChild("domainname"); 
						responseDataparsed.put("domainname"+itemcount, domainname.getText().toString());

						Element format = Item.getChild("format"); 
						responseDataparsed.put("format"+itemcount, format.getText().toString());

						Element confirmed = Item.getChild("confirmed"); 
						responseDataparsed.put("confirmed"+itemcount, confirmed.getText().toString());

						Element confirmcode = Item.getChild("confirmcode");
						responseDataparsed.put("confirmcode"+itemcount, confirmcode.getText().toString());

						Element requestdate = Item.getChild("requestdate");
						responseDataparsed.put("requestdate"+itemcount, requestdate.getText().toString());

						Element requestip = Item.getChild("requestip");
						responseDataparsed.put("requestip"+itemcount, requestip.getText().toString());

						Element confirmdate = Item.getChild("confirmdate");
						responseDataparsed.put("confirmdate"+itemcount, confirmdate.getText().toString());

						Element confirmip = Item.getChild("confirmip"); 
						responseDataparsed.put("confirmip"+itemcount, confirmip.getText().toString());

						Element subscribedate = Item.getChild("subscribedate"); 
						responseDataparsed.put("subscribedate"+itemcount, subscribedate.getText().toString());

						Element updatedate = Item.getChild("updatedate");
						responseDataparsed.put("updatedate"+itemcount, updatedate.getText().toString());

						Element bounced = Item.getChild("bounced"); 
						responseDataparsed.put("bounced"+itemcount, bounced.getText().toString());

						Element unsubscribed = Item.getChild("unsubscribed"); 
						responseDataparsed.put("unsubscribed"+itemcount, unsubscribed.getText().toString());

						Element unsubscribeconfirmed = Item.getChild("unsubscribeconfirmed"); 
						responseDataparsed.put("unsubscribeconfirmed"+itemcount, unsubscribeconfirmed.getText().toString());

						Element feedbacklooped = Item.getChild("feedbacklooped");
						responseDataparsed.put("feedbacklooped"+itemcount, feedbacklooped.getText().toString());

						Element feedbackloopconfirmed = Item.getChild("feedbackloopconfirmed");
						responseDataparsed.put("feedbackloopconfirmed"+itemcount, feedbackloopconfirmed.getText().toString());

						Element itemstatus = Item.getChild("status"); 
						responseDataparsed.put("status"+itemcount, itemstatus.getText().toString());

						Element last_visitorid = Item.getChild("last_visitorid"); 
						responseDataparsed.put("last_visitorid"+itemcount, last_visitorid.getText().toString());

						Element formid = Item.getChild("formid");
						responseDataparsed.put("formid"+itemcount, formid.getText().toString());

						Element total_opens = Item.getChild("total_opens"); 
						responseDataparsed.put("total_opens"+itemcount, total_opens.getText().toString());

						Element total_clicks = Item.getChild("total_clicks");
						responseDataparsed.put("total_clicks"+itemcount, total_clicks.getText().toString());

						Element last_open = Item.getChild("last_open"); 
						responseDataparsed.put("last_open"+itemcount, last_open.getText().toString());

						Element last_click = Item.getChild("last_click");
						responseDataparsed.put("last_click"+itemcount, last_click.getText().toString());

						Element dateadded = Item.getChild("dateadded"); 
						responseDataparsed.put("dateadded"+itemcount, dateadded.getText().toString());

						Element activitystatus = Item.getChild("activitystatus"); 
						responseDataparsed.put("activitystatus"+itemcount, activitystatus.getText().toString());

						Element listname = Item.getChild("listname");
						responseDataparsed.put("listname"+itemcount, listname.getText().toString());

					}
				}
			} catch(JDOMException e) {
				e.printStackTrace();
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return responseDataparsed;
	}
}