package com.accuweather.test;

import org.testng.annotations.Test;

import com.accuweather.base.BaseTest;
import com.accuweather.pages.HomePage;

public class AccuWeatherTest extends BaseTest {

	@Test
	public void validateCurrentWeather() {
		HomePage homePage = new HomePage(getDriver());
		homePage.searchLocation();

	}
}
