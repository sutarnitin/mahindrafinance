package com.mahindra.finance.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mahindra.finance.service.FinanceService;
import com.mahindra.finance.util.Constants;
import com.mahindra.finance.util.MahindraFinanceException;
import com.mahindra.finance.util.Utils;

@RestController
//@RequestMapping("/mahindrafinance/finance")
public class FinanceController implements Constants {

	Logger logger = LoggerFactory.getLogger(FinanceController.class);

	ObjectMapper mapper = new ObjectMapper();

	private Utils utils = Utils.getInstance();

	@Autowired
	private FinanceService financeService;

	@PostMapping(value = "/save-lead-details", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ObjectNode createLead(@RequestBody String request)
			throws JsonMappingException, JsonProcessingException {
		logger.info("START::createLead in FinanceController");
		ObjectNode responseNode = mapper.createObjectNode();
		ObjectNode errorResponse = mapper.createObjectNode();
		ArrayNode messageArrayNode = mapper.createArrayNode();
		try {
			JsonNode inputNode = mapper.readTree(request);
			// creating a logger
			utils.VerifyRequestJsonParam(inputNode, LEADID, FIRST_NAME, LAST_NAME, MOBILE_NUMBER, GENDER, DOB);
			responseNode = financeService.createLead(request);

		} catch (MahindraFinanceException mfe) {
			System.out.println("Excption::" + mfe);
			responseNode.put(STATUS, ERROR);
			errorResponse.put(CODE, E10011);
			messageArrayNode.add(mfe.getMessage());
			errorResponse.set(MESSAGES, messageArrayNode);
			responseNode.set(ERROR_RESPONSE, errorResponse);
		} catch (Exception e) {
			System.out.println("Excption::" + e);
			responseNode.put(STATUS, ERROR);
			errorResponse.put(CODE, E10011);
			messageArrayNode.add(e.getMessage());
			errorResponse.set(MESSAGES, messageArrayNode);
			responseNode.set(ERROR_RESPONSE, errorResponse);

		}
		logger.info("END::createLead in FinanceController");
		return responseNode;
	}

	@GetMapping(value = "/get-lead-details")
	public @ResponseBody ObjectNode getLeadDetails(@RequestBody String request)
			throws JsonMappingException, JsonProcessingException {
		logger.info("START::getLeadDetails in FinanceController");

		ObjectNode responseNode = mapper.createObjectNode();
		ObjectNode errorResponse = mapper.createObjectNode();
		ArrayNode messageArrayNode = mapper.createArrayNode();
		try {
			JsonNode inputNode = mapper.readTree(request);
			// creating a logger
			utils.VerifyRequestJsonParam(inputNode, MOBILE_NUMBER);
			responseNode = financeService.getLeadDetails(request);

		} catch (MahindraFinanceException mfe) {
			System.out.println("Excption::" + mfe);
			responseNode.put(STATUS, ERROR);
			errorResponse.put(CODE, E10011);
			messageArrayNode.add(mfe.getMessage());
			errorResponse.set(MESSAGES, messageArrayNode);
			responseNode.set(ERROR_RESPONSE, errorResponse);
		} catch (Exception e) {
			System.out.println("Excption::" + e);
			responseNode.put(STATUS, ERROR);
			errorResponse.put(CODE, E10011);
			messageArrayNode.add(e.getMessage());
			errorResponse.set(MESSAGES, messageArrayNode);
			responseNode.set(ERROR_RESPONSE, errorResponse);

		}
		logger.info("END::getLeadDetails in FinanceController");
		return responseNode;
	}

}
