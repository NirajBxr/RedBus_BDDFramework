package com.redbus.RunnerClasses;

import com.redbus.BaseClasses.BrowserSetup;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.api.testng.CucumberFeatureWrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.ini4j.Ini;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cucumber.listener.Reporter;
import com.redbus.BaseClasses.*;

@CucumberOptions(
		strict = true,
		monochrome = true, 
		features = "src/test/resources/features/BusBooking.feature",
		glue = "com.redbus.StepDefinitions",
		plugin = {"json:target/ReportJson/Report.json","com.cucumber.listener.ExtentCucumberFormatter:target/ExtentReports/ExtentReport.html"},
		tags={"@SmokeTest"})
public class SmokeTestRunner {
    private TestNGCucumberRunner testNGCucumberRunner;
    private Properties config = null;
    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());       
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
    public void feature(CucumberFeatureWrapper cucumberFeature) throws Exception {
    	try {
    		Ini ini = new Ini(new File(System.getProperty("user.dir") + "//src//test//resources//config//Config.ini"));
    		String BrowserType = ini.get("Config", "browserType").toUpperCase();
    		String baseUrl = ini.get("Config", "siteUrl");
    		String nodeUrl = ini.get("Config","nodeUrl");
    		String ExecutionType = ini.get("Config","ExecutionType");
    		//String ExtentReportConfigPath = ini.get("Config","ExtentReportConfigPath");
    		ini = null;
    		LocalDriverManager.createDriver(BrowserType,ExecutionType,nodeUrl, 15);
    		BrowserSetup objRunner = new BrowserSetup();	
        	objRunner.openBrowser(baseUrl);
    		objRunner.maximizeWindow();
    		objRunner.implicitWait(30);
        	try {
        		testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
			} catch (Exception e) {
				e.printStackTrace();
			}            
            objRunner.closeBrowser();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());			
		}
    	
    }

    @DataProvider
    public Object[][] features() {
        return testNGCucumberRunner.provideFeatures();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {    	
        testNGCucumberRunner.finish();
        Reporter.loadXMLConfig(new File("src/test/resources/config/RedBusExtentReportsConfig.xml"));
    }
    /*
	@AfterClass
	public static void writeExtentReport() {
		Reporter.loadXMLConfig(new File(FileReader.getInstance().getConfigReader().getReportConfigPath()));
	}
    
    public void LoadConfigProperty(String propertyFileName) throws IOException {
		setConfig(new Properties());
		FileInputStream ip = new FileInputStream(
				System.getProperty("user.dir") + "//src//test//resources//config//"+ propertyFileName);
		getConfig().load(ip);
	}
    public Properties getConfig() {
		return config;
	}

	public void setConfig(Properties config) {
		this.config = config;
	}*/
}
