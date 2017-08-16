package com.capgemini.importbills.web;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.importbills.model.Invoice;
import com.capgemini.importbills.service.ImportBillsService;

@RestController
@ComponentScan("com.capgemini.importbills")
@CrossOrigin
@EnableAutoConfiguration
public class ImportBillsController {

	@Autowired
	private ImportBillsService importBillsService;
	private static Logger log = Logger.getLogger(ImportBillsController.class);

	@RequestMapping(value="/importbillservices/invoices",method = RequestMethod.GET)
    public List<Invoice> getAllInvoices() {
	    System.out.println("In get All Invoices method");
	    List<Invoice> listInvoices=importBillsService.getInvoices();
	    return listInvoices;
    }
}
