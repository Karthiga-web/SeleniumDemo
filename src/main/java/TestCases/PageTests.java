package TestCases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import config.Constants;
import frameworkModel.CartPage;
import frameworkModel.ExcelWorkBook;
import frameworkModel.HomePage;
import frameworkModel.LoginPage;
import frameworkModel.SearchShampooPage;

public class PageTests {
	static double item = 0;
	static double count = 0;
	ExcelWorkBook book = new ExcelWorkBook();
	private static Logger log = LogManager.getLogger(PageTests.class.getName());
	@Test
	public void home() throws InterruptedException, IOException {
		log.debug("Setting chrome driver property");
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\grkar\\OneDrive\\Documents\\Selenium\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 6);
		driver.navigate().to(book.getData("URL"));
		
		homepage(driver);
		loginpage(driver);
		searchShampooPage(driver,wait);
		
		searchSamsung(driver);
		navigation(driver, wait);
//		Thread.sleep(2000);
		cartpage(driver, wait);
		signOut(driver);
		driver.close();
	}
	
	private void signOut(WebDriver driver) {
		CartPage cartPage = new CartPage(driver);
		Actions a = new Actions(driver);
		a.moveToElement(cartPage.cartPageMoveToElement()).build().perform();
		cartPage.signOut().click();
		driver.close();
	}

	private void cartpage(WebDriver driver, WebDriverWait wait) {
		CartPage cartPage = new CartPage(driver);
		List<WebElement> list = cartPage.cartActiveItems();
		List<Double> quantity = new ArrayList<>(); 
		List<Double> price = new ArrayList<>();
		list.forEach(i -> {
			if (i.getAttribute(Constants.ATTRIBUTE_CLASS).contains(Constants.HAS_CLASS_LIST_ITEM)) {
				
				System.out.println(Double.parseDouble(i.getAttribute(Constants.DATA_PRICE)));
				quantity.add(Double.parseDouble(i.getAttribute(Constants.DATA_PRICE)));
				System.out.println(Double.parseDouble(i.getAttribute(Constants.DATA_QUANTITY)));
				price.add(Double.parseDouble(i.getAttribute(Constants.DATA_QUANTITY)));
				count += Double.parseDouble(i.getAttribute(Constants.DATA_QUANTITY));
				item += (Double.parseDouble(i.getAttribute(Constants.DATA_PRICE))
						* Double.parseDouble(i.getAttribute(Constants.DATA_QUANTITY)));
			}
		});
		
		String total = cartPage.finalAmount().getText();
		System.out.println(total);
		
		
		total = total.substring(1, total.length());
		total = total.replaceAll(Constants.TO_REMOVE_DOLLAR_COMMAS, "");
		Assert.assertEquals(item, Double.parseDouble(total));
		book.putCartData(quantity,price,total);
		deleteCartItems(driver,cartPage,wait);
	}

	private void deleteCartItems(WebDriver driver, CartPage cartPage, WebDriverWait wait) {
		List<WebElement> list = driver.findElements(By.xpath(Constants.CART_ACTIVE_ITEMS + "/descendant::*[contains(@value,'Delete')]"));
		for(int i = 0; i <= list.size();i++) {
			list.get(i).click();
			list = driver.findElements(By.xpath(Constants.CART_ACTIVE_ITEMS + "/descendant::*[contains(@value,'Delete')]"));
		};
	}

	private static void navigation(WebDriver driver, WebDriverWait wait) {
		SearchShampooPage searchShampooPage = new SearchShampooPage(driver);
		List<WebElement> pages = searchShampooPage.pagination();
		while (!searchShampooPage.pagination().get(pages.size() - 1)
				.getAttribute(Constants.ATTRIBUTE_CLASS).contains(Constants.VALUE_DISABLED)) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(Constants.JAVASCRIPT_WINDOW_SCROLL);
			searchShampooPage.pagination().get(pages.size() - 1).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Constants.PAGINATION)));
			pages = searchShampooPage.pagination();
		}
		getResultItems(driver,searchShampooPage, wait);
	}
	
	private void searchSamsung(WebDriver driver) throws InterruptedException, IOException {
		SearchShampooPage searchShampooPage = new SearchShampooPage(driver);
		searchShampooPage.searchKeyword().sendKeys(Constants.SEARCH_KEYWORD_SAMSUNG);
		Thread.sleep(1000);
		searchShampooPage.searchKeyword().sendKeys(Keys.DOWN);
		searchShampooPage.serachButtonClick().click();
		Thread.sleep(2000);
		searchShampooPage.lowPrice().sendKeys(book.getData("MIN_AMOUNT"));
		searchShampooPage.highPrice().sendKeys(book.getData("MAX_AMOUNT"));
		driver.findElement(By.xpath(Constants.HIGH_PRICE + "/following-sibling::span/span/input")).click();

	}
	private static void getResultItems(WebDriver driver, SearchShampooPage searchShampooPage, WebDriverWait wait) {
		List<WebElement> results = searchShampooPage.filterResults();
		if (results.size() >= 2) {
			results.get(results.size() - 2).findElement(By.xpath(Constants.IMAGE_CLICK))
					.click();
		}
		try {
			String a = searchShampooPage.newAccordionRow().getAttribute(Constants.ATTRIBUTE_CLASS);
			if (a.contains(Constants.ACTIVE_ACCORDIAN)) {
				addToCart(driver, searchShampooPage, wait);
			} else {
				searchShampooPage.newAccordionRow().click();
				addToCart(driver, searchShampooPage, wait);
			}
		} catch (RuntimeException ee) {
			if (ee.toString().contains(Constants.NO_SUCH_ELEMENT_EXCEPTION)) {
				log.error("NO_SUCH_ELEMENT_EXCEPTION");
				addToCart(driver,searchShampooPage, wait);
			}
		}
	}
	
	private static void addToCart(WebDriver driver, SearchShampooPage searchShampooPage, WebDriverWait wait) {
		CartPage cartPage = new CartPage(driver);
		try {
			searchShampooPage.addToCartButton().click();
			try {
				wait.until(ExpectedConditions.elementToBeClickable(By.id(Constants.SIDE_SHEET_SUDDEN_POPUP)));
				if (cartPage.sideSheetSuddenPopup().isDisplayed()) {
					cartPage.sideSheetSuddenPopup().click();
				}
				wait.until(ExpectedConditions.elementToBeClickable(By.id(Constants.SIDE_SHEET_IN_CART_PAGE)));
				if (cartPage.sideSheetInCartPage().isDisplayed()) {
					cartPage.sideSheetInCartPage().click();
				}
			} catch (RuntimeException e) {
				log.error("RuntimeException on sidepopup");
				wait.until(ExpectedConditions.elementToBeClickable(By.id(Constants.CART_CLICK_BUTTON)));
				searchShampooPage.cartPage().click();
			}
			wait.until(ExpectedConditions.elementToBeClickable(By.id(Constants.CART_CLICK_BUTTON)));
			searchShampooPage.cartPage().click();
		} catch (RuntimeException ee) {
			if (ee.toString().contains(Constants.NO_SUCH_ELEMENT_EXCEPTION)) {
				log.error("NO_SUCH_ELEMENT_EXCEPTION on add to cart button click");
				try {
					searchShampooPage.addToCartButton().click();
					searchShampooPage.cartPage().click();
				} catch (RuntimeException e) {
					log.error("RuntimeException when clicking cartpage");
					searchShampooPage.cartPage().click();
				}
			}
		}
	}
	private void sortingResults(WebDriver driver, SearchShampooPage searchShampooPage, WebDriverWait wait) {
		Select sortDropDown = new Select(searchShampooPage.sortFilter());
		sortDropDown.selectByValue(Constants.SORTING_ORDER);
		Assert.assertEquals(Constants.PRICE_LOW_TO_HIGH, sortDropDown.getFirstSelectedOption().getText().toString());
	}
	private void filterSetting(WebDriver driver, SearchShampooPage searchShampooPage, WebDriverWait wait) {
		searchShampooPage.freeCancellation().click();
		wait.until(ExpectedConditions.visibilityOf(searchShampooPage.freeCancellation()));
		Assert.assertTrue(searchShampooPage.freeCancellation().isEnabled());
		wait.until(ExpectedConditions.visibilityOf(searchShampooPage.starRating()));
		searchShampooPage.starRating().click();
		wait.until(ExpectedConditions.visibilityOf(searchShampooPage.starRating()));
		Assert.assertTrue(searchShampooPage.starRating().isEnabled());

		List<WebElement> hairFilter = searchShampooPage.hairCondition();
		System.out.println(hairFilter.size());
		hairFilter.stream().forEach(item -> {
			System.out.println(item.getAttribute(Constants.ATTRIBUTE_ARIA_LABEL).toLowerCase());
			try {
				if (item.getAttribute(Constants.ATTRIBUTE_ARIA_LABEL).toLowerCase().equalsIgnoreCase(book.getData("HAIR_CONDITION"))) {
					item.click();
					Assert.assertTrue(item.isEnabled());
				}
			} catch (IOException e) {
				log.error("IOException on getting data from excel");
				e.printStackTrace();
			}
		});
	}
	private void searchShampooPage(WebDriver driver, WebDriverWait wait) throws IOException {
		SearchShampooPage searchShampooPage = new SearchShampooPage(driver);
		searchShampooPage.searchKeyword().sendKeys(book.getData("SEARCH_KEYWORD_SHAMPOO"));
		searchShampooPage.serachButtonClick().click();
		driver.navigate().back();
		searchShampooPage.searchKeyword().sendKeys(book.getData("SEARCH_KEYWORD_SHAMPOO"));
		searchShampooPage.searchKeyword().sendKeys(Keys.ENTER);
		filterSetting(driver,searchShampooPage,wait);
		sortingResults(driver,searchShampooPage,wait);
		getResultItems(driver,searchShampooPage,wait);
	}
	private void loginpage(WebDriver driver) throws IOException {
		LoginPage loginpage = new LoginPage(driver);
		loginpage.email().sendKeys(book.getData("EMAIL"));;
		loginpage.continueClick().click();;
		loginpage.password().sendKeys(book.getData("PASSWORD"));
		loginpage.submitClick().click();
	}
	private void homepage(WebDriver driver) {
		HomePage homepage = new HomePage(driver);
		Actions a = new Actions(driver);
		a.moveToElement(homepage.signinClick()).build().perform();
		homepage.signin().click();
	}
}
