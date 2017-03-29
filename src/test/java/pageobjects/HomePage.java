package pageobjects;
import helpers.Log;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;

public class HomePage extends BaseClass{

	public HomePage(WebDriver driver){
		super(driver);
	}    

	
	@FindBy(how=How.ID, using="accountTabButton")
	public static WebElement account_tab_button;
	
	@FindBy(how=How.ID, using="loginUsernameInput")
	public static WebElement username_input;
	
	@FindBy(how=How.ID, using="loginPasswordInput")
	public static WebElement password_input;

	@FindBy(how=How.ID, using="loginButton")
	public static WebElement login_button;

	@FindBy(how=How.ID, using="balanceLink")
	public static WebElement balance_link;

	@FindBy(how=How.CSS, using=".column__primary #nav-football")
	public static WebElement football_menu_button;

	public static String sport_menu_button=".column__primary #nav-$$sport$$";

	@FindBy(how=How.ID, using="logoutLink")
	public static WebElement logout_button;

	@FindBy(how=How.CSS, using=".header__balance-refresh .icon-refresh")
	public static WebElement refresh_balance;


	public static void logIn(WebDriver driver,HashMap<String,String> map) throws Exception{

		WebDriverWait wait= new WebDriverWait(driver,10);

		wait.until(ExpectedConditions.visibilityOf(HomePage.account_tab_button));
		account_tab_button.click();
		Log.info("Click action is performed on Account tab" );

		wait.until(ExpectedConditions.visibilityOf(HomePage.username_input));
		username_input.sendKeys(map.get("username"));
		Log.info("UserName filled" );

		password_input.sendKeys(map.get("password"));
		Log.info("Password filled" );

		login_button.click();
		Log.info("Click action is performed on Login button");
		wait.until(ExpectedConditions.visibilityOf(balance_link));
		Assert.assertTrue("Balance is not shown after log in",balance_link.isDisplayed());
		Log.info("Login Successful");
		BetSlipPage.closeBetslip(driver); //check whether betslip can be shown and close it
	}


	public static double getBalance(WebDriver driver){

		String balance=balance_link.getText().replaceAll("[^\\d+(\\.\\d{1,2})?]","");
		double Balance=Double.parseDouble(balance);
		return Balance;
	}

	public static void logOut(WebDriver driver) throws Exception{

		account_tab_button.click();
		logout_button.click();
		Assert.assertTrue("Balance is still visible afer logout",!balance_link.isDisplayed());

		Log.info("Sign out is performed");
	}

	public static void gotoSport(WebDriver driver,String sport) throws Exception{

		WebDriverWait wait=new WebDriverWait(driver,10);
		sport=sport.toLowerCase();
		WebElement sportTab=driver.findElement(By.cssSelector(sport_menu_button.replace("$$sport$$",sport)));
		wait.until(ExpectedConditions.visibilityOf(sportTab)).click();
		wait.until(ExpectedConditions.visibilityOf(sportPage.tabs_element));
		Assert.assertTrue("Not in "+sport+" page",driver.getCurrentUrl().contains(sport));

		Log.info("Navigated to "+sport+" Page");

	}

	public static void scrollToObjectTop(WebElement element) throws Exception {
		Log.info("Scroll up to " + element.toString());
		scrollToObject(true, -100, element);
	}

	public static void scrollToObject(boolean intoView, int pixels, WebElement element) throws Exception {
		Log.info("Scroll to " + element.toString());

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(" + intoView + ");", element);
		jse.executeScript("window.scrollBy(0, " + pixels + ");", "");

		Thread.sleep(500); // after scroll we need to wait for scroll animation before click
	}

}
		

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	