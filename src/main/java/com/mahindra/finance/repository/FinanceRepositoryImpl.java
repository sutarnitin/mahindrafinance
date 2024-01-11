package com.mahindra.finance.repository;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mahindra.finance.model.LeadMaster;

@Repository
public class FinanceRepositoryImpl implements FinanceRepository {
	Logger logger = LoggerFactory.getLogger(FinanceRepositoryImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	@Transactional
	public LeadMaster checkLeadIdExist(String leadId) {
		logger.info("START::checkLeadIdExist in FinanceRepositoryImpl");
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<LeadMaster> criteriaQuery = criteriaBuilder.createQuery(LeadMaster.class);
		Root<LeadMaster> root = criteriaQuery.from(LeadMaster.class);
		Predicate mfleadId = criteriaBuilder.equal(root.get("leadId"), leadId);
		criteriaQuery.select(root).where(mfleadId);
		Query<LeadMaster> query = session.createQuery(criteriaQuery);
		logger.info("START::checkLeadIdExist in FinanceRepositoryImpl");
		return query.uniqueResult();
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public void saveOrUpdate(LeadMaster leadMaster) {
		logger.info("START::saveOrUpdate method for saving lead details in FinanceRepositoryImpl");
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(leadMaster);
	}

	@Override
	@Transactional
	public List<LeadMaster> getLeadList(String mobileNumber) {
		logger.info("START::getLeadList in FinanceRepositoryImpl");
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<LeadMaster> criteriaQuery = criteriaBuilder.createQuery(LeadMaster.class);
		Root<LeadMaster> root = criteriaQuery.from(LeadMaster.class);
		Predicate mobileNum = criteriaBuilder.equal(root.get("mobileNumber"), mobileNumber);
		criteriaQuery.select(root).where(mobileNum);
		Query<LeadMaster> query = session.createQuery(criteriaQuery);
		List<LeadMaster> list = query.getResultList();
		logger.info("START::getLeadList in FinanceRepositoryImpl");
		return list.size() > 0 ? list : null;
	}

}
