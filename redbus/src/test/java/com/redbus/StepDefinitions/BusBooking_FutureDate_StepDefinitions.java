package com.redbus.StepDefinitions;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.redbus.BaseClasses.BrowserSetup;
//import com.redbus.RunnerClasses.CucumberRunner;
import com.redbus.RunnerClasses.SmokeTestRunner;
import com.redbus.PageClasses.HomePage;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

public class BusBooking_FutureDate_StepDefinitions extends BrowserSetup {
		
		private Scenario scenario;
		private String ScenarioName;
		private String FeatureName;
		
		@Before
	    public void beforescenario(Scenario scenario){
	        this.scenario = scenario;      
	        this.ScenarioName = this.scenario.getName();
	        this.FeatureName = getFeatureFileNameFromScenarioId(this.scenario);
	        initPages();	   
	        maximizeWindow();
	    }
		
		private String getFeatureFileNameFromScenarioId(Scenario scenario) {
		    String featureName = "Feature ";
		    String rawFeatureName = scenario.getId().split(";")[0].replace("-"," ");
		    featureName = rawFeatureName.substring(0, 1).toUpperCase() + rawFeatureName.substring(1);
		    return featureName;
		}
		
		@After
		public void afterscenario(Scenario scenario) {
			try {
				if (scenario.isFailed()) {
//			    	File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//					String failureImageFileName = scenario.getName().replace(".", "").replace(" ", "_")
//							+ new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new GregorianCalendar().getTime()) + ".png";
//					File failureImageFile = new File(System.getProperty("user.dir") + "//screenshots//" + failureImageFileName);					
//					FileUtils.copyFile(imageFile, failureImageFile);	
					try {
				        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
				        scenario.embed(screenshot, "image/png");
				      } catch (WebDriverException wde) {
				        System.err.println(wde.getMessage());
				      } catch (ClassCastException cce) {
				        cce.printStackTrace();
				      }
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Given("^user is on redbus home Page$")
	    public void user_is_on_redbus_home_page() throws Throwable {
			boolean result;
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("src")));
				result = true;
			} catch (Exception e) {
				result = false;
			}
			Assert.assertTrue(result == true);
		}

		@When("^user enters source city as \"([^\"]*)\"$")
	    public void user_enters_source_city_as_something(String sourceCity) throws Throwable {
	    	if(homePage.enterSourceCity(sourceCity)){
	    		Assert.assertTrue(true);
	    	}else{
	    		Assert.assertTrue(false);
	    		}
	    	}
		
		@And("^user enters destination city as \"([^\"]*)\"$")
	    public void user_enters_destination_city_as_something(String destinationCity) throws Throwable {
			if(homePage.enterDestinationCity(destinationCity)){
				Assert.assertTrue(true);
				}else{
					Assert.assertTrue(false);
	    			}
		}

		/*@And("^select the current date$")
	    public void select_the_current_date() throws Throwable {
	    	//if(homePage.clickOnwardCalendar()){
	    	if(homePage.switchToFrame()){
	    		System.out.println("Switched to Calendar Frame");
	    	}
	    	if(homePage.setOnwardJourneyDateAsCurrentDate()){
	    		Assert.assertTrue(true);
	    	}else{
    			Assert.assertTrue(false);
	    	}
	    }*/
		
		@And("^select the onward journey data as \"([^\"]*)\"$")
	    public void select_the_onward_journey_data_as_something(String journeyDate) throws Throwable{
			if(homePage.setOnwardJourneyDateAsFutureDate(journeyDate)){
	    		Assert.assertTrue(true);
	    	}else{
    			Assert.assertTrue(false);
	    	}
		}
		
		@And("^clicks on \"([^\"]*)\" button$")
	    public void clicks_on_something_button(String searchBuses) throws Throwable {
			if(homePage.clickSearchBusesButton(searchBuses)){
	    		Assert.assertTrue(true);
	    	}else{
    			Assert.assertTrue(false);
	    	}
		    }

