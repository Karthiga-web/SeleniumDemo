package TestCases;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import frameworkModel.HomePage;
import frameworkModel.LoginPage;
import frameworkModel.SearchShampooPage;

public class PageTests {
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
		
		LoginPage loginpage = new LoginPage(driver);
		loginpage.email().sendKeys("grkarthikagr@yahoo.com");;
		loginpage.continueClick().click();;
		loginpage.password().sendKeys("123456789");
		loginpage.submitClick().click();
		
		SearchShampooPage searchShampooPage = new SearchShampooPage(driver);
		searchShampooPage.searchKeyword().sendKeys("Pantene Shampoo");
		searchShampooPage.serachButtonClick().click();
		driver.navigate().back();
		searchShampooPage.searchKeyword().sendKeys("Pantene Shampoo");
		searchShampooPage.searchKeyword().sendKeys(Keys.ENTER);
		
		driver.close();
	}
}
