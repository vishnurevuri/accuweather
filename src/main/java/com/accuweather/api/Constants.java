package com.accuweather.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Constants {
	public static final String YES = "YES";
	public static final String NO = "NO";
	public static final String POST = "POST";
	public static final String GET = "GET";
	public static final String DELETE = "DELETE";
	public static final String PUT = "PUT";
	public static final String AND = "&";
	public static final String NA = "NA";
	public static final String COMMA = ",";
	public static final String SOAP = "SOAP";
	public static final String REST = "REST";
	public static final String EQUAL = "=";
	public static final String ERROR_RESP_CODES = "400,401,405,415,499";
	public static final List<String> ERROR_RESP_CODES_LIST = new ArrayList<String>(
			Arrays.asList(ERROR_RESP_CODES.split(",")));
	public static final String JSON_CONTENT_TYPE = "application/json";
	public static final String XML_CONTENT_TYPE = "application/xml";
	public static final String TEXT_XML_CONTENT_TYPE = "text/xml";
	public static final String TEXT_CONTENT_TYPE = "text/plain";
	public static final String TEXT_HTML_CONTENT_TYPE = "text/html";
	public static final String JSON = "json";
	public static final String XML = "xml";
	public static final String JSON_PAYLOAD_PATH = "jsonpayloads";
	public static final String XML_PAYLOAD_PATH = "xmlpayloads";
	
	public static final String SEMI_COLON = ";";

}
