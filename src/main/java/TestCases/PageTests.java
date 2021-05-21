package TestCases;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import config.Constants;
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
		WebDriverWait wait = new WebDriverWait(driver, 6);
		driver.navigate().to(Constants.URL);
//		driver.manage().window().fullscreen();
		
		homepage(driver);
		loginpage(driver);
		searchShampooPage(driver,wait);
		
		driver.close();
	}

	private void filterSetting(WebDriver driver, SearchShampooPage searchShampooPage, WebDriverWait wait) {
		searchShampooPage.filter2().click();
//		wait.until(ExpectedConditions..visibilityOf(searchShampooPage.filter2()));
		Assert.assertTrue(searchShampooPage.filter2().isSelected());
		wait.until(ExpectedConditions.visibilityOf(searchShampooPage.filter3()));
		searchShampooPage.filter3().click();
		wait.until(ExpectedConditions.visibilityOf(searchShampooPage.filter3()));
		Assert.assertTrue(searchShampooPage.filter3().isEnabled());

		List<WebElement> hairFilter = searchShampooPage.filter4();
		hairFilter.iterator().forEachRemaining(item -> {
			if (item.getText().toLowerCase().equalsIgnoreCase(Constants.HAIR_CONDITION)) {
				item.click();
				Assert.assertTrue(item.isEnabled());
			}
		});
	}

	private void searchShampooPage(WebDriver driver, WebDriverWait wait) {
		SearchShampooPage searchShampooPage = new SearchShampooPage(driver);
		searchShampooPage.searchKeyword().sendKeys(Constants.SEARCH_KEYWORD);
		searchShampooPage.serachButtonClick().click();
		driver.navigate().back();
		searchShampooPage.searchKeyword().sendKeys(Constants.SEARCH_KEYWORD);
		searchShampooPage.searchKeyword().sendKeys(Keys.ENTER);
		filterSetting(driver,searchShampooPage,wait);
	}

	private void loginpage(WebDriver driver) {
		LoginPage loginpage = new LoginPage(driver);
		loginpage.email().sendKeys(Constants.EMAIL);;
		loginpage.continueClick().click();;
		loginpage.password().sendKeys(Constants.PASSWORD);
		loginpage.submitClick().click();
	}

	private void homepage(WebDriver driver) {
		HomePage homepage = new HomePage(driver);
		Actions a = new Actions(driver);
		a.moveToElement(homepage.signinClick()).build().perform();
		homepage.signin().click();
	}
}
