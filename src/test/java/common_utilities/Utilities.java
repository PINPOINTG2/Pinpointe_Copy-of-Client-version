package common_utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utilities {

	public String getproperty(String propertyFile, String propertyName) {
		String propertyValue = "";
		FileInputStream propertyFileinput = null;
		String propertyFileLocation = "properties/"+propertyFile+".properties";
		File proFile = new File(propertyFileLocation);

		try {
			propertyFileinput = new FileInputStream(proFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Properties properties = new Properties();
		try {
			properties.load(propertyFileinput);
		} catch (Exception e) {
			e.printStackTrace();
		}
		propertyValue = properties.getProperty(propertyName).trim();

		return propertyValue;
	}

	public String prettyFormat(String input, int indent) {
		try {
			Source xmlInput = new StreamSource(new StringReader(input));
			StringWriter stringWriter = new StringWriter();
			StreamResult xmlOutput = new StreamResult(stringWriter);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformerFactory.setAttribute("indent-number", indent);
			Transformer transformer = transformerFactory.newTransformer(); 
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(xmlInput, xmlOutput);
			return xmlOutput.getWriter().toString();
		} catch (Exception e) {
			return input;
		}
	}

	public String prettyFormat(String input) {
		return prettyFormat(input, 2);
	}

	public String epochTodate(String epoch) {
		long Long_epoch = Long.parseLong(epoch);
		Date date = new Date(Long_epoch * 1000L);
		DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
		String formatted = format.format(date);
		
		return formatted;
	}
	
	public String getCurrentDate() {
		DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date(System.currentTimeMillis());  
		format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
		String formatted = format.format(date);
		
		return formatted;		
	}
	
	public boolean compareStringArray(List<String> comparewith, List<String> compare) {
		boolean result = false;
		Collections.sort(comparewith);
		Collections.sort(compare);
		if(compare.equals(comparewith)) {
			result = true;
		}
		return result;
	}
	
	public Response api_Call(String request_para, String uri) {
		RestAssured.baseURI=uri;
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/xml");
		httpRequest.body(request_para);
		Response response = httpRequest.request(Method.GET);
		
		return response;
	}
}
