package frameworkModel;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import config.Constants;

public class SearchShampooPage {
	WebDriver driver;

	public SearchShampooPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "twotabsearchtextbox")
	WebElement searchKeyword;

	public WebElement searchKeyword() {
		return searchKeyword;
	}

	@FindBy(id = "nav-search-submit-button")
	WebElement serachButtonClick;

	public WebElement serachButtonClick() {
		return serachButtonClick;
	}

	@FindBy(xpath = Constants.FILTER1 + "/descendant::i")
	WebElement freeCancellation;

	public WebElement freeCancellation() {
		return freeCancellation;
	}

	@FindBy(xpath = "//*[@id='p_72/1248876011']/descendant::section")
	WebElement starRating;

	public WebElement starRating() {
		return starRating;
	}

	@FindBy(xpath = "//div[@id='filters']/child::ul[1]/li")
	List<WebElement> hairCondition;

	public List<WebElement> hairCondition() {
		return hairCondition;
	}

	@FindBy(id = "s-result-sort-select")
	WebElement sortFilter;

	public WebElement sortFilter() {
		return sortFilter;
	}

	@FindBy(xpath = "//*[@data-component-type='s-search-result']")
	List<WebElement> filterResults;

	public List<WebElement> filterResults() {
		return filterResults;
	}
	
	@FindBy(xpath = "//*[@data-component-type='s-product-image']/a/div")
	WebElement imageClick;

	public WebElement imageClick() {
		return imageClick;
	}

	@FindBy(id = "newAccordionRow")
	WebElement newAccordionRow;

	public WebElement newAccordionRow() {
		return newAccordionRow;
	}
	
	@FindBy(id = Constants.ADD_TO_CART_CLICK_BUTTON)
	WebElement addToCartButton;

	public WebElement addToCartButton() {
		return addToCartButton;
	}
	
	@FindBy(id = Constants.CART_CLICK_BUTTON)
	WebElement cartPage;

	public WebElement cartPage() {
		return cartPage;
	}
	
	@FindBy(id = Constants.LOW_PRICE)
	WebElement lowPrice;

	public WebElement lowPrice() {
		return lowPrice;
	}
	
	@FindBy(xpath = Constants.HIGH_PRICE)
	WebElement highPrice;

	public WebElement highPrice() {
		return highPrice;
	}
	
	@FindBy(xpath = Constants.PAGINATION)
	List<WebElement> pagination;

	public List<WebElement> pagination() {
		return pagination;
	}
}
