package com.mahindra.finance.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jboss.logging.Logger;

public class MessageInfo {
	static final Logger logger = Logger.getLogger(MessageInfo.class);

	private MessageInfo() {

	}

	private static MessageInfo instance = null;
	private static Properties properties = null;

	public static MessageInfo getInstance() {
		if (instance == null) {
			instance = new MessageInfo();
			properties = new MessageInfo().getProperty("message.properties");
		}
		return instance;
	}

	public Properties getProperty(String propertyName) {
		Properties properties = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyName);
		if (inputStream != null) {
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				System.out.println("Exception :: " + e.getMessage());
			}
		} else {
			try {
				throw new FileNotFoundException("property file" + propertyName + "not found in the classpath");
			} catch (FileNotFoundException e) {
				System.out.println("Exception :: " + e.getMessage());
			}
		}
		return properties;
	}
	
	public static String getMessageString(String messageCode) {
		getInstance();
		return properties.getProperty(messageCode) != null ? properties.getProperty(messageCode) : "";
		
	}
	
}
