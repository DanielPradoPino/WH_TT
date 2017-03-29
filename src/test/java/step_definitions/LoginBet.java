package step_definitions;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import pageobjects.BetSlipPage;
import pageobjects.sportPage;
import pageobjects.HomePage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LoginBet {
    public WebDriver driver;
//    public List<HashMap<String,String>> datamap = DataHelper.data();
    public static List<HashMap<String,String>> datamap = null;
    private static Logger log = LogManager.getLogger(LoginBet.class.getName());
    private String[] selection={"","",""},betData={"",""};
    private double balance=0.00;

    
    public LoginBet()
    {
    	driver = Hooks.driver;
    	
    	datamap = new ArrayList<HashMap<String,String>>();
    	HashMap<String,String> sampleData = new HashMap<String,String>();
    	sampleData.put("username","WHATA_FOG2");
    	sampleData.put("password","F0gUaTtest");
    	log.info("Current User Data" +sampleData);
    	datamap.add(sampleData);
    }
    
    @When("^I open website$")
    public void i_open_website() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    	driver.get("http://sports.williamhill.com/sr-admin-set-white-list-cookie.html");
        Assert.assertTrue("Not in a William Hill Site",driver.getTitle().contains("Online Betting from William Hill"));
        PageFactory.initElements(driver, HomePage.class);
        PageFactory.initElements(driver, sportPage.class);
        PageFactory.initElements(driver, BetSlipPage.class);
    }

    @When("^I sign in$")
    public void i_sign_in() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    	HomePage.logIn(driver,datamap.get(0));
    }

    @When("^I get balance$")
    public void i_get_balance() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    	balance=HomePage.getBalance(driver);
        log.info("User Balance==>"+balance);
    }

    @When("^I navigate to football$")
    public void i_navigate_to_football() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
		HomePage.gotoSport(driver,"football");
    }


    @When("^I navigate to tennis$")
    public void i_navigate_to_tennis() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
		HomePage.gotoSport(driver,"tennis");
    }


    @When("^I add selection to betslip$")
    public void i_add_selection_to_betslip() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
		selection= sportPage.addHighlightsFirstSelectiontoBetslip(driver);
    }


    @When("^I open betslip$")
    public void i_open_betslip() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        BetSlipPage.gotoBetslip(driver);
    }


    @When("^I check selection in betslip$")
    public void i_check_selection_in_betslip() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
		BetSlipPage.checkBetslipEvent(driver,selection[0],selection[1],selection[2]);
    }


    @When("^I place bet$")
    public void i_place_bet() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
		betData=BetSlipPage.placeBet(driver,"0.50",balance);
    }


    @When("^I goto openbets$")
    public void i_goto_openbets() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
		BetSlipPage.gotoOpenBets(driver);
    }


    @When("^I check bet in openbets$")
    public void i_check_bet_in_openbets() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
		BetSlipPage.checkBetInOpenBets(driver,selection[0],selection[1],selection[2],betData[0],betData[1]);
    }

    @Then("^I sign out$")
    public void i_sign_out() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    	HomePage.logOut(driver);
    }
    
}