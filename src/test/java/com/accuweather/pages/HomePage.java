package com.accuweather.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.accuweather.reports.StepReportData.StepStatus;

import io.restassured.response.Response;

import com.accuweather.reports.TestLogger;
import com.accuweather.base.BasePage;
import com.accuweather.base.BaseTest;
import com.accuweather.exception.FailedVerificationException;

public class HomePage extends BasePage {

	@FindBy(name = "query")
	private WebElement text_Search;

	@FindBy(xpath = "//h2[normalize-space(text())='Current Weather']/parent::div/descendant::div[@class='temp']")
	private WebElement lable_temp;

	@FindBy(xpath = "//div[@class='current-weather-details']/div[@class='right']//div[text()='Pressure']/following-sibling::div")
	private WebElement lable_Pressure;

	@FindBy(xpath = "//div[@class='current-weather-details']/div[@class='left']//div[text()='Humidity']/following-sibling::div")
	private WebElement lable_Humidity;

	@FindBy(xpath = "//span[@class='cur-con-weather-card__cta']/span[text()='More Details']")
	private WebElement link_MoreDetails;

	public HomePage(WebDriver webDriver) {
		super(webDriver);
	}

	public void searchLocation() {
		String city = BaseTest.getStringProperty("city");
		text_Search.sendKeys("London, London, GB");
		text_Search.sendKeys(Keys.ENTER);
		TestLogger.logStep(StepStatus.PASS, "Search with City: " + city,true);
	}

	public String getTemp() {
		WebDriverWait wait = new WebDriverWait(getDriver(),30);
		wait.until(ExpectedConditions.visibilityOf(lable_temp));
		String temp = lable_temp.getText();
		temp = temp.substring(0, temp.length() - 2);
		TestLogger.logStep(StepStatus.PASS, "Tempratute: " + temp,true);
		return temp;

	}

	public String getPressure() {
		link_MoreDetails.click();
		Actions action = new Actions(getDriver());
		action.moveByOffset(0, 0).click().build().perform();
		WebDriverWait wait = new WebDriverWait(getDriver(),30);
		wait.until(ExpectedConditions.visibilityOf(lable_Pressure));
		String pressure = lable_Pressure.getText();
		pressure = pressure.substring(2, pressure.length() - 3);
		TestLogger.logStep(StepStatus.PASS, "Pressure: " + pressure,true);
		return pressure;

	}

	public String getHumidity() {
		String humidity = lable_Humidity.getText();
		humidity = humidity.substring(0, humidity.length() - 1);
		TestLogger.logStep(StepStatus.PASS, "Humidity: " + humidity);
		return humidity;

	}

	public List<Map<String, Double>> getWeatherDetailsFromUI() {
		List<Map<String, Double>> expectedResultset = new ArrayList<Map<String, Double>>();
		Map<String, Double> rows = null;
		rows = new LinkedHashMap<String, Double>();
		rows.put("temp", Double.parseDouble(getTemp()));
		rows.put("pressure", Double.parseDouble(getPressure()));
		rows.put("humidity", Double.parseDouble(getHumidity()));
		expectedResultset.add(rows);
		return expectedResultset;

	}

	public List<Map<String, Double>> getWeatherDetailsFromAPI(Response weatherreport) {
		List<Map<String, Double>> expectedResultset = new ArrayList<Map<String, Double>>();
		Map<String, Double> rows = null;
		rows = new LinkedHashMap<String, Double>();
		rows.put("temp", Double.parseDouble(weatherreport.path("main.temp").toString()));
		rows.put("pressure", Double.parseDouble(weatherreport.path("main.pressure").toString()));
		rows.put("humidity", Double.parseDouble(weatherreport.path("main.humidity").toString()));
		expectedResultset.add(rows);
		return expectedResultset;

	}

	

	public void validateTemperatureDetails(List<Map<String, Double>> expectedResult,
			List<Map<String, Double>> actualResult) {

		String columnsToValidate = null;
		try {

			columnsToValidate = "temp&pressure&humidity";

			List<String> columns = Arrays.asList(columnsToValidate.split("&"));
			for (String column : columns) {
				boolean result = isVarianceInGivenLimitRange(expectedResult.get(0).get(column),
						actualResult.get(0).get(column), column);
				if (result) {
					TestLogger.logStep(StepStatus.PASS,
							"Validate Field: " + column + "<b><font color=" + "green" + "><br> Expected value : <br>"
									+ expectedResult.get(0).get(column).toString() + ".<br> Actual value : <br>"
									+ actualResult.get(0).get(column).toString() + ".</font><b><br>",
							false);
				} else {
					TestLogger.logStep(StepStatus.FAIL,
							"Validate Field: " + column + "<b><font color=" + "green" + "><br> Expected value : <br>"
									+ expectedResult.get(0).get(column).toString() + ".<br> Actual value : <br>"
									+ actualResult.get(0).get(column).toString() + ".</font><b><br>",
							false);
				}
			}

		} catch (Exception e) {

			throw new FailedVerificationException("Failed to validate Temprature Details ");
		}
	}

	public boolean isVarianceInGivenLimitRange(double expectedValue, double actualValue, String fieldValue) {

		boolean status = false;
		try {
			double varianceValue = expectedValue - actualValue;
			if (fieldValue.equals("temp")) {
				if (varianceValue >= -5.0 && varianceValue <= 5.0) {
					status = true;
				}
			}
			if (fieldValue.equals("pressure")) {
				if (varianceValue >= -20.0 && varianceValue <= 20.0) {
					status = true;
				}
			}
			if (fieldValue.equals("humidity")) {
				if (varianceValue >= -10.0 && varianceValue <= 10.0) {
					status = true;
				}
			}

		} catch (Exception e) {

			throw new FailedVerificationException("Failed to validate Temprature Details ");
		}
		return status;
	}

}
