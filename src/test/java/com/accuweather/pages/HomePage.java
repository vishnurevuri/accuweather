package com.accuweather.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


import com.accuweather.base.BasePage;


public class HomePage extends BasePage {

	@FindBy(name="query")
	private WebElement text_Search;
	
	
	
	public HomePage(WebDriver webDriver) {
		super(webDriver);
	}
	
	
	
	public void searchLocation(){
		text_Search.sendKeys("London, London, GB");
		text_Search.sendKeys(Keys.ENTER);
	}
	
	
	
}
