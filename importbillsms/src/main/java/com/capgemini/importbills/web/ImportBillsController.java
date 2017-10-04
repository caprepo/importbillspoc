package com.capgemini.importbills.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.sql.ResultSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.capgemini.importbills.dao.ImportBillsDao;
import com.capgemini.importbills.helper.StorageSample;
import com.capgemini.importbills.helper.VisionOCRAnalysis;
import com.capgemini.importbills.model.Invoice;
import com.capgemini.importbills.model.Image_Upload;
import com.capgemini.importbills.service.ImportBillsService;
import com.google.api.core.ApiFuture;
import com.google.api.gax.core.ExecutorProvider;
import com.google.api.gax.core.InstantiatingExecutorProvider;
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

	@RequestMapping(value = "/importbillservices/invoices", method = RequestMethod.GET)
	public List<Invoice> getAllInvoices() {
		List<Invoice> listInvoices = importBillsService.getInvoices();
		return listInvoices;
	}

	@RequestMapping(value = "/importbillservices/imageList", method = RequestMethod.GET)
	public List<Image_Upload> getAllImageList() {
		List<Image_Upload> listOfImages = importBillsService.getAllImageList();
		return listOfImages;
	}

	@RequestMapping(value = "/importbillservices/uploadfile", method = RequestMethod.POST)
	public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
		System.out.println("In uploadFile method");
		String googleCloudLocation = null;
		String googleCloudpubLocation = null;
		Boolean cloudFlag = true;

		saveToBucket(file);
		
		googleCloudpubLocation = "gs://poc-importbills/" + file.getOriginalFilename();
		googleCloudLocation = "http://poc-importbills.storage.googleapis.com/" + file.getOriginalFilename();
		//Save to the database
	 importBillsService.save(file.getOriginalFilename(), file.getSize(), googleCloudLocation, cloudFlag);
		int id = importBillsService.getId();
	      sendToPubSub(googleCloudpubLocation,id);
		return "success";
	}
	
	public void saveToBucket(MultipartFile file) {
		String UPLOADED_FOLDER = "/tmp";
		if (file.isEmpty()) {
			return;
		}
		try {
			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + "/" + file.getOriginalFilename());
			Files.write(path, bytes);

			File file2 = new File(UPLOADED_FOLDER + "/" + file.getOriginalFilename());
			StorageSample.uploadFile(file.getOriginalFilename(), "image/jpg", file2, "poc-importbills");
			
		} catch (Exception e) {
			
		}
	}
	
	public void sendToPubSub(String googleCloudpubLocation,int id) {
		TopicName topicName = TopicName.create("CG-HSBC-PoC", "importbills");
		Publisher publisher = null;
	String idvalue=	Integer.toString(id);
		ApiFuture<String> messageIdFuture = null;
	ApiFuture<String> pubsubmessageIdFuture = null;
		try {
			ExecutorProvider executorProvider = InstantiatingExecutorProvider.newBuilder().setExecutorThreadCount(1)
					.build();
			publisher = Publisher.defaultBuilder(topicName).setExecutorProvider(executorProvider).build();
			ByteString data = ByteString.copyFromUtf8(googleCloudpubLocation);
			ByteString pubsubmessageid =	ByteString.copyFromUtf8(idvalue);
			PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
			PubsubMessage sendidpub = PubsubMessage.newBuilder().setData(pubsubmessageid).build();
			pubsubmessageIdFuture = publisher.publish(sendidpub);
			messageIdFuture = publisher.publish(pubsubMessage);
			String messageId = messageIdFuture.get();
			System.out.println("published with message ID: " + messageId);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			if (publisher != null) {
				try {
					publisher.shutdown();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
