package servicenow;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.sukgu.Shadow;

public class Hibernate {
    ExtentReports extent;
    ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        // This matches the path in your Jenkins pipeline: target/reports/ExtentReport.html
        ExtentSparkReporter spark = new ExtentSparkReporter("target/reports/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @Test
    public void runWake() throws InterruptedException {
        test = extent.createTest("ServiceNow Hibernate Wake Test");
        
        ChromeDriver driver = new ChromeDriver();
        test.info("Browser launched");
        
        driver.get("https://dev355356.service-now.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        
        Shadow sh = new Shadow(driver);	
        String pageSource = driver.getPageSource();
        
        if(pageSource.contains("Instance Hibernating page")) {
            test.pass("Instance is hibernating. Starting wake process.");
            driver.findElement(By.xpath("//button[contains(text(),'Sign in')]")).click();
            sh.setImplicitWait(30);
            Thread.sleep(5000);
            sh.findElementByXPath("//span[text()='Sign In']").click();
            driver.findElement(By.xpath("//input[@name='username']")).sendKeys("harrishanimation@gmail.com");
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Harri123@");
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            test.pass("Wake command submitted successfully");
        } else {
            test.info("Instance is already awake.");
            driver.quit();
        }
    }

    @AfterSuite
    public void tearDown() {
        extent.flush(); // This line actually WRITES the file to disk
    }
}