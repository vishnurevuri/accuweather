package com.accuweather.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.accuweather.reports.StepReportData.StepStatus;
import com.accuweather.reports.TestLogger;
import com.accuweather.base.BasePage;


public class HomePage extends BasePage {

	@FindBy(name="query")
	private WebElement text_Search;
	
	@FindBy(xpath="//h2[normalize-space(text())='Current Weather']/parent::div/descendant::div[@class='temp']")
	private WebElement lable_temp;
	
	@FindBy(xpath="//div[@class='current-weather-details']/div[@class='right']//div[text()='Pressure']/following-sibling::div")
	private WebElement lable_Pressure;
	
	
	@FindBy(xpath="//div[@class='current-weather-details']/div[@class='left']//div[text()='Humidity']/following-sibling::div")
	private WebElement lable_Humidity;
	
	@FindBy(xpath="//span[@class='cur-con-weather-card__cta']/span[text()='More Details']")
	private WebElement link_MoreDetails;
	
	
	public HomePage(WebDriver webDriver) {
		super(webDriver);
	}
	
	
	
	public void searchLocation(){
		text_Search.sendKeys("London, London, GB");
		text_Search.sendKeys(Keys.ENTER);
	}
	
	public String getTemp(){
		
		TestLogger.logStep(StepStatus.PASS, "Tempratute: " +lable_temp.getText());
		return null;
		
	}
	
	public String getPressure(){
		link_MoreDetails.click();
		TestLogger.logStep(StepStatus.PASS, "Pressure: " +lable_Pressure.getText());
		return null;
		
	}
	
	public String getHumidity(){
		TestLogger.logStep(StepStatus.PASS, "Humidity: " +lable_Humidity.getText());
		return null;
		
	}
	
}
