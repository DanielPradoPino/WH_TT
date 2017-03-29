package pageobjects;
import helpers.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Assert;

public class BetSlipPage extends BaseClass{

	public BetSlipPage(WebDriver driver){
		super(driver);
	}    

	
	@FindBy(how=How.ID, using="betslip-btn-toolbar")
	public static WebElement betslip_button;
	
	@FindBy(how=How.CSS, using=".betslip-selection__event")
	public static WebElement event_name;
	
	@FindBy(how=How.CSS, using="[id*='bet-price_']")
	public static WebElement odd_price;

	@FindBy(how=How.CSS, using="input.betslip-selection__stake-input")
	public static WebElement bet_input;

	@FindBy(how=How.ID, using="place-bet-button")
	public static WebElement place_bet_button;

	@FindBy(how=How.ID, using="receipt-notice-box")
	public static WebElement bet_placed_notice;

	@FindBy(how=How.ID, using="continue-button")
	public static WebElement continue_button;

	@FindBy(how=How.ID, using="total-to-return-price")
	public static WebElement total_return;

	@FindBy(how=How.ID, using="total-stake-price")
	public static WebElement total_stake;

	@FindBy(how=How.CSS, using=".betslip-toolbar .icon-double-arrow-right")
	public static WebElement betslip_close_button;

	//open bets

	@FindBy(how=How.ID, using="openbets-tab")
	public static WebElement openbets_tab;

	@FindBy(how=How.CSS, using="#toolbar .toggle-openbet")
	public static WebElement openbets_menu_button;

	@FindBy(how=How.ID, using="cimb-alert")
	public static WebElement cimb_alert;

	@FindBy(how=How.CSS, using="#cimb-alert .o-btn--success")
	public static WebElement cimb_alert_accept;

	@FindBy(how=How.CSS, using=".betslip-placed-bet:first-child .betslip-placed-bet__description span")
	public static WebElement openbets_odd_price;

	@FindBy(how=How.CSS, using=".betslip-placed-bet:first-child .betslip-placed-bet__event-link span")
	public static WebElement openbets_event_name;

	@FindBy(how=How.CSS, using=".betslip-placed-bet:first-child .betslip-placed-bet__returns:not(.ng-hide) [id*='cashout-stake']")
	public static WebElement openbets_total_stake;

	@FindBy(how=How.CSS, using=".betslip-placed-bet:first-child .betslip-placed-bet__returns:not(.ng-hide) [id*=\"cashout-returns\"]")
	public static WebElement openbets_total_return;




	public static void gotoBetslip(WebDriver driver) throws Exception{

		WebDriverWait wait= new WebDriverWait(driver,10);

		wait.until(ExpectedConditions.visibilityOf(betslip_button));
		betslip_button.click();
		Log.info("Click action is performed on BetSlip Button" );

	}

	public static void closeBetslip(WebDriver driver) throws Exception{

		WebDriverWait wait= new WebDriverWait(driver,10);

		try {
			wait.until(ExpectedConditions.visibilityOf(betslip_button)).click();
			Log.info("BetSlip close button clicked");
		}catch(Exception noBetslip){
			Log.info("Betslip was not present");
		}
	}

	public static void checkBetslipEvent(WebDriver driver,String Team1,String Team2,String odd) throws Exception{

		WebDriverWait wait= new WebDriverWait(driver,10);

		wait.until(ExpectedConditions.visibilityOf(event_name));
		Log.info("Event in BetSlip is("+event_name.getText()+")");
		Assert.assertTrue(Team1+" is not in BetSlip",event_name.getText().contains(Team1));
		Assert.assertTrue(Team2+" is not in BetSlip",event_name.getText().contains(Team2));
		Assert.assertTrue("Odd price is not correct",odd_price.getText().equals(odd));
		Log.info("Selection in Betslip is correct" );

	}

	public static String[] placeBet(WebDriver driver,String bet,double balance) throws Exception{

		WebDriverWait wait= new WebDriverWait(driver,10);

		wait.until(ExpectedConditions.visibilityOf(bet_input)).sendKeys(bet);
		Log.info("Bet input filled with==>"+bet);
		Log.info(total_stake.getText().replaceAll("[^\\d+(\\.\\d{1,2})?]","")+"====="+bet);
		Assert.assertTrue("Total Stake is not correct",total_stake.getText().replaceAll("[^\\d+(\\.\\d{1,2})?]","").equals(bet));
		String[] toReturn={total_stake.getText().replaceAll("[^\\d+(\\.\\d{1,2})?]",""),total_return.getText().replaceAll("[^\\d+(\\.\\d{1,2})?]","")};
		place_bet_button.click();
		Log.info("Place Bet Button clicked" );
		wait.until(ExpectedConditions.visibilityOf(bet_placed_notice));
		Log.info("Bet Placed Correctly");
		wait.until(ExpectedConditions.visibilityOf(continue_button)).click();
		wait.until(ExpectedConditions.visibilityOf(HomePage.refresh_balance)).click();
		double actual_balance=HomePage.getBalance(driver);
		Assert.assertTrue("Balance was not updated correctly",actual_balance==(balance-Double.parseDouble(bet)));

		return toReturn;
	}


	public static void gotoOpenBets(WebDriver driver) throws Exception{

		WebDriverWait wait= new WebDriverWait(driver,10);

		wait.until(ExpectedConditions.visibilityOf(openbets_menu_button)).click();
		Log.info("Open Bets Button clicked" );
		try {
			wait.until(ExpectedConditions.visibilityOf(cimb_alert_accept)).click();
			Log.info("Cimb Alert was present and accepted");
		}catch (Exception noCimb){
			Log.info("Cimb Alert was not present");
		}
		wait.until(ExpectedConditions.visibilityOf(openbets_event_name));
		Log.info("In Open Bets");
	}

	public static void checkBetInOpenBets(WebDriver driver,String Team1,String Team2,String odd,String totalStake,String totalReturn) throws Exception{

		WebDriverWait wait= new WebDriverWait(driver,10);

		Assert.assertTrue(Team1+" is not present in open bets",openbets_event_name.getText().contains(Team1));
		Assert.assertTrue(Team2+" is not present in open bets",openbets_event_name.getText().contains(Team2));
		Assert.assertTrue(odd+" is not correct in open bets",openbets_odd_price.getText().contains(odd));
		Assert.assertTrue("Total Stake is not correct in open bets",openbets_total_stake.getText().replaceAll("[^\\d+(\\.\\d{1,2})?]","").equals(totalStake));
		Assert.assertTrue("Total Stake is not correct in open bets",openbets_total_return.getText().replaceAll("[^\\d+(\\.\\d{1,2})?]","").equals(totalReturn));
		Log.info("Open Bets Data is correct and Bet was placed correctly" );

	}




}
		

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	