package config;

public class Constants {
	//Data to be given from user
	public static final String URL = "https://www.amazon.com/";
	public static final String SEARCH_KEYWORD_SHAMPOO = "Pantene Shampoo";
	public static final String SEARCH_KEYWORD_SAMSUNG = "samsung laptop";
	public static final String HAIR_CONDITION = "dry";
	public static final String EMAIL = "grkarthikagr@yahoo.com";
	public static final String PASSWORD = "123456789";
	public static final String MIN_AMOUNT = "250";
	public static final String MAX_AMOUNT = "500";
	
	//SearchShampooPage Constants
	public static final String FILTER1 = "//*[@id='p_76/1249134011']";
	public static final String ACTIVE_ACCORDIAN = "a-accordion-active";
	public static final String IMAGE_CLICK = "//*[@data-component-type='s-product-image']/a/div";
	public static final String ADD_TO_CART_CLICK_BUTTON = "add-to-cart-button";
	public static final String CART_CLICK_BUTTON = "nav-cart";
	public static final String LOW_PRICE = "low-price";
	public static final String HIGH_PRICE = "//*[@id='high-price']";
	public static final String PAGINATION = "//*[@class='a-pagination']/li";
	public static final String SORTING_ORDER = "price-asc-rank";
	public static final String PRICE_LOW_TO_HIGH = "Price: Low to High";
	
	//CartPage conctants
	public static final String SIDE_SHEET_SUDDEN_POPUP = "attachSiNoCoverage";
	public static final String SIDE_SHEET_IN_CART_PAGE = "attach-sidesheet-view-cart-button";
	public static final String SIGN_OUT = "Sign Out";
	public static final String CART_PAGE_MOVE_TO_ELEMENT = "//div[@id='nav-tools']/child::a[2]/span[1]";
	public static final String DATA_QUANTITY = "data-quantity";
	public static final String DATA_PRICE = "data-price";
	public static final String JAVASCRIPT_WINDOW_SCROLL = "window.scrollBy(0,5800)";
	public static final String VALUE_DISABLED = "a-disabled";
	public static final String HAS_CLASS_LIST_ITEM = "sc-list-item";
	public static final String FINAL_AMOUNT = "sc-subtotal-amount-activecart";
	public static final String CART_ACTIVE_ITEMS = "//*[@data-name='Active Items']/div";
	
	//Attributes
	public static final String ATTRIBUTE_ARIA_LABEL = "aria-label";
	public static final String ATTRIBUTE_CLASS = "class";
	
	
	//Exception
	public static final String NO_SUCH_ELEMENT_EXCEPTION = "NoSuchElementException";
	
	//Regex
	public static final String TO_REMOVE_DOLLAR_COMMAS = "[,$\\s]";
}
