package com.framework.testcases;


import org.testng.ITestContext;
import org.testng.annotations.Test;
import com.framework.pages.BaseClass;
import com.framework.pages.HomePage;
import com.framework.pages.LoginPage;

public class LoginToAppTest extends BaseClass{
    @Test
    public void loginApp(ITestContext testContext) throws InterruptedException{

        LoginPage lpage = new LoginPage(driver);
        logger = report.createTest(testContext.getName());
        logger.info("Starting Application");

        lpage.loginToApp(excel.getStringData("Sheet1", 0, 0), excel.getStringData("Sheet1", 0, 1));

        logger.info("Started Application");

        Thread.sleep(9000);
        HomePage hpage = new HomePage(driver);
        if (hpage.homeNav.isDisplayed()) {
            logger.pass("Login Done Successfully");
        }else {
            logger.fail("Failed to load home page");
        }

    }
}