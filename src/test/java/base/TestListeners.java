package base;

import code.TestUsingListenersInExtentReport;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class TestListeners implements ITestListener {

    private static ExtentReports extent=ExtentManager.createInstance();
    private static ThreadLocal<ExtentTest> extentTest=new ThreadLocal<ExtentTest>();


    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test=extent.createTest(result.getTestClass().getName() + " : :"+
        result.getMethod().getMethodName()); //gives class name
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        String logText = "<b>Test Method " + result.getMethod().getMethodName() + "Sucess</b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
        extentTest.get().log(Status.PASS, m);

    }

    @Override
    public void onTestFailure(ITestResult result) {

        String methodName=result.getMethod().getMethodName();
        String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
        extentTest.get().fail("<font color=red> Exception Occured </font>" + exceptionMessage); //exception message

        WebDriver wd=((TestUsingListenersInExtentReport) result.getInstance()).wd;
        takeScreenShot(wd,result.getMethod().getMethodName());
        String path=getScreenshot(result.getMethod().getMethodName());
        System.out.println(path);
        try {
            extentTest.get().fail("<b><font color=red>" + "Screenshot of failure" + "</font></b>",
                    MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        } catch (IOException e) {
            e.printStackTrace();
        }


        //extentTest.get().fail("<b><font color=red>" + "Screenshot of failure" + "</font></b>" +
                    //"<div><img src="+path+"></div>");

            //extentTest.get().fail("<b><font color=red>" + "Screenshot of failure" + "</font></b>" +
              //      "<div><img src="+path+"></div>");
        String logText = "<b>Test Method " + methodName + "Failed</b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
        extentTest.get().log(Status.FAIL, m);

    }

    @Override
    public void onTestSkipped(ITestResult result) {

        String logText = "<b>Test Method " + result.getMethod().getMethodName() + "SKIPPED</b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
        extentTest.get().log(Status.SKIP, m);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) { //on failure

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
        if(extent!=null){
            extent.flush();
        }

    }

    //takes input wd and screenshot name,
    public String getScreenshot(String methodName)  {
        Date d=new Date();
        String fileName=methodName+ "_" + d.toString().replace(":","_").replace(" ","_")+ ".png";
        return  fileName;
    }

    public String takeScreenShot(WebDriver wd,String methodName) {
        String fileName=getScreenshot(methodName);
        System.out.println("filename" + fileName);
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
}