	    @Then("^\"([^\"]*)\" Page should appear$")
	    public void something_page_should_appear(String nextPageTitle) throws Throwable {
	    	if(homePage.getNextPageTitle(nextPageTitle)){
	    		Assert.assertTrue(true);
	    	}else{
    			Assert.assertTrue(false);
	    	}
		 }
	}	    

	   
		/*
		@Given("^user is on Login Page$")
	    public void user_is_on_login_page() throws Throwable {
	       boolean result;
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("j_id0:j_id2:username")));
				result = true;
			} catch (Exception e) {
				result = false;
			}
			Assert.assertTrue(result == true);
	    }
	    @When("^user enters username as \"([^\"]*)\" and password as \"([^\"]*)\"$")
	    public void user_enters_username_as_something_and_password_as_something(String username, String password) throws Throwable {
	    	if(loginPage.EnterUserName(username))
	    	{
	    		if(loginPage.EnterPassword(password))
	    			Assert.assertTrue(true);
	    		else
	    			Assert.assertTrue(false);
	    	}
	    	else
	    		Assert.assertTrue(false);
	    }
	    
	    @And("^clicks on Login button$")
	    public void clicks_on_login_button() throws Throwable {
	    	Assert.assertTrue(loginPage.clickLoginButton());
	    }
	    @Then("^error message \"([^\"]*)\" message should appear on Login page$")
	    public void error_message_something_message_should_appear_on_login_page(String ExpectedErrorMessage) throws Throwable {
	    	Assert.assertTrue(loginPage.verifyInvalidLoginError(ExpectedErrorMessage));
	    }
	    
	    @Then("^Display name \"([^\"]*)\" should appear on Home Page$")
	    public void display_name_something_should_appear_on_home_page(String userDisplayName) throws Throwable {
	    	Assert.assertTrue(homePage.waitForDisplayNameToAppear(userDisplayName));
	    }

	    @And("^Home page should be loaded$")
	    public void home_page_should_be_loaded() throws Throwable {
	    	Assert.assertTrue(homePage.waitForHomePageToLoad());  	  
	    }
	    @Then("^Display name \"([^\"]*)\" should appear on Home Page within \"([^\"]*)\" milliseconds$")
	    public void display_name_something_should_appear_on_home_page_within_something_milliseconds(String displayName, String maxTimetoLoad) throws Throwable {
	    	long startTimer = System.currentTimeMillis();
	  	  try {
	  		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"userName\"]")));
	  		  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"userName\"]")));
	  		  wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\"userName\"]"), displayName));
	  		  String text  = driver.findElement(By.xpath("//*[@id=\"userName\"]")).getText();
	  		} catch (Exception e) {
	  			System.out.println(e.getMessage());
	  		}  	  
	  	  long stopTimer = System.currentTimeMillis();
	  	  
	  	  long totalTime = stopTimer - startTimer;
	  	  scenario.write("Login time : "+String.valueOf(totalTime)+" ms");
	  	  Assert.assertTrue(totalTime< Integer.parseInt(maxTimetoLoad));
	    }
	    @And("^Home page should appear within \"([^\"]*)\" milliseconds$")
	    public void home_page_should_appear_within_something_milliseconds(String maxTimetoLoad) throws Throwable {
	    	long startTimer = System.currentTimeMillis();
	 	   
	  	  try {
	  		  WaitForLoadingIconToBeInvisible();
	  		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#homeService']")));
	  		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[contains(@src,\"ic-partnership\")]")));
	  		  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='#dashboard-sidebar']")));
	  		  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[contains(@src,\"ic-partnership\")]")));
	  		  
	  		} catch (Exception e) {
	  			System.out.println(e.getMessage());
	  		}  	  
	  	  long stopTimer = System.currentTimeMillis();
	  	  
	  	  long totalTime = stopTimer - startTimer; 
	  	  Assert.assertTrue(totalTime< Integer.parseInt(maxTimetoLoad));
	    }
  	    @Given("^user is on Home Page$")
	    public void user_is_on_home_page() throws Throwable {
  	    	Assert.assertTrue(homePage.waitForHomePageToLoad());  	  
	    }
  	    @And("^User navigates to Dashboard page for first time$")
  	    public void user_navigates_to_dashboard_page_for_first_time() throws Throwable {
  	    	try {
  	    		homePage.navigateToMR();
  				MRdashboardPage.waitForMRPageToLoad();
  				MRdashboardPage.RefreshPage();
  				MRdashboardPage.waitForMRPageToLoad();	
  				Assert.assertTrue(true);
			} catch (Exception e) {
				Assert.assertTrue(false);
			}
  	    }
  	    @Then("^following Modalities should be available in the Dashboard menu$")
        public void following_modalities_should_be_available_in_the_dashboard_menu(DataTable modalities) throws Throwable {
  	    	try {
	  	    	List<List<String>> data = modalities.raw();
				List<String> availableModalities = homePage.getAvailableModalitiesList();
				if (availableModalities.contains("Interventional X-Ray") 
						&& availableModalities.contains("Computed Tomography")
						&& availableModalities.contains("Magnetic Resonance Imaging")) 
				{				
					Assert.assertTrue(true);
				}
				else{
					//homePage.navigateToCT();
					Assert.assertTrue(false);
				}  	
  	    	} catch (Exception e) {
				Assert.assertTrue(false);
			}
        }
  	    @When("^user navigates to \"([^\"]*)\" dashboard page$")
  	    public void user_navigates_to_something_dashboard_page(String Modality) throws Throwable {
  	    	try {
  	    		switch (Modality) {
  				case "CT":
  					Assert.assertTrue(kpiMenuPage.navigateToCT());	 
  					break;
  				case "MR":
  					Assert.assertTrue(kpiMenuPage.navigateToMR());
  					break;
  				case "IXR":
  					Assert.assertTrue(kpiMenuPage.navigateToIXR());
  					break;
  	  	    	}
			} catch (Exception e) {
				Assert.assertTrue(false);
			}  	    	
  	    }

  	    @Then("^\"([^\"]*)\" dashboard page should load$")
  	    public void something_dashboard_page_should_load(String Modality) throws Throwable {
  	    	switch (Modality) {
			case "CT":
				Assert.assertTrue(CTdashboardPage.waitForCTPageToLoad());
				break;
			case "MR":
				Assert.assertTrue(MRdashboardPage.waitForMRPageToLoad());
				break;
			case "IXR":
				Assert.assertTrue(IXRdashboardPage.waitForIXRPageToLoad());
				break;
  	    	}
  	    }
  	    
	    @When("^user navigates to CT dashboard page$")
	    public void user_navigates_to_ct_dashboard_page() throws Throwable {
	    	SelectModality("Computed Tomography");
	    }
	    @Then("^CT dashboard page should load within \"([^\"]*)\" milliseconds$")
	    public void ct_dashboard_page_should_load_within_something_milliseconds(String maxTimetoLoad) throws Throwable 
	    {
	      long startTimer = System.currentTimeMillis();
	  	  try {
	  		  WaitForLoadingIconToBeInvisible();
	  		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"modalityHeaderPage\"]//bi-kpi-box/bi-kpi/div[1]")));		  
	  		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("iframe[title='data visualization']")));		 
	  		  
	  		  driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='data visualization']")));
	  		  wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='tab-tvView tvimagesNS']//descendant::div[@class='tvimagesContainer']/img")));
	  		  driver.switchTo().defaultContent();
	  		} catch (Exception e) {
	  			System.out.println(e.getMessage());
	  		}  	  
	  	  long stopTimer = System.currentTimeMillis();
	  	  
	  	  long totalTime = stopTimer - startTimer; 
	  	  
	  	  scenario.write("CT Page Load time : " + String.valueOf(totalTime)+" ms");
	  	  Assert.assertTrue(totalTime< Integer.parseInt(maxTimetoLoad));
	    }
	    @When("^\"([^\"]*)\" kpi is selected$")
	    public void something_kpi_is_selected(String kpi) throws Throwable {
	    	Assert.assertTrue(kpiMenuPage.SelectKPI(kpi));
	    }
	    
	    @Then("^\"([^\"]*)\" kpi value should be numeric on CT dashboard$")
	    public void something_kpi_value_should_be_numeric_on_ct_dashboard(String kpiName) throws Throwable {
	    	String value = CTdashboardPage.getKPIValue(kpiName);
	    	scenario.write(kpiName+" : "+  value);
	    	Assert.assertTrue(value.matches("^[0-9]+$"));
	    }
	    
	    @Then("^\"([^\"]*)\" kpi value should be numeric on MR dashboard$")
	    public void something_kpi_value_should_be_numeric_on_mr_dashboard(String kpiName) throws Throwable {
	    	String value = MRdashboardPage.getKPIValue(kpiName);
	    	scenario.write(kpiName+" : "+  value);
	    	Assert.assertTrue(value.matches("^[0-9]+$"));
	    }
	    
	    @Then("^\"([^\"]*)\" kpi value should be numeric on IXR dashboard$")
	    public void something_kpi_value_should_be_numeric_on_ixr_dashboard(String kpiName) throws Throwable {
	    	String value = IXRdashboardPage.getKPIValue(kpiName);
	    	scenario.write(kpiName+" : "+  value);
	    	Assert.assertTrue(value.matches("^[0-9]+$"));
	    }
	    
	    @And("^Utilization value should be numeric on IXR dashboard$")
	    public void utilization_value_should_be_numeric_on_ixr_dashboard() throws Throwable {
	    	String value = IXRdashboardPage.getUtilizationValue();
	    	scenario.write("Utilization : "+  value);
	    	Assert.assertTrue(value.matches("^[0-9]+$"));
	    }

	    @And("^Average Change over time value should be numeric on IXR dashboard$")
	    public void average_change_over_time_value_should_be_numeric_on_ixr_dashboard() throws Throwable {
	    	String value = IXRdashboardPage.getAverageChangeOverTimeValue();
	    	scenario.write("Average change over time : "+  value);
	    	Assert.assertTrue(value.matches("^[0-9]+$"));
	    }

	    @And("^Volume value should be numeric on IXR dashboard$")
	    public void voulme_value_should_be_numeric_on_ixr_dashboard() throws Throwable {
	    	String value = IXRdashboardPage.getVolumeValue();
	    	scenario.write("Volume : "+  value);
	    	Assert.assertTrue(value.matches("^[0-9]+$"));
	    }
	    
	    @And("^Utilization by Location value should be numeric on CT dashboard$")
	    public void utilization_by_location_value_should_be_numeric_on_ct_dashboard() throws Throwable {
	    	String value = CTdashboardPage.getUtilizationByLocationValue();
	    	scenario.write("Value : "+  value);
	    	Assert.assertTrue(value.matches("^[0-9]+$"));
	    }

	    @And("^Utilization by Department value should be numeric on CT dashboard$")
	    public void utilization_by_department_value_should_be_numeric_on_ct_dashboard() throws Throwable {
	    	String value = CTdashboardPage.getUtilizationByDepartmentValue();
	    	scenario.write("Value : "+  value);
	    	Assert.assertTrue(value.matches("^[0-9]+$"));
	    }

	    @And("^Utilization by System value should be numeric on CT dashboard$")
	    public void utilization_by_system_value_should_be_numeric_on_ct_dashboard() throws Throwable {
	    	String value = CTdashboardPage.getUtilizationBySystemValue();
	    	scenario.write("Value : "+  value);
	    	Assert.assertTrue(value.matches("^[0-9]+$"));
	    }

	    @And("^Volume by System value should be numeric on CT dashboard$")
	    public void volume_by_system_value_should_be_numeric_on_ct_dashboard() throws Throwable {
	    	String value = CTdashboardPage.getVolumeBySystemValue();
	    	scenario.write("Value : "+  value);
	    	Assert.assertTrue(value.matches("^[0-9]+$"));
	    }

	    @And("^Volume per Exam Type by System value should be numeric on CT dashboard$")
	    public void volume_per_exam_type_by_system_value_should_be_numeric_on_ct_dashboard() throws Throwable {
	    	String value = CTdashboardPage.getVolume_per_Exam_Type_by_System_Value();
	    	scenario.write("Value : "+  value);
	    	Assert.assertTrue(value.matches("^[0-9]+$"));
	    }
	    
	    @And("^Utilization by Location value should be numeric on MR dashboard$")
	    public void utilization_by_location_value_should_be_numeric_on_mr_dashboard() throws Throwable {
	    	String value = MRdashboardPage.getUtilizationByLocationValue();
	    	scenario.write("Value : "+  value);
	    	Assert.assertTrue(value.matches("^[0-9]+$"));
	    }

	    @And("^Utilization by Department value should be numeric on MR dashboard$")
	    public void utilization_by_department_value_should_be_numeric_on_mr_dashboard() throws Throwable {
	    	String value = MRdashboardPage.getUtilizationByDepartmentValue();
	    	scenario.write("Value : "+  value);
	    	Assert.assertTrue(value.matches("^[0-9]+$"));
	    }

	    @And("^Utilization by System value should be numeric on MR dashboard$")
	    public void utilization_by_system_value_should_be_numeric_on_mr_dashboard() throws Throwable {
	    	String value = MRdashboardPage.getUtilizationBySystemValue();
	    	scenario.write("Value : "+  value);
	    	Assert.assertTrue(value.matches("^[0-9]+$"));
	    }

	    @And("^Volume by System value should be numeric on MR dashboard$")
	    public void volume_by_system_value_should_be_numeric_on_mr_dashboard() throws Throwable {
	    	String value = MRdashboardPage.getVolumeBySystemValue();
	    	scenario.write("Value : "+  value);
	    	Assert.assertTrue(value.matches("^[0-9]+$"));
	    }

	    @And("^Volume per Exam Type by System value should be numeric on MR dashboard$")
	    public void volume_per_exam_type_by_system_value_should_be_numeric_on_mr_dashboard() throws Throwable {
	    	String value = MRdashboardPage.getVolume_per_Exam_Type_by_System_Value();
	    	scenario.write("Value : "+  value);
	    	Assert.assertTrue(value.matches("^[0-9]+$"));
	    }
	    public void WaitForLoadingIconToBeInvisible()
	    {
	  	  driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS) ;
	  	  try {
	  		  //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id=\"loading-bar-spinner\"]/div")));
	  		  for(int i=1;i<100;i++)
	  		  {
	  			  try {
	  				  driver.findElement(By.xpath("//div[@id=\"loading-bar-spinner\"]/div"));
	  			} catch (Exception e) {
	  				driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
	  				return;
	  			}
	  			  Thread.sleep(1000);
	  		  }
	  		  
	  	} catch (Exception e) {
	  		// TODO: handle exception
	  	}
	  	  driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
	  	  return;
	    }
	    public void SelectModality(String ModalityName)
	    {
	  	  try {
	  		  driver.findElement(By.id("modalitySelection")).click(); 	
	  		  driver.findElement(By.xpath("//*[@id=\"modalitySelection\"]//a[contains(text(),"+"\""+ModalityName+"\""+")]")).click();		  		  
	  		} catch (Exception e) {
	  			System.out.println(e.getMessage());
	  		}   
	    }
	    public void FirstTimeNavigationtoDashboard()
	    { 
	  	  try {
	  		  driver.findElement(By.xpath("//a[@href='#dashboard-sidebar']")).click();	
	  		  driver.findElement(By.xpath("//*[@id=\"dashboard-sidebar\"]//a[contains(text(),'Computed')]")).click();
	  		  WaitForLoadingIconToBeInvisible();
	  		  driver.navigate().refresh();
	  		  WaitForLoadingIconToBeInvisible();
	  		} catch (Exception e) {
	  			System.out.println(e.getMessage());
	  		}	  
	    }
	    @Given("^Test Scenario1_Step$")
	    public void test_scenario1step() throws Throwable {
	    	scenario.write("Test Scenario");
	        Assert.assertTrue(true);
	    }
	    */
