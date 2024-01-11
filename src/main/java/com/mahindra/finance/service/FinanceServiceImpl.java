package com.mahindra.finance.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mahindra.finance.model.LeadMaster;
import com.mahindra.finance.repository.FinanceRepository;
import com.mahindra.finance.util.Constants;
import com.mahindra.finance.util.MahindraFinanceException;
import com.mahindra.finance.util.Utils;

@Service
public class FinanceServiceImpl implements FinanceService, Constants {
	Logger logger = LoggerFactory.getLogger(FinanceServiceImpl.class);
	ObjectMapper mapper = new ObjectMapper();

	private Utils utils = Utils.getInstance();

	@Autowired
	FinanceRepository financeRepository;

	@Override
	public ObjectNode createLead(String request)
			throws JsonMappingException, JsonProcessingException, MahindraFinanceException, ParseException {
		logger.info("START::createLead in FinanceServiceImpl");
		ObjectNode responseNode = mapper.createObjectNode();
		JsonNode inputNode = mapper.readTree(request);
		String leadId = inputNode.get(LEADID).asText();
		// To check the leadId contains only integers
		if (Utils.isDigit(leadId)) {

			String firstName = inputNode.get(FIRST_NAME).asText();
			if (!Utils.isLetter(firstName)) {
				throw new MahindraFinanceException(FIRSTNAME_CONTAINS_SPACES_INTEGERS, firstName);
			}

			String middleName = inputNode.get(MIDDLE_NAME).asText();
			if (!Utils.isLetter(middleName)) {
				throw new MahindraFinanceException(MIDDLENAME_CONTAINS_SPACES_INTEGERS, middleName);
			}

			String lastName = inputNode.get(LAST_NAME).asText();
			if (!Utils.isLetter(lastName)) {
				throw new MahindraFinanceException(LASTNAME_CONTAINS_SPACES_INTEGERS);
			}

			String mobileNumber = inputNode.get(MOBILE_NUMBER).asText();
			if (Utils.isDigit(mobileNumber)) {

				if (!mobileNumber.matches(MOBILE_REGEX)) {
					throw new MahindraFinanceException(INVALID_MOBILE_NUMBER, mobileNumber);
				}
			} else {
				throw new MahindraFinanceException(MOBILE_NUMBER_CONTAINS_INTEGER, mobileNumber);
			}

			String gender = inputNode.get(GENDER).asText();
			if (Utils.isLetter(gender)) {
				if ((!MALE.equals(gender)) && (!FEMALE.equals(gender)) && (!OTHERS.equals(gender))) {
					throw new MahindraFinanceException(GENDER_SHOLUD_ONE_FROM_MALE_FEMALE_OTHERS, gender);
				}
			} else {
				throw new MahindraFinanceException(INVALID_GENDER, gender);
			}

			String dateOfBirth = inputNode.get(DOB).asText();
			Date dob = new SimpleDateFormat(DATE_PATTERN).parse(dateOfBirth);

			String email = inputNode.get(EMAIL).asText();
			// String emailRegex = EMAIL_REGEX;
			if (!email.matches(EMAIL_REGEX)) {
				throw new MahindraFinanceException(INVALID_EMAIL, email);
			}

			LeadMaster leadMaster = financeRepository.checkLeadIdExist(leadId);
			if (leadMaster == null) {
				LeadMaster leadMst = new LeadMaster();
				leadMst.setLeadId(Integer.parseInt(leadId));
				leadMst.setFirstName(firstName);
				leadMst.setMiddleName(middleName);
				leadMst.setLastName(lastName);
				leadMst.setMobileNumber(mobileNumber);
				leadMst.setGender(gender);
				leadMst.setDob(dob);
				leadMst.setEmail(inputNode.get(EMAIL).asText());
				leadMst.setCreatedBy(MAHINDRA);
				leadMst.setUpdatedBy(MAHINDRA);
				leadMst.setCreatedDate(new Date());
				leadMst.setUpdatedDate(new Date());

				financeRepository.saveOrUpdate(leadMst);
				responseNode.put(SUCCESS, SUCCESS);
				responseNode.put(DATA, "Created Leads Successfully");

			} else {
				ObjectNode errorResponse = mapper.createObjectNode();
				errorResponse.put(CODE, E10010);
				ArrayNode messages = mapper.createArrayNode();
				messages.add("Lead Already Exists in the database with the lead id " + leadId);
				errorResponse.set(MESSAGES, messages);
				responseNode.put(STATUS, ERROR);
				responseNode.set(ERROR_RESPONSE, errorResponse);
			}
		} else {
			ObjectNode errorResponse = mapper.createObjectNode();
			errorResponse.put(CODE, E10010);
			ArrayNode messages = mapper.createArrayNode();
			messages.add("LeadId contains characters : " + leadId);
			errorResponse.set(MESSAGES, messages);
			responseNode.put(STATUS, ERROR);
			responseNode.set(ERROR_RESPONSE, errorResponse);
		}
		logger.info("END::createLead in FinanceServiceImpl");
		return responseNode;
	}

	@Override
	public ObjectNode getLeadDetails(String request) throws JsonMappingException, JsonProcessingException {
		logger.info("START::getLeadDetails in FinanceServiceImpl");
		ObjectNode responseNode = mapper.createObjectNode();
		ArrayNode messageArrayNode = mapper.createArrayNode();
		JsonNode inputNode = mapper.readTree(request);
		String mobileNumber = inputNode.get(MOBILE_NUMBER).asText();
		List<LeadMaster> leadMasterList = financeRepository.getLeadList(mobileNumber);
		DateFormat df = new SimpleDateFormat(DATE_PATTERN);
		if (leadMasterList == null) {
			ObjectNode errorResponse = mapper.createObjectNode();
			errorResponse.put(CODE, E10011);
			messageArrayNode.add("No Lead found with the Mobile Number");
			errorResponse.set(MESSAGES, messageArrayNode);
			responseNode.put(ERROR, ERROR);
			responseNode.set(ERROR_RESPONSE, errorResponse);
			return responseNode;
		} else {
			ArrayNode leadArrayNode = mapper.createArrayNode();
			leadMasterList.stream().forEach(e -> {
				ObjectNode leadDetailsNode = mapper.createObjectNode();
				leadDetailsNode.put(LEADID, e.getLeadId());
				leadDetailsNode.put(FIRST_NAME, e.getFirstName());
				leadDetailsNode.put(MIDDLE_NAME, e.getMiddleName());
				leadDetailsNode.put(LAST_NAME, e.getLastName());
				leadDetailsNode.put(MOBILE_NUMBER, e.getMobileNumber());
				leadDetailsNode.put(GENDER, e.getGender());
				String dob = df.format(e.getDob());
				leadDetailsNode.put(DOB, dob);
				leadDetailsNode.put(EMAIL, e.getEmail());
				leadArrayNode.add(leadDetailsNode);
			});
			responseNode.put(STATUS, SUCCESS);
			responseNode.set(DATA, leadArrayNode);
		}
		logger.info("END::getLeadDetails in FinanceServiceImpl");
		return responseNode;
	}

}
