package subscribers_common_utilities;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

public class ParseAddMultipleSubscriberResponse {

	public HashMap<String, String> parse(String response){
		HashMap<String, String> result = new HashMap<String, String>();
		
		String status = "";
		String success_quantity = "0";
		String success_ItemCount = "0";
		String failed_quantity = "0";
		String failed_ItemCount = "0";
		try {

			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(new InputSource(new StringReader(response)));
			Element classElement = document.getRootElement();
			try {
				Element ele_status = classElement.getChild("status");
				status = ele_status.getText().toString();
			}
			catch(Exception e) {
				status = "";
			}
			

			try {
				Element ele_data = classElement.getChild("data");
				Element ele_success = ele_data.getChild("success");
				Element ele_quantity = ele_success.getChild("quantity");
				Element ele_subscribers = ele_success.getChild("subscribers");
				List<Element> Items = ele_subscribers.getChildren("item");
				
				success_quantity = ele_quantity.getText().toString();
				success_ItemCount = "0";
				try {
					success_ItemCount = Integer.toString(Items.size());
				}
				catch (NumberFormatException e) {
				}
				
			}
			catch(Exception e) {
				success_quantity = "0";
				success_ItemCount = "0";
			}

			
			
			try {
				Element ele_data = classElement.getChild("data");
				Element ele_failed = ele_data.getChild("failed");
				Element ele_quantity = ele_failed.getChild("quantity");
				Element ele_subscribers = ele_failed.getChild("subscribers");
				List<Element> Items = ele_subscribers.getChildren("item");
				
				failed_quantity = ele_quantity.getText().toString();
				failed_ItemCount = "0";
				try {
					failed_ItemCount = Integer.toString(Items.size());
				}
				catch (NumberFormatException e) {
				}
				
			}
			catch(Exception e) {
				failed_quantity = "0";
				failed_ItemCount = "0";
			}
			

		}
		catch(Exception e) {

		}

		
		result.put("status", status);
		result.put("success_quantity", success_quantity);
		result.put("success_ItemCount", success_ItemCount);
		result.put("failed_quantity", failed_quantity);
		result.put("failed_ItemCount", failed_ItemCount);
		
		
		return result;
		
	}
	
	
}
