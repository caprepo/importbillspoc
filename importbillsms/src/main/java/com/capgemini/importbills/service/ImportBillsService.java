package com.capgemini.importbills.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capgemini.importbills.model.Invoice;

@Service
public interface ImportBillsService {

	public List<Invoice> getInvoices();
}
