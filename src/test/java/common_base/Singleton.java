package common_base;

import java.util.Calendar;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import common_utilities.Utilities;

public class Singleton {

	private static Singleton single_instance = null; 
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest logger;
	public String reportname = "";
	public String uri = "";
	public String username=  "";
	public String password = "";
	public String token = "";
	
	Utilities utils = new Utilities();
	
	
	private Singleton() 
    { 		
		uri = System.getProperty("uri");
		username = System.getProperty("username");
		token = System.getProperty("token");

		if(uri == null || uri.isEmpty() || uri.trim() =="") {
			uri = utils.getproperty("config", "api-url");

			String[] uris = uri.split(",");
			uri = uris[0];
		}
		
		if(username == null || username.isEmpty() || username.trim() =="") {
			username = utils.getproperty("config", "username");

			String[] usernames = username.split(",");
			username = usernames[0];
		}
		
		if(token == null || token.isEmpty() || token.trim() =="") {
			token = utils.getproperty("config", "api-token");

			String[] tokens = token.split(",");
			token = tokens[0];
		}
		
		Date date= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		reportname = "Executereport_" + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + cal.get(Calendar.DATE) +"_"+ cal.get(Calendar.HOUR)+ cal.get(Calendar.MINUTE)+ cal.get(Calendar.SECOND);
				
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/report/Executereport.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name", "PinPointe - API");
		extent.setSystemInfo("Environment", "Test");
		htmlReporter.config().setDocumentTitle("PinPointe - API");
		htmlReporter.config().setReportName("Testrun report ");
		htmlReporter.config().setTheme(Theme.STANDARD);

    } 
	public static Singleton getInstance() 
    { 
        if (single_instance == null) {
            single_instance = new Singleton(); 
        }
  
        return single_instance; 
    } 

}
