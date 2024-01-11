package com.mahindra.finance.util;

public class StatusCodes {

	public static String getMessage(String messageString) {
		messageString = messageString.trim();
		if (messageString.contains("#")) {
			messageString = messageString.substring(messageString.indexOf("#") + 1, messageString.length());
		} else {
			messageString = MessageInfo.getMessageString(messageString);
			if (messageString.contains("#")) {
				messageString = messageString.substring(messageString.indexOf("#") + 1, messageString.length());
			}
		}
		return messageString;
	}
	
	public static Integer getCode(String messageString) {
		messageString = messageString.trim();
		Integer status = 0;
		if (messageString.contains("#")) {
			status = Integer.valueOf(messageString.substring(0, messageString.indexOf("#")).trim());
		} else {
			messageString = MessageInfo.getMessageString(messageString);
			if (messageString.contains("#")) {
				status = Integer.valueOf(messageString.substring(0, messageString.indexOf("#")).trim());
			}
		}
		return status;
	}
	
}
