package com.capgemini.importbills.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.capgemini.importbills.dao.ImportBillsDao;
import com.capgemini.importbills.model.Invoice;
import com.capgemini.importbills.service.ImportBillsService;

@Component
@Service
public class ImportBillsServiceImpl implements ImportBillsService{
	static Logger logger = Logger.getLogger(ImportBillsServiceImpl.class);

	@Autowired
	private ImportBillsDao importBillsDao;

	@Value("${max.recent.transactions}")
	private Integer maxRecentTransactions;

	@Override
	public List<Invoice> getInvoices() {
		logger.info("In Service : Finding Invoices");
		return importBillsDao.findInvoices();
	}

}
