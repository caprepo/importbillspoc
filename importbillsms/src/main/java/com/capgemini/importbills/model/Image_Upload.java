package com.capgemini.importbills.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component


@Entity
@Table(name = "image_upload")
public class Image_Upload implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "image_id")
	public int ImageId;
	
	@Column(name = "image_name")
   public String ImageName;
	
	@Column(name="image_size")
	public long ImageSize;
	
	@Column(name = "image_stored_flag")
	public Boolean ImageStoredFlag;
	
	@Column(name="vision_api_resp")
	public String VisionApiResp;
	
	@Column(name = "image_google_storage_loc")
	public String ImageGoogleStorageLoc;

	

	public int getImageId() {
		return ImageId;
	}

	public void setImageId(int imageId) {
		ImageId = imageId;
	}

	public String getImageName() {
		return ImageName;
	}

	public void setImageName(String imageName) {
		ImageName = imageName;
	}

	public long getImageSize() {
		return ImageSize;
	}

	public void setImageSize(long imageSize) {
		ImageSize = imageSize;
	}

	public Boolean getImageStoredFlag() {
		return ImageStoredFlag;
	}

	public void setImageStoredFlag(Boolean imageStoredFlag) {
		ImageStoredFlag = imageStoredFlag;
	}

	public String getVisionApiResp() {
		return VisionApiResp;
	}

	public void setVisionApiResp(String visionApiResp) {
		VisionApiResp = visionApiResp;
	}

	public String getImageGoogleStorageLoc() {
		return ImageGoogleStorageLoc;
	}

	public void setImageGoogleStorageLoc(String imageGoogleStorageLoc) {
		ImageGoogleStorageLoc = imageGoogleStorageLoc;
	}
	
	
	
	
}
