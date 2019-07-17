package subscribers_common_utilities;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import io.restassured.response.Response;

public class ResponseGeneral {
	
	public HashMap<String, String> response_Status(Response response) {
		HashMap<String, String> status = new HashMap<String, String>();
		String responseData = response.getBody().asString();
		
		try {
	         SAXBuilder saxBuilder = new SAXBuilder();
	         Document document = saxBuilder.build(new InputSource(new StringReader(responseData)));
	         Element classElement = document.getRootElement();
	         Element statusdata = classElement.getChild("status");
	         Element version = classElement.getChild("version");
	         Element elapsed = classElement.getChild("elapsed");

	         status.put(statusdata.getName(), statusdata.getText().toString());
	         status.put(version.getName(), version.getText().toString());
	         status.put(elapsed.getName(), elapsed.getText().toString());
	         
	         if(statusdata.getText().toString().equalsIgnoreCase("FAILED")) {
	        	  Element errormessage = classElement.getChild("errormessage");
	 	         Element errordetail = classElement.getChild("errordetail");
	        	 status.put(errormessage.getName(), errormessage.getText().toString());
	        	 status.put(errordetail.getName(), errordetail.getText().toString());
	         }
	         
	         
	      } catch(JDOMException e) {
	         e.printStackTrace();
	      } catch(IOException ioe) {
	         ioe.printStackTrace();
	      }
		
		
		return status;
	}

	
	public HashMap<String, String> response_Status(String response) {
		HashMap<String, String> status = new HashMap<String, String>();
		String responseData = response;
		
		try {
	         SAXBuilder saxBuilder = new SAXBuilder();
	         Document document = saxBuilder.build(new InputSource(new StringReader(responseData)));
	         Element classElement = document.getRootElement();
	         Element statusdata = classElement.getChild("status");
	         Element version = classElement.getChild("version");
	         Element elapsed = classElement.getChild("elapsed");

	         status.put(statusdata.getName(), statusdata.getText().toString());
	         status.put(version.getName(), version.getText().toString());
	         status.put(elapsed.getName(), elapsed.getText().toString());
	         
	         if(statusdata.getText().toString().equalsIgnoreCase("FAILED")) {
	        	  Element errormessage = classElement.getChild("errormessage");
	 	         Element errordetail = classElement.getChild("errordetail");
	        	 status.put(errormessage.getName(), errormessage.getText().toString());
	        	 status.put(errordetail.getName(), errordetail.getText().toString());
	         }
	         
	         
	      } catch(JDOMException e) {
	         e.printStackTrace();
	      } catch(IOException ioe) {
	         ioe.printStackTrace();
	      }
		
		
		return status;
	}
}
