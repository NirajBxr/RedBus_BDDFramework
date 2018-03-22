package com.redbus.BaseClasses;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.paulhammant.ngwebdriver.NgWebDriver;
//import com.redbus.PageClasses.SamplePageClass1;
import com.redbus.PageClasses.*;

public class BrowserSetup{
	
	public static WebDriver driver = LocalDriverManager.getDriver();
	//creates NGWebdriver object
	public NgWebDriver ngDriver = new NgWebDriver((JavascriptExecutor) driver);
	//created Test data object
	//protected HashMap<String, String> objTestData = LocalDriverManager.getTestData();
	
	public final static long TIME_OUT = 60;
	protected WebDriverWait wait = new WebDriverWait(driver, TIME_OUT);
	
	//protected SamplePageClass1 objSamplepageclass1;
	protected HomePage homePage;
	//protected HomePage homePage;
	//protected KpiMenuPage kpiMenuPage;
	//protected CT_DashboardPage CTdashboardPage;
	//protected MR_DashboardPage MRdashboardPage;
	//protected IXR_DashboardPage IXRdashboardPage;
	//protected Overview_DashboardPage overviewDashboardPage;
	
	public void openBrowser(String URL) throws Exception {
		driver.get(URL);
		//deleteAllCookies();		
	}
	
	//initializes page factory of each page
	public void initPages()
	{
		//objSamplepageclass1 = PageFactory.initElements(driver, SamplePageClass1.class);
		homePage = PageFactory.initElements(driver, HomePage.class);
		//homePage = PageFactory.initElements(driver, HomePage.class);
		//kpiMenuPage = PageFactory.initElements(driver, KpiMenuPage.class);
		//CTdashboardPage = PageFactory.initElements(driver, CT_DashboardPage.class);
		//MRdashboardPage = PageFactory.initElements(driver, MR_DashboardPage.class);
		//IXRdashboardPage = PageFactory.initElements(driver, IXR_DashboardPage.class);
		//overviewDashboardPage = PageFactory.initElements(driver, Overview_DashboardPage.class);
	}
	public void maximizeWindow() {
		//driver.manage().window().maximize();
	}

	public void implicitWait(int time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	public void pageLoad(int time) {
		driver.manage().timeouts().pageLoadTimeout(time, TimeUnit.SECONDS);
	}

	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}
	
	public void closeBrowser()
	{
		driver.quit();
	}
	/*
	public String currentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String cal1 = dateFormat.format(cal.getTime());
		return cal1;
	}
	
	public WebElement fluentWait(By locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(TIME_OUT, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});
		return element;
	}*/

	private void waitUntil(ExpectedCondition<WebElement> condition, long timeout) {
		timeout = timeout != 0 ? timeout : TIME_OUT;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(condition);
	}

	public Boolean waitForIsDisplayed(WebElement element, Integer... timeout) {
		try {
			Thread.sleep(3);
			waitUntil(ExpectedConditions.visibilityOf(element), (timeout.length > 0 ? timeout[0] : TIME_OUT));
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public Boolean waitForIsClickable(WebElement element, Integer... timeout) {
		waitTillSpinnerDisappear();
		try {
			waitUntil(ExpectedConditions.elementToBeClickable(element), (timeout.length > 0 ? timeout[0] : TIME_OUT));
		} catch (org.openqa.selenium.TimeoutException exception) {
			return false;
		}
		return true;
	}

	public Boolean waitTillSpinnerDisappear() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, TIME_OUT);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='spinner-icon']")));
		} catch (org.openqa.selenium.TimeoutException exception) {
			return false;
		}
		return true;
	}
	
	public void WaitForLoadingIconToDisappear()
	 { 
		  driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS) ;
		  try {
			  for(int i=1;i<100;i++)
			  {
				  try {
					  driver.findElement(By.xpath("//div[@id=\"loading-bar-spinner\"]/div"));
					  
				} catch (Exception e) {
					driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
					ngDriver.waitForAngularRequestsToFinish();
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
	public void RefreshPage()
	{
		driver.navigate().refresh();
		WaitForLoadingIconToDisappear();
	}

	private void waitUntil(ExpectedCondition<WebElement> condition, WebDriver driver) {
		waitUntil(condition, TIME_OUT);
	}
	
	public static void acceptAlert() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 2);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
		}
	}
	
	public static void dismissAlert() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 2);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
		} catch (Exception e) {
		}
	}
	
	public static void acceptAlert(WebDriver driver, String object, String data, String locatorValue, String nodeName,
			int iTestStep) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 2);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
//			Log.debug(logger, e.getMessage());
		}
	}
	/*
	public String getTestData(String FeatureName, String ScenarioName, String Key)
	{
		try {
			if(Key.contains("[") && Key.contains("]"))
			{			
				String KeyName = FeatureName.replace(".", "").replace(" ", "_").toLowerCase()
						+"#"+ScenarioName.replace(".", "").replace(" ", "_").toLowerCase()
						+"#"+Key.substring(1,Key.length()-1);
				return objTestData.get(KeyName);
			}
			else
				return Key;
		} catch (Exception e) {
			return "";
		}
		
	}
	*/
}
