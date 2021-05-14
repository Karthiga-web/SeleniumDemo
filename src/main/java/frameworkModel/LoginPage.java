package frameworkModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
	WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	By email = By.id("ap_email");
	By password = By.id("ap_password");
	By continueClick = By.id("continue");
	By submitClick = By.id("signInSubmit");

	public WebElement email() {
		return driver.findElement(email);
	}

	public WebElement password() {
		return driver.findElement(password);
	}

	public WebElement continueClick() {
		return driver.findElement(continueClick);
	}

	public WebElement submitClick() {
		return driver.findElement(submitClick);
	}
}
