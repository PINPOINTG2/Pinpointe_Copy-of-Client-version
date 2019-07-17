package common_base;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class BulkBaseClass {
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest logger;
	private String reportname = "";
	public String reportDetails = "";
	Singleton sintn;
	public String uri = "";
	public String username=  "";
	public String password = "";
	public String token = "";
	public String methodName = "GetSubscriber";
	public long consolidateresponseTime = 0;
	public long maxtime =0;
	public long mintime=100000;
	public String consolidatestatus = "";
	public int totalAPICalls = 0;

	public BulkBaseClass() {
		sintn = Singleton.getInstance();
		extent = sintn.extent;
		reportname = sintn.reportname;

		uri = sintn.uri;
		username = sintn.username;
		password = sintn.password;
		token = sintn.token;
	}

	@BeforeSuite
	public void kickstart() {

	}

	@AfterSuite
	public void closeApplication() {
		extent.flush();
		File original = new File(System.getProperty("user.dir") + "/report/Executereport.html");
		File copy = new File(System.getProperty("user.dir") + "/old_report/"+ reportname +".html");
		try {
			FileUtils.copyFile(original, copy);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@BeforeTest
	public void reportAdditional() {
		reportDetails = "";
		consolidateresponseTime =0;
		maxtime =0;
		mintime=100000;
		consolidatestatus = "";
		totalAPICalls = 0;
		methodName = "";
	}

	@AfterTest
	public void getResults() throws Exception {
		reportDetails = reportDetails + "" + methodName;
		reportDetails = reportDetails + "\n\n Max Response Time: " + maxtime +"ms";
		reportDetails = reportDetails + "\n Min Response Time: " + mintime+"ms";

		long averageresponseTime = consolidateresponseTime/totalAPICalls ;

		reportDetails = reportDetails + "\n Average Response Time: " + averageresponseTime+"ms";

		reportDetails = reportDetails + "\n\nResponse Data\n=============" +consolidatestatus;
		
		Markup m = MarkupHelper.createCodeBlock(reportDetails, CodeLanguage.XML);
		logger.pass(m);
	}

}