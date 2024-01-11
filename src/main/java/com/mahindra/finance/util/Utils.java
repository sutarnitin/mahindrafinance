package com.mahindra.finance.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

public class Utils implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(Utils.class);

	private Properties properties;
	public static Utils instance;

	private Utils() {
		this.properties = new Properties();
	}

	public static Utils getInstance() {
		if (instance == null) {
			instance = new Utils();
			InputStream inputStream = instance.getClass().getClassLoader()
					.getResourceAsStream("application.properties");
			try {
				instance.properties.load(inputStream);
			} catch (IOException ex) {
				logger.error("Error ::", ex);
			}
		}
		return instance;
	}

	// Verify request null or empty
	public void VerifyRequestJsonParam(JsonNode requestNode, String... paramList) throws MahindraFinanceException {
		for (String reqParam : paramList) {
			if (!requestNode.has(reqParam)) {
				logger.error("Parameter not found in request : " + reqParam);
				// throw custom exception
				throw new MahindraFinanceException("REQUIRED_PARAMETER_NOT_FOUND", reqParam);
			}
			if (requestNode.get(reqParam) == null) {
				logger.error("Parameter is null : " + reqParam);
				// throw custom exception
				throw new MahindraFinanceException("REQUIRED_PARAMETER_EMPTY", reqParam);
			}

			if (requestNode.get(reqParam).isContainerNode()) {
				continue;
			}

			if (requestNode.get(reqParam).asText().isEmpty()) {
				logger.error("Parameter is empty : " + reqParam);
				// throw custom exception
				throw new MahindraFinanceException("REQUIRED_PARAMETER_EMPTY", reqParam);
			}
		}
	}

	public static boolean isDigit(String str) {
		return str.chars().allMatch(Character::isDigit);
	}

	public static boolean isLetter(String str) {
		return str.chars().allMatch(Character::isLetter);
	}
}
