package com.accuweather.api;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accuweather.base.BaseTest;
import com.accuweather.reports.StepReportData.StepStatus;
import com.accuweather.reports.TestLogger;
import com.accuweather.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Class for initializing request parameter, Callig the REST Services and for
 * validating the Response of the service calls..
 * 
 * @author rvishnu
 * @verion 1.0
 * 
 */

public class APITest {

	private static Logger logger = LoggerFactory.getLogger(APITest.class);

	/**
	 * Call the Rest API service and get the response.
	 * 
	 * @param testdata
	 * @return
	 * @throws Exception
	 */
	public Response invokeService() {

		String baseURL = BaseTest.getStringProperty("baseURL");
		String requestURL = BaseTest.getStringProperty("RequestURL");
		String requestMethod = BaseTest.getStringProperty("RequestMethod");

		TestLogger.logStep(StepStatus.INFO, "<b>End Point URL: <br> </b>" + baseURL + requestURL);
		TestLogger.logStep(StepStatus.INFO, "<b>Method Type: <br> </b>" + requestMethod);
		Response response = null;

		if (isNotEmpty(baseURL)) {			
			RestAssured.baseURI = baseURL.trim();

		}
		final RequestSpecification reqSpecification = RestAssured.given();

		Map<String, String> queryParamsMap = createMap(
				"q=London,London,GB&units=metric&appid=7fe67bf08c80ded756e598d6f8fedaea");
		reqSpecification.queryParams(queryParamsMap);
		TestLogger.logStep(StepStatus.INFO, "<b>Query Params:<br> </b>" + printMap(queryParamsMap));

		if (Constants.GET.equalsIgnoreCase(requestMethod)) {
			response = reqSpecification.get(requestURL).andReturn().then().extract().response();
		}

		if (response != null) {
			logger.info("Response Body" + response.asString());
			logger.info("Response Content Type = " + response.getContentType());
			logPayload("Response", response.getContentType().split(Constants.SEMI_COLON)[0].trim(),
					response.asString());
		}

		return response;
	}

	/**
	 * Logs payload into the report.
	 * 
	 * @param text
	 * @param contentType
	 * @param requestPayload
	 */
	private void logPayload(String text, String contentType, String requestPayload) {
		requestPayload = formatPayload(requestPayload);
		TestLogger.logStep(StepStatus.INFO, text + " Payload" + "<textarea>" + requestPayload + "</textarea>");
	}

	/**
	 * Prepares map object based on provided data. Ex: key1=value1&key2=value2
	 * 
	 * @param expResponseString
	 * @return
	 */
	private Map<String, String> createMap(String expResponseString) {
		Map<String, String> maptest = new HashMap<String, String>();
		if (isNotEmpty(expResponseString)) {
			final String[] texct = expResponseString.split(Constants.AND);
			for (int j = 0; j < texct.length; j++) {
				final String[] text1 = texct[j].split(Constants.EQUAL, 2);
				if (text1.length == 1) {
					Log.info("Key : " + text1[0] + " Value is empty setting empty value");
					maptest.put(text1[0], StringUtils.EMPTY);
				} else {
					maptest.put(text1[0], text1[1]);
				}
			}
		}
		return maptest;
	}

	/**
	 * Checks whether given value empty or not.
	 * 
	 * @param value
	 * @return
	 */
	public boolean isNotEmpty(String value) {
		if (StringUtils.isNotBlank(value)) {
			return true;
		}
		return false;
	}

	/**
	 * Formats json payload.
	 * 
	 * @param json
	 * @return
	 */
	private String formatJson(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Object jsonObject = mapper.readValue(json, Object.class);
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
			logger.info(json);
		} catch (IOException e) {
			logger.error(e.toString());
		}
		return json;
	}

	@SuppressWarnings("rawtypes")
	public String printMap(Map<String, String> map) {
		String mapToString = "";
		if (map != null && map.size() > 0) {
			for (Map.Entry entry : map.entrySet()) {
				mapToString = mapToString + "<font color=" + "blue" + ">" + entry.getKey() + ":</font>   "
						+ entry.getValue() + "<br>";
			}
		}
		return mapToString;
	}

	/**
	 * Formats the payload.
	 * 
	 * @param payload
	 * @return
	 */
	public String formatPayload(String payload) {
		return formatXML(payload);
	}

	/**
	 * Formats xml payload
	 * 
	 * @param input
	 * @return
	 */
	private String formatXML(String input) {
		try {
			Document doc = DocumentHelper.parseText(input);
			StringWriter sw = new StringWriter();
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setIndent(true);
			format.setIndentSize(3);
			XMLWriter xw = new XMLWriter(sw, format);
			xw.write(doc);
			return sw.toString();
		} catch (Exception e) {
			input = formatJson(input);
			return input;
		}
	}

}
