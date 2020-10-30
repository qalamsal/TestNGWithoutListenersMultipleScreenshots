package Tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Test1 {
    public WebDriver wd;

    @BeforeClass
    public void setup(){
        //conf-start ...saving files to reports folder with name extent.html
        WebDriverManager.chromedriver().setup();
        wd=new ChromeDriver();
        wd.get("http://www.facebook.com");
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

}
