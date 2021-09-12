package com.accuweather.reports;

import java.io.File;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;

import org.openqa.selenium.WebDriver;

import com.accuweather.base.BaseTest;
import com.accuweather.reports.StepReportData.StepStatus;
import com.accuweather.util.Log;
import com.accuweather.util.ScreenshotUtil;
import com.aventstack.extentreports.Status;

public class TestStepLogger {

	private static final AtomicLong sequence = new AtomicLong(1);

	public static void logStep(StepStatus status, String stepDesc, WebDriver driver, Method methodName,
			boolean captureScreenShot) {

		String filePath = null;

		try {

			if (driver != null) {
				String scrnShotFileName = new StringBuilder(methodName.toString()).append("_seq")
						.append(sequence.getAndIncrement()).append(".png").toString();
				if (captureScreenShot) {
					ScreenshotUtil screenshotUtil = new ScreenshotUtil(driver, BaseTest.getScreenShotDir());
					// To take FullScreen shot of the page

					screenshotUtil.captureScreenShot(scrnShotFileName);

					filePath = new StringBuilder(BaseTest.getScreenShotDir().getName()).append(File.separator)
							.append(scrnShotFileName).toString();
					if ((status == StepStatus.FAIL && status == StepStatus.ERROR) || status == StepStatus.FAIL) {
						TestLogger.logScreenShot(Status.FAIL, stepDesc, filePath);
					} else if (status == StepStatus.PASS) {
						TestLogger.logScreenShot(Status.PASS, stepDesc, filePath);
					} else if (status == StepStatus.INFO) {
						TestLogger.logScreenShot(Status.INFO, stepDesc, filePath);
					} else if (status == StepStatus.WARN) {
						TestLogger.logScreenShot(Status.WARNING, stepDesc, filePath);
					}
				} else {
					if ((status == StepStatus.FAIL && status == StepStatus.ERROR) || status == StepStatus.FAIL) {
						TestLogger.logTestMessage(Status.FAIL, stepDesc);
					} else if (status == StepStatus.PASS) {
						TestLogger.logTestMessage(Status.PASS, stepDesc);
					} else if (status == StepStatus.INFO) {
						TestLogger.logTestMessage(Status.INFO, stepDesc);
					} else if (status == StepStatus.WARN) {
						TestLogger.logTestMessage(Status.WARNING, stepDesc);
					}
				}

			}
		} catch (Exception excp) {
			Log.error("Error while taking screenshots" + excp);
		}
	}

}
