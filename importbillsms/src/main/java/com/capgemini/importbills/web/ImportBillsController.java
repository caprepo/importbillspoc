package com.capgemini.importbills.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.capgemini.importbills.helper.StorageSample;
import com.capgemini.importbills.helper.VisionOCRAnalysis;
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
	
	@RequestMapping(value="/importbillservices/uploadfile",method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file) {
	    System.out.println("In uploadFile method");
	    String response = "";
	    String UPLOADED_FOLDER = "/tmp";
	    String ocrResp = null;
	    
	    if (file.isEmpty()) {
            return "empty";
        }
	    try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            System.out.println("Length = " + bytes.length);
            Path path = Paths.get(UPLOADED_FOLDER + "/" + file.getOriginalFilename());
            System.out.println(path.getFileName());
            Files.write(path, bytes);

			System.out.println("Before Upload " + file.getOriginalFilename());
			File file2 = new File(UPLOADED_FOLDER + "/" + file.getOriginalFilename());
			
			StorageSample.uploadFile(file.getOriginalFilename(), "image/jpg", file2, "poc-importbills");
			System.out.println("After Upload 1" + file.getOriginalFilename());
			VisionOCRAnalysis ocr = new VisionOCRAnalysis();
			System.out.println("Before ocr " + file.getOriginalFilename());
			ocrResp = ocr.OCRAnalysis(file.getOriginalFilename());
			System.out.println("After ocr" + file.getOriginalFilename());
			System.out.println("response length = " + ocrResp.length());
            
            response = "success";

        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	    return response;
    }
}
