package frameworkModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchShampooPage {
	WebDriver driver;

	public SearchShampooPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="twotabsearchtextbox")
	WebElement searchKeyword ;
	
	@FindBy(id="nav-search-submit-button")
	WebElement serachButtonClick ;
	
	public WebElement searchKeyword() {
		return searchKeyword;
	}
	
	public WebElement serachButtonClick() {
		return serachButtonClick;
	}
}
