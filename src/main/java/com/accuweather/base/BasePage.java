package com.accuweather.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 *  Represents the base page class that provide common abilities such as initilize the elements and utility methods
 * @author Vishnu
 *
 */

public class BasePage {

	private WebDriver driver = null;

	public BasePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;

	}

	public WebDriver getDriver() {
		return driver;
	}

}
