package com.mahindra.finance.service;

import java.text.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mahindra.finance.model.ResponseModel;
import com.mahindra.finance.util.MahindraFinanceException;

public interface FinanceService {

	public ObjectNode createLead(String request)
			throws JsonMappingException, JsonProcessingException, MahindraFinanceException, ParseException;

	public ObjectNode getLeadDetails(String request) throws JsonMappingException, JsonProcessingException;

}
