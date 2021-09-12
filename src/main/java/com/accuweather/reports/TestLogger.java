package com.accuweather.reports;

import com.accuweather.base.BaseTest;
import com.accuweather.reports.StepReportData.StepStatus;
import com.accuweather.util.Log;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;

/**
 * Add the test steps into extent report
 * 
 * @author vishnu
 * @version 1.0
 */

public class TestLogger {

	private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

	/**
	 * Logs step status with or with out screenshot
	 * 
	 * @param status
	 * @param stepDesc
	 * @param captureScreenShot
	 */
	public static void logStep(StepStatus status, String stepDesc, boolean captureScreenShot) {

		TestStepLogger.logStep(status, stepDesc, BaseTest.getDriver(), BaseTest.getMethod(), captureScreenShot);
		logMsg(status, stepDesc);

	}

	/**
	 * Logs step status with out screenshot
	 * 
	 * @param status
	 * @param stepDesc
	 */
	public static void logStep(StepStatus status, String stepDesc) {

		TestStepLogger.logStep(status, stepDesc, BaseTest.getDriver(), BaseTest.getMethod(), false);
		logMsg(status, stepDesc);

	}

	/**
	 * Log the messages into Log file 
	 * @param status
	 * @param stepDesc
	 */
	private static void logMsg(StepStatus status, String stepDesc) {
		if (status.toString().equalsIgnoreCase("info") || status.toString().equalsIgnoreCase("pass")) {
			Log.info(stepDesc);
		} else if (status.toString().equalsIgnoreCase("warn")) {
			Log.warn(stepDesc);
		} else if (status.toString().equalsIgnoreCase("error") || status.toString().equalsIgnoreCase("fail")) {
			Log.error(stepDesc);
		}
	}

	public static ExtentTest createExtentTest(String testCaseName) {
		ExtentTest extentTest = BaseTest.extentReport.createTest(testCaseName);
		test.set(extentTest);
		return extentTest;
	}

	public static void assignCategory(String categoryName) {
		test.get().assignCategory(categoryName);
	}

	public static void flushTests() {
		BaseTest.extentReport.flush();
	}

	public static void logTestMessage(Status status, String desc) {
		try {
			test.get().log(status, desc);
		} catch (Exception e) {
			Log.error(e.toString());
		}
	}

	public static void logScreenShot(Status status, String desc, String imageLoc) {
		try {
			MediaEntityModelProvider imagePath = null;
			imagePath = MediaEntityBuilder.createScreenCaptureFromPath(imageLoc).build();
			test.get().log(status, desc, imagePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
