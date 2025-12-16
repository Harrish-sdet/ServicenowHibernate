package servicenow;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.github.sukgu.Shadow;

public class Hibernate {
	@Test
	public void runWake() throws InterruptedException {
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://dev355356.service-now.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		Shadow sh = new Shadow(driver);	
String pageSource = driver.getPageSource();
		
		if(pageSource.contains("Instance Hibernating page")) {
			driver.findElement(By.xpath("//button[contains(text(),'Sign in')]")).click();
			sh.setImplicitWait(15);
			sh.findElementByXPath("//span[text()='Sign in']").click();
			driver.findElement(By.xpath("//input[@name='username']")).sendKeys("harrishanimation@gmail.com");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//button[@type='submit']")).click();
			driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Harri123@");
			driver.findElement(By.xpath("//button[@type='submit']")).click();
		}
		else {
			driver.quit();
		}
		
	}
}
