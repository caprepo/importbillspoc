package com.capgemini.importbills.service;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.capgemini.importbills.model.Invoice;
import com.capgemini.importbills.model.Image_Upload;

@Service
public interface ImportBillsService {

	public List<Invoice> getInvoices();
	
	//public int save(String originalFilename, long size, String googleCloudLocation,Boolean cloudFlag);
public List<Image_Upload> getAllImageList();

//public void save(Image_Upload im);

void save(String originalFilename, long size, String googleCloudLocation, Boolean cloudFlag);

public int getId();
	
	/*
	public void uploadInvoiceDetailToDB();
	
	public void uploadInvoiceToCloudStorage();
	
	public void callVisionAPI();
	*/
	
}
