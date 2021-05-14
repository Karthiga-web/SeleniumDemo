package frameworkModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	By signinClick = By.xpath("//div[@id='nav-tools']/child::a[2]/span[1]");
	By signin = By.xpath("//div[@id='nav-flyout-ya-signin']/child::a");

	public WebElement signinClick() {
		return driver.findElement(signinClick);
	}

	public WebElement signin() {
		return driver.findElement(signin);
	}
}
