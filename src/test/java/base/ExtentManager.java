package base;

//https://www.youtube.com/watch?v=Lapn6VLoqdc&t=278s

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.util.Date;


public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports createInstance(){
        String fileName=getReportName();
        String directory=System.getProperty("user.dir")+"\\reports\\";
        new File(directory).mkdirs();
        String path=directory+fileName;
        ExtentHtmlReporter htmlReporter=new ExtentHtmlReporter(path);
        htmlReporter=new ExtentHtmlReporter("./reports/extent.html");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setDocumentTitle("Automation Report with Extent Report");
        htmlReporter.config().setReportName("Automation Test Results");
        htmlReporter.config().setTheme(Theme.STANDARD);

        extent=new ExtentReports();
        extent.setSystemInfo("Organization","San Automation");
        extent.setSystemInfo("Browser","Chrome");
        extent.attachReporter(htmlReporter);
        return extent;
    }

    private static String getReportName() {
        Date d=new Date();
        String fileName="AutomationReport_"+ d.toString().replace(":","_").replace(" ","_")+ ".png";
        return  fileName;
    }


}
