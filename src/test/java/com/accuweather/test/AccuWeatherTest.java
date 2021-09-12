package com.accuweather.test;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.accuweather.api.APITest;
import com.accuweather.base.BaseTest;
import com.accuweather.pages.HomePage;

import io.restassured.response.Response;

public class AccuWeatherTest extends BaseTest {

	@Test
	public void configFileTest() {
		HomePage homePage = new HomePage(getDriver());
		homePage.searchLocation();

		List<Map<String, Double>> valuesFromUI = homePage.getWeatherDetailsFromUI();

		APITest apiTest = new APITest();
		Response weatherreport = apiTest.invokeService();

		List<Map<String, Double>> valuesFromAPI = homePage.getWeatherDetailsFromAPI(weatherreport);
		homePage.validateTemperatureDetails(valuesFromUI, valuesFromAPI);

	}
}
