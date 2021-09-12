package com.accuweather.test;

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
		homePage.getTemp();
		homePage.getPressure();
		homePage.getHumidity();

		APITest apiTest = new APITest();
		Response weatherreport = apiTest.invokeService();
		System.out.println(weatherreport.toString());
	}
}
