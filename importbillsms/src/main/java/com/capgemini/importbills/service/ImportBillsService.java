package com.capgemini.importbills.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.capgemini.importbills.model.Invoice;
import com.capgemini.importbills.model.Image_Upload;

@Service
public interface ImportBillsService {

	public List<Invoice> getInvoices();
	
	public void save(String originalFilename, long size, String googleCloudLocation,Boolean cloudFlag);
public List<Image_Upload> getAllImageList();
	
	/*
	public void uploadInvoiceDetailToDB();
	
	public void uploadInvoiceToCloudStorage();
	
	public void callVisionAPI();
	*/
	
}
