package spring.testPetClinic.Utility;

import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
	
	public static ExtentSparkReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	
	public static void setExtent() {
		htmlReporter= new ExtentSparkReporter(System.getProperty("user.dir")+"/test-output/ExtentReport/"+"MyReport.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		try {
			htmlReporter.loadXMLConfig(System.getProperty("user.dir")+"/extent-config.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		extent.setSystemInfo("HostName", "MyHost");
		extent.setSystemInfo("ProjectName", "testPetClinic");
		extent.setSystemInfo("Tester", "Lakshmi");
		extent.setSystemInfo("OS", "Win11");
		extent.setSystemInfo("Browser", "Chrome");
	}
	public static void endReport() {
		extent.flush();
	}

}
