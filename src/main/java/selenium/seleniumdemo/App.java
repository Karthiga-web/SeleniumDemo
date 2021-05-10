package selenium.seleniumdemo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class App 
{
    public static void main( String[] args ) throws InterruptedException{
    	System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\grkar\\OneDrive\\Documents\\Selenium\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to("https://www.amazon.com/");
		
		signinMethod(driver);
		searchShampoo(driver);
		filterSetting(driver);
		sortingResults(driver);
		signOut(driver);
		
    }

	private static void sortingResults(WebDriver driver) {
		WebElement dropDown = driver.findElement(By.id("s-result-sort-select"));
		Select sortDropDown = new Select(dropDown);
		sortDropDown.selectByValue("price-asc-rank");
		Assert.assertEquals("Price: Low to High",sortDropDown.getFirstSelectedOption().getText().toString());
	}

	private static void signOut(WebDriver driver) {
		Actions a= new Actions(driver);
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
		hairFilter.iterator().forEachRemaining(item->{
			if(item.getText().toLowerCase().equalsIgnoreCase("dry")) {
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
		WebElement searchField = driver.findElement(By.id("twotabsearchtextbox"));
		searchField.sendKeys(Keys.ENTER);
	}

	private static void signinMethod(WebDriver driver) {
		Actions a= new Actions(driver);
		a.moveToElement(driver.findElement(By.xpath("//div[@id='nav-tools']/child::a[2]/span[1]"))).build().perform();
		driver.findElement(By.xpath("//div[@id='nav-flyout-ya-signin']/child::a")).click();
//		System.out.println(driver.findElement(By.xpath("//a[@id='login_accordion_header']/i")).getClass().toString());
//		Assert.assertTrue(driver.findElement(By.xpath("//a[@id='login_accordion_header']/i")).getClass().toString().contains("a-icon-radio-active"));
		driver.findElement(By.id("ap_email")).sendKeys("grkarthikagr@yahoo.com");
		driver.findElement(By.id("continue")).click();
		driver.findElement(By.id("ap_password")).sendKeys("123456789");
		driver.findElement(By.id("signInSubmit")).click();
	}
}
