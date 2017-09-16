package com.capgemini.importbills.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.capgemini.importbills.model.Invoice;

@Service
public interface ImportBillsService {

	public List<Invoice> getInvoices();
	
	public void save(String originalFilename, long size, String googleCloudLocation,Boolean cloudFlag);
	
	/*
	public void uploadInvoiceDetailToDB();
	
	public void uploadInvoiceToCloudStorage();
	
	public void callVisionAPI();
	*/
	
}
