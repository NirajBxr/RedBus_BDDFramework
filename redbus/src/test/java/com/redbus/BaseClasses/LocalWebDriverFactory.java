package com.redbus.BaseClasses;

import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.remote.DesiredCapabilities;

// Class			:	LocalWebDriverFactory
// Purpose		:	For running it locally or remotely.
public class LocalWebDriverFactory {
	
	// Method Name	:	getBrowser
	// Purpose				:	It will return the web driver instance after reading the config file. 
	public static WebDriver getBrowser(String myBrowserType, String executionType, String nodeUrl, int timeoutSeconds)
	{ 
		WebDriver driver = null;
		if(executionType.equalsIgnoreCase("LOCAL"))
		{
			try {
				switch (myBrowserType.toUpperCase()) 
				{
					case "FIREFOX":
						driver = ThreadGuard.protect(new FirefoxDriver()); 
						return driver; 
					case "CHROME":
						System.setProperty("webdriver.chrome.driver",
								System.getProperty("user.dir") + "//src//test//resources//drivers/chromedriver.exe");
						ChromeOptions options = new ChromeOptions();
						options.addArguments("--disable-notifications");
						options.addArguments("disable-infobars");
						driver = new ChromeDriver(options);		
						return driver; 
					default:
				}
				return driver; 
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}	
		}
		else if (executionType.equalsIgnoreCase("REMOTE"))
		{	
			DesiredCapabilities capabilities = new DesiredCapabilities();
			try {
				switch (myBrowserType.toUpperCase()) 
				{
					case "FIREFOX":
						capabilities.setBrowserName("firefox");
						capabilities.setCapability("marionette", false);
						return driver; 
					case "CHROME":					
						ChromeOptions options = new ChromeOptions();
						options.addArguments("disable-infobars");
						capabilities.setCapability(ChromeOptions.CAPABILITY, options);
						capabilities.setBrowserName("chrome");
						driver = new RemoteWebDriver(new URL(nodeUrl), capabilities);
						return driver; 
					default:
						//logger.error("Cant find setup for browser : " + myBrowserType.getBrowser());
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return driver;
	}	
}
