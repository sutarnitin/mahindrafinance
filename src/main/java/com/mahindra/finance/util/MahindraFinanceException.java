package com.mahindra.finance.util;

public class MahindraFinanceException extends Exception {

	private static final long serialVersionUID = 1L;
	private String exceptionMessage;
	private Integer errorCode;
	private String loggerMessage;

	// public constructor for MahindraFinanceException containing no arguments
	public MahindraFinanceException() {
	}

	public MahindraFinanceException(String exceptionMessage, Integer errorCode, String loggerMessage) {
		super();
		this.exceptionMessage = exceptionMessage;
		this.errorCode = errorCode;
		this.loggerMessage = loggerMessage;
	}

	public MahindraFinanceException(String errorMessage) {
		// Call constructor of parent Exception
		super(errorMessage);
	}

	public MahindraFinanceException(String errorCode, String... messageFields) {
		super(MessageInfo.getMessageString(errorCode));
		String errorMessage = StatusCodes.getMessage(errorCode);
		for (String field : messageFields) {
			int fieldCounter = 0;
			errorMessage = errorMessage.replace("{" + fieldCounter + "}", field);
		}
		this.exceptionMessage = errorMessage;
		this.loggerMessage = errorMessage;
		this.errorCode = StatusCodes.getCode(errorCode);
	}

	public String getMessage() {
		return exceptionMessage;
	}

	public String getLocalizedMessage() {
		return exceptionMessage;
	}
}
