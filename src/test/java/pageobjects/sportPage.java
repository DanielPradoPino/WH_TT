package pageobjects;
import helpers.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class sportPage extends BaseClass{

	public sportPage(WebDriver driver){
		super(driver);
	}    

	
		@FindBy(how=How.CLASS_NAME, using="tabs__nav")
		public static WebElement tabs_element;

		@FindBy(how=How.CSS, using=".tabs__nav [data-name='Highlights']")
		public static WebElement highlights_tab;

		@FindBy(how=How.CSS, using="#match-highlights .event:nth-of-type(2) .btmarket__link-name span:nth-of-type(1)")
		public static WebElement first_match_team_1;

		@FindBy(how=How.CSS, using="#match-highlights .event:nth-of-type(2) .btmarket__link-name span:nth-of-type(2)")
		public static WebElement first_match_team_2;

		@FindBy(how=How.CSS, using="#match-highlights .event:nth-of-type(2) .btmarket__selection:nth-of-type(1) button")
		public static WebElement first_odds_button;

	public static String team1,team2,odd;


	public static String[] addHighlightsFirstSelectiontoBetslip(WebDriver driver) throws Exception{

		WebDriverWait wait=new WebDriverWait(driver,10);

		wait.until(ExpectedConditions.visibilityOf(highlights_tab)).click();
		wait.until(ExpectedConditions.visibilityOf(tabs_element));

		String firstTeam=first_match_team_1.getText().replace("Rep","Republic"); //I had an issue where Rep of Ireland was Republic of Ireland in Betslip
		String seconTeam=first_match_team_2.getText().replace("Rep","Republic");
		String odd=first_odds_button.getText();
		String[] selection={firstTeam,seconTeam,odd};
		Log.info("Selection ("+firstTeam+" vs "+seconTeam+") will be added to Betslip with odd==>"+odd);
		HomePage.scrollToObjectTop(first_odds_button);
		first_odds_button.click();
		Log.info("First Selection was added to Betslip");
		return selection;
	}

	public static String getTeam1(){
		return team1;
	}
		
	public static String getTeam2(){
		return team2;
	}

	public static String getOdd(){
		return odd;
	}

	}
		

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
