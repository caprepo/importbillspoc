package com.capgemini.importbills.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.importbills.model.Image_Upload;
import com.capgemini.importbills.model.Invoice;

@Repository
@Transactional
public interface ImportBillsDao<I extends Image_Upload> extends JpaRepository<Invoice, Long> {

	@Query("SELECT p FROM Invoice p")
	public List<Invoice> findInvoices();
	@Modifying
   @Query(value = "insert into Image_Upload (image_name,image_size,image_google_storage_loc,image_stored_flag) VALUES (:originalFilename,:size,:googleCloudLocation,:cloudFlag)", nativeQuery = true)
	@Transactional
	void save(@Param("originalFilename") String originalFilename,@Param("size") long size,@Param("googleCloudLocation") String googleCloudLocation,@Param("cloudFlag") Boolean cloudFlag);
	
	@Query("SELECT I FROM Image_Upload I")
	public List<Image_Upload> getAllImages();
	
	@Query("SELECT Im.ImageId FROM Image_Upload Im where Im.ImageId = (select max(ImageId) from Image_Upload)")
	public int getId();
	
   
}
