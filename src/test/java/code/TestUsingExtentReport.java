package code;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class TestUsingExtentReport {


    public ExtentReports extent;
    public ExtentTest extentTest;
    public WebDriver wd;

    @BeforeClass
    public void setup(){
        //conf-start ...saving files to reports folder with name extent.html

        ExtentHtmlReporter htmlReporter;
        htmlReporter=new ExtentHtmlReporter("./reports/extent.html");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setDocumentTitle("Automation Report with Extent Report");
        htmlReporter.config().setReportName("Automation Test Results");
        htmlReporter.config().setTheme(Theme.STANDARD);

        extent=new ExtentReports();
        extent.setSystemInfo("Organization","San Automation");
        extent.setSystemInfo("Browser","Chrome");
        extent.attachReporter(htmlReporter);


        //conf-end
    }

    @BeforeMethod
    public void setWd(){
        WebDriverManager.chromedriver().setup();
        wd=new ChromeDriver();
    }

    @Test(priority = 0,groups = {"Regression"})
    public void passTest(){
            extentTest=extent.createTest("Pass Test");
            Assert.assertTrue(true);
            //extentTest.log(Status.PASS,"Test Passed");
    }

    @Test(priority = 1,groups = {"Regression"})
    public void failTest1(){
        wd.get("http://www.gmail.com");
        extentTest=extent.createTest("Fail Test");
        Assert.fail("Executing Failed Test Method");
        //System.out.println("Regression1");
        //System.out.println("Regression2");
    }

    @Test(priority = 2,groups = {"Regression"})
    public void failTest2(){
        wd.get("http://www.facebook.com");
        extentTest=extent.createTest("Fail Test");
        Assert.fail("Executing Failed Test Method");
        //System.out.println("Regression1");
        //System.out.println("Regression2");
    }

    @Test(priority = 3,groups = {"Regression"})
    public void failTest22(){

        wd.get("http://www.sandytech.net");
        extentTest=extent.createTest("Fail Test");
        Assert.fail("Executing Failed Test Method");
        //System.out.println("Regression1");
        //System.out.println("Regression2");
    }
//
    @Test(priority = 3,groups = {"Regression"}) //
    public void skipTest(){
        extentTest=extent.createTest("Skip Test");
        //Assert.assertTrue(true);
        throw new SkipException("Skipping - This is not ready for testing ");
        //System.out.println("Regression1");
        //System.out.println("Regression2");
    }


    //common method to handle , test pass,fail or skip in extent report
    //executes after each test methods,  if pass or fail or skip
    @AfterMethod
    public void getResult(ITestResult result) throws IOException {
        String methodName=result.getMethod().getMethodName();
        if(result.getStatus() == ITestResult.FAILURE) {

            String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
            extentTest.fail("<font color=red> Exception Occured </font>" + exceptionMessage); //exception message

            takeScreenShot(result.getMethod().getMethodName());
            String path=getScreenshot(result.getMethod().getMethodName());
            System.out.println(path);
            extentTest.fail("<b><font color=re>" + "Screenshot of failure" + "</font></b>",
            MediaEntityBuilder.createScreenCaptureFromPath(path).build());

            //extentTest.fail("<b><font color=red>" + "Screenshot of failure" + "</font></b>" +
                  //"<div><img src="+path+"></div>");

            String logText = "<b>Test Method " + methodName + "Failed</b>";
            Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
            extentTest.log(Status.FAIL, m);
            wd.close();
        }

        else if(result.getStatus() == ITestResult.SUCCESS) {
            String logText = "<b>Test Method " + methodName + "Sucess</b>";
            Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
            extentTest.log(Status.PASS, m);
        }
        else {
            String logText = "<b>Test Method " + methodName + "Skipped</b>";
            Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
            extentTest.log(Status.SKIP,m);
        }
    }


    //takes input wd and screenshot name,
    public String getScreenshot(String methodName) throws IOException {
        Date d=new Date();
        String fileName=methodName+ "_" + d.toString().replace(":","_").replace(" ","_")+ ".png";
        return  fileName;
    }

    public synchronized String takeScreenShot(String methodName) throws IOException {
        String fileName=getScreenshot(methodName);
        String directory=System.getProperty("user.dir") + "\\reports\\";
        new File(directory).mkdirs();
        String path=directory+fileName;

        try{
            File screeshot=((TakesScreenshot) wd).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screeshot,new File(path));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return  path;
    }

    @AfterClass
    public void close(){
        extent.flush();
        System.out.println("close called");
        //wd.quit();
    }
}
