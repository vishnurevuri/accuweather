package com.accuweather.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.FileAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents logs to captures in log file.
 * 
 * @author Vishnu
 *
 */

public class Log extends FileAppender {

	public static Logger logger = LoggerFactory.getLogger("");

	public static void startTestCase(String testName) {

		logger.info("****************************************************************************************");

		logger.info("$$$$$$$$$$$$$$$$$$$$$                 " + testName + "       $$$$$$$$$$$$$$$$$$$$$$$$$");

		logger.info("****************************************************************************************");

	}

	public static void endTestCase() {

		logger.info("XXXXXXXXXXXXXXXXXXXXXXX             " + "-E---N---D-" + "             XXXXXXXXXXXXXXXXXXXXXX");
		logger.info("X");
		logger.info("X");

	}

	public static void info(String message) {

		logger.info(message);

	}

	public static void warn(String message) {

		logger.warn(message);

	}

	public static void error(String message) {

		logger.error(message);

	}

	public static void debug(String message) {

		logger.debug(message);

	}

	@Override
	public void setFile(String fileName) {
		super.setFile(createLogFile(fileName));
	}

	private static String createLogFile(String fileName) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
		String sourceFolder = new File("reports" + File.separator + "html_report").getAbsolutePath();
		return sourceFolder + File.separator + fileName + "_" + dateFormat.format(new Date()) + ".log";
	}
}
