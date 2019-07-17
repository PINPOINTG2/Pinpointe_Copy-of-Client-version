package subscribers_common_utilities;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import common_utilities.Utilities;
import io.restassured.response.Response;

public class SubscribersGetDataFromResponse {

	
	Utilities utils = new Utilities();
	
	public HashMap<String, String> response_data(Response response) {
		HashMap<String, String> status = new HashMap<String, String>();
		String responseData = response.getBody().asString();
		
		try {
	         SAXBuilder saxBuilder = new SAXBuilder();
	         Document document = saxBuilder.build(new InputSource(new StringReader(responseData)));
	         Element classElement = document.getRootElement();
	         Element response_data = classElement.getChild("data");

	         status.put(response_data.getName(), response_data.getText().toString());
	        
	         
	      } catch(JDOMException e) {
	         e.printStackTrace();
	      } catch(IOException ioe) {
	         ioe.printStackTrace();
	      }
		
		
		return status;
	}
	
	
	
}
