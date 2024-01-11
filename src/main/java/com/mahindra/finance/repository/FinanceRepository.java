package com.mahindra.finance.repository;

import java.util.List;

import com.mahindra.finance.model.LeadMaster;

public interface FinanceRepository {

	public LeadMaster checkLeadIdExist(String leadId);

	public void saveOrUpdate(LeadMaster leadMaster);

	public List<LeadMaster> getLeadList(String mobileNumber);

}
