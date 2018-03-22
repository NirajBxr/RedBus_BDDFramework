package com.redbus.PageClasses;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.redbus.BaseClasses.BrowserSetup;

public class HomePage extends BrowserSetup {
	public WebElement element = null;
	public final String LoginPage = "Login_Page";
	public By locator;
	
	// First Change to track changes
	@FindBy(id="src")
	private WebElement FromStation;
	
	@FindBy(id="dest")
	private WebElement ToStation;
	
	@FindBy(css="ul.autoFill")
	private WebElement AutoFillDropDown;
	
	//@FindBy(className="db text-trans-uc")
	//private WebElement OnwardDate;
	
	@FindBy(id="onward_cal")
	private WebElement OnwardDate;
	
	//@FindBy(xpath="//*[@id='search']/div/div[3]/div/label")
	//private WebElement OnwardDate;
	
	@FindBy(css="table.rb-monthTable.first.last")
	private By OnwardCalendarWebTable;
	
	//@FindBy(xpath="//div[@id='rb-calendar_onward_cal']/table/tbody/tr[1]/td[2]")
	//private By OnwardCalendarMonthYearLabel;
	
	@FindBy(css="#rb-calendar_onward_cal > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2)")
	private By OnwardCalendarMonthYearLabel;
	
	@FindBy(className="next")
	private WebElement NextArrow;
	
	@FindBy(className="prev")
	private WebElement PreviousArrow;	
	
	@FindBy(className="db text-trans-uc tal")
	private WebElement ReturnDate;
	
	@FindBy(className="monthTitle")
	private WebElement ReturnCalendarMonthYearLabel;
	
	@FindBy(id="search_btn")
	private WebElement SearchButton;
	
	
	@FindBy(how = How.ID, using = "j_id0:j_id2:username")
	private WebElement userName;
	
	By userName1 = By.id("j_id0:j_id2:username");

	@FindBy(id = "j_id0:j_id2:password")
	private WebElement password;

	@FindBy(id = "j_id0:j_id2:btnLogin")
	private WebElement loginbutton;

	@FindBy(id = "j_id0:j_id2:LoginError:j_id9:j_id10:0:j_id11:j_id12:j_id14")
	private WebElement loginError;

	@FindBys({ @FindBy(id = "j_id0:j_id2:LoginError:j_id9:j_id10:0:j_id11:j_id12:j_id14") })
	private List<WebElement> loginErrors;

	@FindBy(css = "span[id='j_id0:j_id2:LoginError:j_id9:j_id10:0:j_id11:j_id12:j_id15'] [style='color:#cc0000;']")
	private WebElement loginErrorHeader;

	@FindBy(xpath = "//div[@class='dropdown']/a[@id='dropDownImage']/span[@class='caret']")
	private WebElement myAccount;

	@FindBy(css = "a#signOutBtn")
	private WebElement logOut;

	@FindBys({ @FindBy(css = "input[class='btn FlowFinishBtn']") })
	private List<WebElement> finishButton;

	private final String INAVALID_LOGIN_ERRO_MESSAGE = "Your login attempt has failed. Make sure the username and password are correct.";
	private final String INAVALID_USER_LOGIN_MESSAGE = "Please enter a valid Username";
	private final String LOGIN_WITHOUT_PASSWORD_MESSAGE = "Please enter Password";
	
	public boolean enterSourceCity(String sourceCity)
	{
		try {
			this.FromStation.clear();
			this.FromStation.sendKeys(sourceCity);
			WebElement autoSelectOptions = driver.findElement(By.cssSelector("ul.autoFill"));
			wait.until(ExpectedConditions.visibilityOf(autoSelectOptions));
			List<WebElement> options = autoSelectOptions.findElements(By.tagName("li"));
			for (WebElement option : options){
				if(sourceCity.equalsIgnoreCase(option.getText().trim())){
					option.click();
					return true;				
				}
			} 
			return false;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
			}
		}
	
	public boolean enterDestinationCity(String destinationCity)
	{
		try {
			this.ToStation.clear();
			this.ToStation.sendKeys(destinationCity);
			WebElement autoSelectOptions = driver.findElement(By.cssSelector("ul.autoFill"));
			wait.until(ExpectedConditions.visibilityOf(autoSelectOptions));
			List<WebElement> options = autoSelectOptions.findElements(By.tagName("li"));
			for (WebElement option : options){
				if(destinationCity.equalsIgnoreCase(option.getText().trim())){
					option.click();
					return true;	
				}
			}
			return false;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
				}
		}
	
