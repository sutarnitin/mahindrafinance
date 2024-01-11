package com.mahindra.finance.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseModel {

	@JsonProperty("status")
	private String status;

	@JsonProperty("data")
	private String data;

	@JsonProperty("errorResponse")
	private Object errorResponse;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Object getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(Object errorResponse) {
		this.errorResponse = errorResponse;
	}

}
