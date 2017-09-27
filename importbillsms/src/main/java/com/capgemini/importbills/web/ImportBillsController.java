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
import com.capgemini.importbills.model.Image_Upload;
import com.capgemini.importbills.service.ImportBillsService;
import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;

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
	    List<Invoice> listInvoices=importBillsService.getInvoices();
	    return listInvoices;
    }
	
	@RequestMapping(value = "/importbillservices/imageList", method = RequestMethod.GET)
	public List<Image_Upload> getAllImageList() {
		List<Image_Upload> listOfImages = importBillsService.getAllImageList();
		return listOfImages;
	}
	
	@RequestMapping(value="/importbillservices/uploadfile",method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
	    System.out.println("In uploadFile method");
	    String response = "";
	    String UPLOADED_FOLDER = "/tmp";
	    String ocrResp = null;
	    String googleCloudLocation = null;
	    Boolean cloudFlag =false;
	    
	    if (file.isEmpty()) {
            return "empty";
        }
	    try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + "/" + file.getOriginalFilename());
            Files.write(path, bytes);

			File file2 = new File(UPLOADED_FOLDER + "/" + file.getOriginalFilename());
			StorageSample.uploadFile(file.getOriginalFilename(), "image/jpg", file2, "poc-importbills");
			VisionOCRAnalysis ocr = new VisionOCRAnalysis();
			ocrResp = ocr.OCRAnalysis(file.getOriginalFilename());
			//googleCloudLocation = "gs://poc-importbills/"+file.getOriginalFilename();
			googleCloudLocation= "http://poc-importbills.storage.googleapis.com/"+file.getOriginalFilename();
			if(googleCloudLocation != null){
				cloudFlag = true;
            
			}
             importBillsService.save(file.getOriginalFilename(),file.getSize(),googleCloudLocation,cloudFlag);
			 TopicName topicName = TopicName.create("CG-HSBC-PoC", "importbills");
			Publisher publisher = null;
			 ApiFuture<String> messageIdFuture = null;
try {
	publisher = Publisher.defaultBuilder(topicName).build();
	System.out.println("DEfaulL"+publisher);
		        ByteString data = ByteString.copyFromUtf8(googleCloudLocation);
		        System.out.println("DATAAAAAAa"+data);
		        PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
		        messageIdFuture = publisher.publish(pubsubMessage);
		      System.out.println("PUBLISHHH"+messageIdFuture);
} finally{
}
String messageId = messageIdFuture.get();
		        System.out.println("published with message ID: " + messageId);
		      if (publisher != null) {
		        publisher.shutdown();
		      }           
		   response = "success";

        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	    return response;
    }
}
