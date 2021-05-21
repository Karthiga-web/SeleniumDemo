package frameworkModel;

import java.util.List;

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
	
	public WebElement searchKeyword() {
		return searchKeyword;
	}
	
	@FindBy(id="nav-search-submit-button")
	WebElement serachButtonClick ;
	
	public WebElement serachButtonClick() {
		return serachButtonClick;
	}
	
	@FindBy(xpath="//*[@id='p_76/1249134011']/descendant::i")
	WebElement filter1 ;
	
	public WebElement filter1() {
		return filter1;
	}
	
	@FindBy(id="//*[@id='p_76/1249134011']/descendant::input")
	WebElement filter2 ;
	
	public WebElement filter2() {
		return filter2;
	}
	
	@FindBy(id="//*[@id='p_72/1248876011']/descendant::section")
	WebElement filter3 ;
	
	public WebElement filter3() {
		return filter3;
	}
	
	@FindBy(id="//div[@id='filters']/child::ul[1]/li")
	List<WebElement> filter4 ;
	
	public List<WebElement> filter4() {
		return filter4;
	}
}
