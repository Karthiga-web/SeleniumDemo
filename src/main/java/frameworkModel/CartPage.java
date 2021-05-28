package frameworkModel;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import config.Constants;

public class CartPage {
	WebDriver driver;

	public CartPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = Constants.SIDE_SHEET_SUDDEN_POPUP)
	WebElement sideSheetSuddenPopup;

	public WebElement sideSheetSuddenPopup() {
		return sideSheetSuddenPopup;
	}
	
	@FindBy(id = Constants.SIDE_SHEET_IN_CART_PAGE)
	WebElement sideSheetInCartPage;

	public WebElement sideSheetInCartPage() {
		return sideSheetInCartPage;
	}
	
	@FindBy(linkText = Constants.SIGN_OUT)
	WebElement signOut;

	public WebElement signOut() {
		return signOut;
	}
	
	@FindBy(xpath = Constants.CART_PAGE_MOVE_TO_ELEMENT)
	WebElement cartPageMoveToElement;

	public WebElement cartPageMoveToElement() {
		return cartPageMoveToElement;
	}
	
	@FindBy(id = Constants.FINAL_AMOUNT)
	WebElement finalAmount;

	public WebElement finalAmount() {
		return finalAmount;
	}
	
	@FindBy(xpath = Constants.CART_ACTIVE_ITEMS)
	List<WebElement> cartActiveItems;

	public List<WebElement> cartActiveItems() {
		return cartActiveItems;
	}
}
