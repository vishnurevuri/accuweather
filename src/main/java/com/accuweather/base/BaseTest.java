package com.accuweather.base;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.accuweather.exception.FrameworkException;
import com.accuweather.util.Log;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Represents the base testng class that provide common abilities such as
 * retrieving test data, logging capabilities, common initializations that needs
 * to be extended by all other test classes for reusing the capabilities.
 *
 * @author vishnu
 * @verion 1.0
 */

public class BaseTest {

	public static Config config;
	private Properties properties = new Properties();

	protected static String FS = File.separator;

	public static ThreadLocal<Method> method = new ThreadLocal<Method>();

	private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

	private WebDriver driver;

	/**
	 * Initializes BaseTest.
	 */
	public BaseTest() {
		Log.debug("Instance created " + this.getClass().getName());

	}

	/**
	 * Initialize the entire test suite with relevant test
	 */
	@BeforeSuite(alwaysRun = true)
	public void initializeConfig(ITestContext context) {
		Log.debug("InitializeConfig called");
		initializeBaseConfig();

	}

	/**
	 * Loads multiple Config files from resource folder.
	 */
	private void initializeBaseConfig() {
		createDirectory("reports");
		createOrCleanDirectory("reports" + FS + "html_report");
		createOrCleanDirectory("reports" + FS + "html_report" + FS + "screenshots");
		try {
			String env = System.getProperty("env");
			String additionalConfigFiles = System.getProperty("additional.configs");
			String configFileDir = System.getProperty("user.dir") + FS + "src" + FS + "test" + FS + "resources";
			List<String> additionalConfigFilesList = Collections.emptyList();

			final List<File> configFiles = (List<File>) FileUtils.listFiles(new File(configFileDir),
					new String[] { "conf" }, true);

			if (configFiles.isEmpty()) {
				throw new FrameworkException("Configuration files not found");
			}

			if (StringUtils.isNotBlank(additionalConfigFiles)) {
				additionalConfigFilesList = Stream.of(additionalConfigFiles.split(", ")).collect(Collectors.toList());
			}

			for (File file : configFiles) {
				if (file.getName().startsWith("common")
						|| (((StringUtils.isNotBlank(env) && file.getName().startsWith(env))
								|| (StringUtils.isNotBlank(env) && !additionalConfigFilesList.isEmpty()
										&& additionalConfigFilesList.contains(file.getName())))
								|| StringUtils.isBlank(env))) {
					Config config = ConfigFactory.parseFile(new File(file.getAbsolutePath()));
					properties.putAll(toProperties(config));
					Log.info("Configuration details loaded for the config file : " + file.getName());
				}
			}

			// To load Environment variables.
			properties.putAll(toProperties(ConfigFactory.load()));
			config = ConfigFactory.parseProperties(properties);
			Log.info(config.toString());

		} catch (Exception iexcp) {
			Log.error("Error while loding data from config files." + iexcp);
		}
	}

	/**
	 * Creates directory if not exists.
	 * 
	 * @param directory
	 */
	public void createDirectory(String directory) {
		try {
			File file = new File(System.getProperty("user.dir") + "/" + directory);
			if (!file.exists()) {
				file.mkdir();
				Log.error("Successfully created the Directory : " + directory);
			}
		} catch (Exception ex) {
			Log.error("Failed to create the Directory : " + directory);
		}
	}

	/**
	 * Creates or Cleans directory.
	 * 
	 * @param directory
	 */
	public void createOrCleanDirectory(String directory) {
		try {
			File file = new File(System.getProperty("user.dir") + FS + directory);
			if (!file.exists()) {
				file.mkdir();
			} else {
				FileUtils.cleanDirectory(new File(directory));
			}
		} catch (Exception ex) {
			Log.info("Failed to clean the Directory : " + directory);
		}
	}

	/**
	 * Returns the test property file.
	 *
	 * @return
	 */
	public static Config getTestConfig() {
		return config;
	}

	/**
	 * Returns String Value if exception returns false.
	 * 
	 * @param key
	 * @return
	 */
	public static String getStringProperty(String key) {
		try {
			return getTestConfig().getString(key);
		} catch (Exception ex) {
			Log.warn("Property Missed in configration please verify --> " + key);
			return "";
		}
	}

	/**
	 * Returns String Value if exception returns false.
	 * 
	 * @param key
	 * @return
	 */
	public static int getIntegerProperty(String key) {
		try {
			return getTestConfig().getInt(key);
		} catch (Exception ex) {
			Log.warn("Property Missed in configration please verify --> " + key);
			return 0;
		}
	}

	/**
	 * Returns Boolean Value if exception returns false.
	 * 
	 * @param key
	 * @return
	 */
	public static boolean getBooleanProperty(String key) {
		try {
			return getTestConfig().getBoolean(key);
		} catch (Exception ex) {
			Log.warn("Property Missed in configration please verify --> " + key);
			return false;
		}
	}

	/**
	 * Converts config file to properties.
	 * 
	 * @param config
	 * @return
	 */
	private Properties toProperties(Config config) {
		Properties properties = new Properties();
		config.entrySet().forEach(e -> properties.setProperty(e.getKey(), config.getString(e.getKey())));
		return properties;
	}

	@BeforeMethod
	public void launchBrowser(Method method, ITestResult result) {
		Log.startTestCase(method.getName());

		String browserName = "chrome";
		if (browserName.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir") + getStringProperty("iedriverpath"));

			driver = new InternetExplorerDriver();

		} else if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + getStringProperty("chromeDriverpath"));

			driver = new ChromeDriver();

		} else {
			Log.error("Invalid Browser");

		}
		setWebDriver(driver);
		setMethod(method);
		result.setAttribute("web.driver", driver);
		result.setAttribute("method", method.getName());

		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(getTestConfig().getInt("implicitlyWait"), TimeUnit.SECONDS);
		getDriver().manage().timeouts().pageLoadTimeout(getTestConfig().getInt("pageLoadTimeout"), TimeUnit.SECONDS);
		getDriver().manage().timeouts().setScriptTimeout(getTestConfig().getInt("scriptTimeout"), TimeUnit.SECONDS);
		driver.get(getStringProperty("url"));
	}

	@AfterMethod
	public void closeBrowser() {
		WebDriver driver = getDriver();
		if ((driver != null)) {
			driver.quit();
			Log.info("Successfully close the browser");
			Log.endTestCase();
		}
	}

	/**
	 * Sets WebDriver reference in ThreadLocal object.
	 * 
	 * @param driver
	 */
	public void setWebDriver(WebDriver driver) {
		BaseTest.webDriver.set(driver);
	}

	/**
	 * Returns an instance of the web driver.
	 * 
	 * @return
	 */
	public static WebDriver getDriver() {
		return BaseTest.webDriver.get();

	}

	public static Method getMethod() {
		return BaseTest.method.get();
	}

	public static void setMethod(Method method) {
		BaseTest.method.set(method);
	}

}