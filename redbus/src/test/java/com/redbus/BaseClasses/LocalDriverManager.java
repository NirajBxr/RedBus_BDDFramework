package com.redbus.BaseClasses;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;
import org.openqa.selenium.WebDriver;

public class LocalDriverManager {
	
	private static final ThreadLocal threadLocalWebDriver = new ThreadLocal();
	private static final ThreadLocal threadLocalTestData = new ThreadLocal();
	
	public static void createDriver(String browserType,String executionType,String nodeUrl, int waitTimeSec){
		WebDriver driver = LocalWebDriverFactory.getBrowser(browserType,executionType,nodeUrl, waitTimeSec);
		LocalDriverManager.setWebDriver(driver);
	}
	
	public static WebDriver getDriver() {
	return (WebDriver) threadLocalWebDriver.get();
	}
	
	private static void setWebDriver(WebDriver driver) {
		threadLocalWebDriver.set(driver);
	}
	public static void createTestDataObject() throws InvalidFileFormatException, IOException{
		HashMap<String, String> objTestData = new HashMap<>();
		String fileName;
		File dir = new File(System.getProperty("user.dir") + "//src//test//resources//test_data");
		File[] filesList = dir.listFiles();
		for (File file : filesList) {
		    if (file.isFile()) {
		    	fileName = file.getName();
		    	//remove.ini
		    	fileName = fileName.split(".ini")[0];
		        if(file.getName().contains(".ini"))
		        {
		        	Ini ini = new Ini(file);
		        	
		    		for (String sectionName: ini.keySet()) {
		                Section section = ini.get(sectionName);
		                for (String optionKey: section.keySet()) {
		                	objTestData.put(fileName.replace(".", "").toLowerCase()
		                			+"#"+sectionName.replace(".", "").replace(" ", "_").toLowerCase()
		                			+"#"+optionKey, section.get(optionKey));
		                }
		    		}
		    		ini = null;
		        }
		    }
		}
		LocalDriverManager.setTestData(objTestData);
	}
	private static void setTestData(HashMap<String, String> objTestData) {
		threadLocalTestData.set(objTestData);
	}
	
	public static HashMap<String, String> getTestData() {
		return (HashMap<String, String>) threadLocalTestData.get();
		}
	
	public static void unset() {
		threadLocalWebDriver.remove();
	}
	
	public static void destroyLocalDriver(){	
	if (getDriver() != null) {
		getDriver().quit();
//		logger.info("Destroyed ...!");
		}else {
//		logger.info("Not Destroyed ...is NULL ...!");
		}
	}

}