package TestCases;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import frameworkModel.HomePage;

public class HomePageTests {
	@Test
	public void home() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\grkar\\OneDrive\\Documents\\Selenium\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to("https://www.amazon.com/");
		driver.manage().window().fullscreen();
		
		HomePage homepage = new HomePage(driver);
		Actions a = new Actions(driver);
		a.moveToElement(homepage.signinClick()).build().perform();
		homepage.signin().click();
		driver.close();
	}
}
