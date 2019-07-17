package common_base;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class BaseClass {
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest logger;
	private String reportname = "";
	public String reportDetails = "";
	Singleton sintn;
	public String uri = "";
	public String username=  "";
	public String token = "";

	public BaseClass() {
		sintn = Singleton.getInstance();
		extent = sintn.extent;
		reportname = sintn.reportname;

		uri = sintn.uri;
		username = sintn.username;
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

	@BeforeMethod
	public void reportAdditional() {
		reportDetails = "";
	}

	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			Markup m = MarkupHelper.createCodeBlock(reportDetails, CodeLanguage.XML);
			logger.fail(m);
			logger.fail(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			Markup m = MarkupHelper.createCodeBlock(reportDetails, CodeLanguage.XML);
			logger.skip(m);
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			Markup m = MarkupHelper.createCodeBlock(reportDetails, CodeLanguage.XML);
			logger.pass(m);
		}
	}


}