	public boolean clickOnwardCalendar()
	{
		try {
			//System.out.println("Before Calendar Click");
			OnwardDate.click();
			//System.out.println("After Calendar Click");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean clickReturnCalendar()
	{
		try {
			ReturnDate.click();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean setOnwardJourneyDateAsCurrentDate()
	{
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd");  
		    Date date = new Date();
		    String todayDate = formatter.format(date);
		    
		    // click on onward date to open calendar
		    //driver.findElement(By.xpath("//*[@id='search']/div/div[3]/span")).click();
		    //driver.findElement(By.cssSelector("div.search-box:nth-child(4) > div:nth-child(2) > label:nth-child(2)")).click();
		    
		   // Thread.sleep(1000);
		    
		    // Find Onward calendar Web element
			WebElement OnwardCalendarTable = driver.findElement(By.cssSelector("#rb-calendar_onward_cal"));
			
			// To get Rows
			List<WebElement> rows = OnwardCalendarTable.findElements(By.tagName("tr"));
			int rowCount = rows.size();
			for (int row=2; row<rowCount; row++) {
				// To get Columns
				List<WebElement> columns = rows.get(row).findElements(By.tagName("td"));
				int columnCount = columns.size();
				for (int column = 0; column < columnCount; column++) {
				    // To retrieve Cell value from that specific cell.					
				    String cellValue = columns.get(column).getText();
				    // If cell value match with desired date, click on it
				    if(cellValue.equalsIgnoreCase(todayDate)){
				    	OnwardCalendarTable.findElement(By.cssSelector("#rb-calendar_onward_cal > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child("+(row+1)+") > td:nth-child("+(column+1)+")")).click();
				    	return true;
				    }
				   }
			}
			return false;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
				}
	}

	
	public boolean setOnwardJourneyDateAsFutureDate(String Date)
	{
		try {
			SimpleDateFormat dateFormat =  new SimpleDateFormat("dd-MMM-yyyy");
			Date dateToBeSelected = dateFormat.parse(Date);
			
			String day = new SimpleDateFormat("d").format(dateToBeSelected);
			String  month =  new SimpleDateFormat("MMM").format(dateToBeSelected);
			String year = new SimpleDateFormat("yyyy").format(dateToBeSelected);
			String monthYearToBeSelected=month+" "+year;
			System.out.println("Month Year Going to be selected -->"+monthYearToBeSelected);
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd");  
		    Date currentDate = new Date();
		    String todayDate = formatter.format(currentDate);
		    
			String monthYearDisplayed = driver.findElement(By.cssSelector("#rb-calendar_onward_cal > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2)")).getText();
			System.out.println("Month Year displayed in Calender -->"+monthYearDisplayed);	
			
			while(true){
				if(monthYearToBeSelected.equalsIgnoreCase(monthYearDisplayed)){
					//Same Month's Date should be selected
					System.out.println("Going to select date -->"+day);
					
					// Find Onward calendar Web element
					WebElement OnwardCalendarTable = driver.findElement(By.cssSelector("#rb-calendar_onward_cal"));
					
					// To get Rows
					List<WebElement> rows = OnwardCalendarTable.findElements(By.tagName("tr"));
					int rowCount = rows.size();
					for (int row=2; row<rowCount; row++) {
						// To get Columns
						List<WebElement> columns = rows.get(row).findElements(By.tagName("td"));
						int columnCount = columns.size();
						for (int column = 0; column < columnCount; column++) {
						    // To retrieve Cell value from that specific cell.					
						    String cellValue = columns.get(column).getText();
						    // If cell value match with desired date, click on it
						    if(cellValue.equalsIgnoreCase(day)){
						    	OnwardCalendarTable.findElement(By.cssSelector("#rb-calendar_onward_cal > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child("+(row+1)+") > td:nth-child("+(column+1)+")")).click();
						    	return true;
						    }
						   }
					}
					return false;
					}else{
						if(dateToBeSelected.after(currentDate)){
						//click on next icon 
						//driver.findElement(By.cssSelector("#rb-calendar_onward_cal > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(3) > button:nth-child(1)")).click();
						driver.findElement(By.cssSelector("#rb-calendar_onward_cal > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(3)")).click();
					}else{
						//click on prev icon 
						driver.findElement(By.cssSelector("#rb-calendar_onward_cal > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > button:nth-child(1)")).click();
					}
					}
				monthYearDisplayed=driver.findElement(By.cssSelector("#rb-calendar_onward_cal > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2)")).getText();
				}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean setReturnDate()
	{
		try {
			ReturnDate.click();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean clickSearchBusesButton(String searchBuses){
		try {
			if(SearchButton.getText().equalsIgnoreCase(searchBuses)){
				SearchButton.click();
				return true;
			}else{
				return false;
			}
		}catch (Exception e) {
			return false;
		}
	}
	
	public boolean switchToFrame() {
		try {
			driver.switchTo().frame(0);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean getNextPageTitle(String nextPageTitle) {
		try{
			if(driver.getTitle().equalsIgnoreCase(nextPageTitle)){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean EnterUserName(String userName)
	{
		try {
			this.userName.clear();
			this.userName.sendKeys(userName);
			return true;
		} catch (Exception e) {
			return false;
		}		
	}
	
	public boolean EnterPassword(String password)
	{
		try {
			this.password.clear();
			this.password.sendKeys(password);
			return true;
		} catch (Exception e) {
			return false;
		}		
	}
	public boolean clickLoginButton()
	{
		try {
			loginbutton.click();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	public void login(String userName, String password) throws InterruptedException {
		this.userName.clear();
		this.userName.sendKeys(userName);
		this.password.sendKeys(password);
		this.loginbutton.click();
		try {
			forceLogin();
		} catch (Exception e) {
			// TODO: handle exception
		}		
	}
	
	public boolean verifyIsLoginError() {

		return (loginErrors.size() != 0);
	}

	/*public void launchApplication() throws MalformedURLException {
		driver.get(url);
	}*/

	public boolean verifyInvalidUserNameError() {
		return this.loginErrors.get(0).getText().equalsIgnoreCase(INAVALID_USER_LOGIN_MESSAGE);
	}

	public boolean verifyInvalidLoginError(String expectedErrorMessage) {
		try {
			if(this.loginError.getText().contains(expectedErrorMessage))
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}		
	}

	public boolean verifyLoginWithOutPasswordError() {
		return this.loginError.getText().equalsIgnoreCase(LOGIN_WITHOUT_PASSWORD_MESSAGE);
	}

	public void logOut() {
		waitForIsClickable(myAccount);
//		myAccount.click();
		Actions actions = new Actions(driver);
		actions.moveToElement(myAccount).click().perform();
//		actions.moveToElement(myAccount).click().perform();
		waitForIsClickable(logOut);
		logOut.click();
//		Log.info(logger, "Logged out successfully");
		
	}

	public void forceLogin() {
		if (finishButton.size() > 0) {
			finishButton.get(0).click();
		}
	}

	

}
