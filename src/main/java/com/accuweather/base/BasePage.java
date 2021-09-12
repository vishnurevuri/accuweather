package com.accuweather.base;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
	
	private WebDriver driver=null;
	
	public BasePage(WebDriver driver){
		PageFactory.initElements(driver, this);
		this.driver=driver;
		
	}
	
	
	
	public WebDriver getDriver(){
		return driver;
	}
	
	
	
	
	/**
	 * Checks whether given value empty or not.
	 * 
	 * @param value
	 * @return
	 */
	public boolean isNotEmpty(String value) {
		if (StringUtils.isNotBlank(value)) {
			return true;
		}
		return false;
	}

}
