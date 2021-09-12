package com.accuweather.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Utility for creating screenshots using the web driver.
 * 
 * @author Vishnu
 * @version 1.0
 */
public class ScreenshotUtil {	

	private final WebDriver driver;
	private final File screenShotDir;

	/**
	 * Initialize screen shot driver.
	 * 
	 * @param driver
	 * @param screenShotDir
	 */
	public ScreenshotUtil(WebDriver driver, File screenShotDir) {
		this.driver = driver;
		this.screenShotDir = screenShotDir;
	}

	/**
	 * Captures the screenshot from mobile or web page.
	 */
	public String captureScreenShot(String fileName) {
		try {
			File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File targetScreenshot = new File(screenShotDir, fileName);
			FileUtils.copyFile(screenShot, targetScreenshot);
			return targetScreenshot.getName();
		} catch (IOException excp) {
			Log.warn("Screen shot retrieval failed due to :"+ excp);
		}
		return null;
	}



	
}
