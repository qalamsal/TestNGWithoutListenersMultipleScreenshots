package code;


import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class TestUsingListenersInExtentReport {

    public WebDriver wd;

    @BeforeClass
    public void setup(){
        //conf-start ...saving files to reports folder with name extent.html
        WebDriverManager.chromedriver().setup();
        wd=new ChromeDriver();
        wd.get("http://www.gmail.com");
        //conf-end
    }

    @Test(priority = 0)
    public void passTest(){
        System.out.println("Sucessful Test");
    }

    @Test(priority = 1)
    public void failTest(){
        System.out.println("Failed Test");
        Assert.fail("Executing Fail Method");

    }
    //
    @Test(priority = 2) //
    public void skipTest(){
        System.out.println("Skipped Test");
        throw  new SkipException("Executing the Skipped Method");
    }


    //common method to handle , test pass,fail or skip in extent report
    //executes after each test methods,  if pass or fail or skip
    @AfterMethod
    public void getResult(ITestResult result) throws IOException {
    }

   }
