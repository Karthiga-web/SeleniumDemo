package selenium.seleniumdemo;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

public class App {
	static double item = 0;
	static double count = 0;

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\grkar\\OneDrive\\Documents\\Selenium\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, 6);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to("https://www.amazon.com/");
		driver.manage().window().fullscreen();
		signinMethod(driver);
		searchShampoo(driver);
		filterSetting(driver);
		sortingResults(driver);
		getResultItems(driver, wait);
//		searchSamsung(driver);
//		navigation(driver, wait);
//		Thread.sleep(2000);
//		getResultItems(driver, wait);
		cartpage(driver, wait);
		signOut(driver);
	}

	private static void cartpage(WebDriver driver, WebDriverWait wait) {
		List<WebElement> list = driver.findElements(By.xpath("//*[@data-name='Active Items']/div"));
		list.forEach(i -> {
			if (i.getAttribute("class").contains("sc-list-item")) {
				System.out.println(Double.parseDouble(i.getAttribute("data-price")));
				System.out.println(Double.parseDouble(i.getAttribute("data-quantity")));
				count += Double.parseDouble(i.getAttribute("data-quantity"));
				item += (Double.parseDouble(i.getAttribute("data-price"))
						* Double.parseDouble(i.getAttribute("data-quantity")));
			}
		});
		String total = driver.findElement(By.id("sc-subtotal-amount-activecart")).getText();
		total = total.substring(1, total.length());
		total = total.replaceAll("[,$\\s]", "");
		Assert.assertEquals(item, Double.parseDouble(total));
	}

	private static void navigation(WebDriver driver, WebDriverWait wait) {
		List<WebElement> pages = driver.findElements(By.xpath("//*[@class='a-pagination']/li"));
		while (!driver.findElements(By.xpath("//*[@class='a-pagination']/li")).get(pages.size() - 1)
				.getAttribute("class").contains("a-disabled")) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,5800)");
			driver.findElements(By.xpath("//*[@class='a-pagination']/li")).get(pages.size() - 1).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='a-pagination']/li")));
			pages = driver.findElements(By.xpath("//*[@class='a-pagination']/li"));
		}
	}

	private static void searchSamsung(WebDriver driver) throws InterruptedException {
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("samsung laptop");
		Thread.sleep(1000);
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(Keys.DOWN);
		driver.findElement(By.id("nav-search-submit-button")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("low-price")).sendKeys("250");
		driver.findElement(By.id("high-price")).sendKeys("500");
		driver.findElement(By.xpath("//*[@id='high-price']/following-sibling::span/span/input")).click();
	}

	private static void getResultItems(WebDriver driver, WebDriverWait wait) throws InterruptedException {
		List<WebElement> results = driver.findElements(By.xpath("//*[@data-component-type='s-search-result']"));
		if (results.size() >= 2) {
			results.get(results.size() - 2).findElement(By.xpath("//*[@data-component-type='s-product-image']/a/div"))
					.click();
		}
		try {
			String a = driver.findElement(By.xpath("//*[@id='newAccordionRow']")).getAttribute("class");
			if (a.contains("a-accordion-active")) {
				addToCart(driver, wait);
			} else {
				driver.findElement(By.xpath("//*[@id='newAccordionRow']")).click();
				addToCart(driver, wait);
			}
		} catch (RuntimeException ee) {
			if (ee.toString().contains("NoSuchElementException")) {
				addToCart(driver, wait);
			}
		}
	}

	private static void addToCart(WebDriver driver, WebDriverWait wait) throws InterruptedException {
		try {
//			WebElement staticDropdown = driver.findElement(By.name("quantity"));
//			Select dropDown = new Select(staticDropdown);
//			dropDown.selectByValue("2");
//			Thread.sleep(1000);
			driver.findElement(By.id("add-to-cart-button")).click();
			try {
				wait.until(ExpectedConditions.elementToBeClickable(By.id("attachSiNoCoverage")));
				driver.findElement(By.id("attachSiNoCoverage")).click();
				if (driver.findElement(By.id("attach-sidesheet-view-cart-button")).isDisplayed()) {
					driver.findElement(By.id("attach-sidesheet-view-cart-button")).click();
				}
			} catch (RuntimeException e) {
				wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-cart")));
				driver.findElement(By.id("nav-cart")).click();
			}
			wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-cart")));
			driver.findElement(By.id("nav-cart")).click();
		} catch (RuntimeException ee) {
			if (ee.toString().contains("NoSuchElementException")) {
				try {
					driver.findElement(By.xpath("//*[contains(@id,'add-to-cart-button')]")).click();
					driver.findElement(By.id("nav-cart")).click();
				} catch (RuntimeException e) {
					driver.findElement(By.id("nav-cart")).click();
				}
			}
		}
	}

	private static void sortingResults(WebDriver driver) {
		WebElement dropDown = driver.findElement(By.id("s-result-sort-select"));
		Select sortDropDown = new Select(dropDown);
		sortDropDown.selectByValue("price-asc-rank");
		Assert.assertEquals("Price: Low to High", sortDropDown.getFirstSelectedOption().getText().toString());
	}

	private static void signOut(WebDriver driver) {
		Actions a = new Actions(driver);
		a.moveToElement(driver.findElement(By.xpath("//div[@id='nav-tools']/child::a[2]/span[1]"))).build().perform();
		driver.findElement(By.linkText("Sign Out")).click();
		driver.close();
	}

	private static void filterSetting(WebDriver driver) throws InterruptedException {
		driver.findElement(By.xpath("//*[@id='p_76/1249134011']/descendant::i")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//*[@id='p_76/1249134011']/descendant::input")).isEnabled());
		driver.findElement(By.xpath("//*[@id='p_72/1248876011']/descendant::section")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//*[@id='p_72/1248876011']/descendant::section")).isEnabled());

		List<WebElement> hairFilter = driver.findElements(By.xpath("//div[@id='filters']/child::ul[1]/li"));
		hairFilter.iterator().forEachRemaining(item -> {
			if (item.getText().toLowerCase().equalsIgnoreCase("dry")) {
				item.click();
				Assert.assertTrue(item.isEnabled());
			}
		});
	}

	private static void searchShampoo(WebDriver driver) {
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Pantene Shampoo");
		driver.findElement(By.id("nav-search-submit-button")).click();
		driver.navigate().back();
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Pantene Shampoo");
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(Keys.ENTER);
	}

	private static void signinMethod(WebDriver driver) {
		Actions a = new Actions(driver);
		a.moveToElement(driver.findElement(By.xpath("//div[@id='nav-tools']/child::a[2]/span[1]"))).build().perform();
		driver.findElement(By.xpath("//div[@id='nav-flyout-ya-signin']/child::a")).click();
		driver.findElement(By.id("ap_email")).sendKeys("grkarthikagr@yahoo.com");
		driver.findElement(By.id("continue")).click();
		driver.findElement(By.id("ap_password")).sendKeys("123456789");
		driver.findElement(By.id("signInSubmit")).click();
	}
}
