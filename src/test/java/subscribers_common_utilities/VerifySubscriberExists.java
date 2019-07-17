package subscribers_common_utilities;

import java.util.HashMap;


public class VerifySubscriberExists  extends common_base.BaseClass {

	ParseGetSubscribersResponse parseGetSubResponse = new ParseGetSubscribersResponse();

	public boolean VerifyGetSubscribers(String mailinglistid, String emailId) {
		boolean isexists = false;

		HashMap<String, String> responseDataparsed = parseGetSubResponse.callGetSubscriberAndParse(mailinglistid, emailId, uri);

		int totalCount = 0;
		try {
		totalCount =Integer.parseInt(responseDataparsed.get("count"));
		}
		catch(NumberFormatException e) {
		}

		if(totalCount>0) {
			isexists = true;
		}

		return isexists;
	}

}
