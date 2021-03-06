package com.framework.pages;

import java.io.File;
import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.framework.utility.BrowserFactory;
import com.framework.utility.ConfigDataProvider;
import com.framework.utility.ExcelDataProvider;
import com.framework.utility.Helper;

public class BaseClass {
	
	public WebDriver driver;
	public ExcelDataProvider excel;
	public ConfigDataProvider config;
	public ExtentReports report;
	public ExtentTest logger;
	
	@BeforeSuite
	public void setupSuit() {
		excel = new ExcelDataProvider();
		config = new ConfigDataProvider();
		
		ExtentSparkReporter extent = new ExtentSparkReporter(new File(System.getProperty("user.dir")+"/Reports/CRMPro"+ Helper.getCurrentDateTime()+".html"));
		
		report = new ExtentReports();
		report.attachReporter(extent);	
	}
	@Parameters({"browser","env"})
	@BeforeClass
	public void setupTest(String browser,String env) throws MalformedURLException {
		driver=BrowserFactory.startApplication( driver, browser,config.getUrl(env));
	}

	//below is depricated method to pull data from config
	public void setupTest() throws MalformedURLException {
		driver=BrowserFactory.startApplication( driver, config.getBrowser(),config.getUrl("uat"));
	}
	
	@AfterClass
	public void tearDown() {
		BrowserFactory.quitBrowser(driver);
	}
	
	@AfterMethod
	public void tearDownMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			Helper.captureScreenshot(driver);
			logger.fail("Test Case aborted check screenshot",MediaEntityBuilder.createScreenCaptureFromPath(Helper.captureScreenshot(driver)).build());
		}else {
			logger.pass("Test Case Passed",MediaEntityBuilder.createScreenCaptureFromPath(Helper.captureScreenshot(driver)).build());
		}
			
		report.flush();
	}
	

}
