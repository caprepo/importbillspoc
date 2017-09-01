package com.capgemini.importbills.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.model.ObjectAccessControl;
import com.google.api.services.storage.model.StorageObject;

/**
 * Main class for the Cloud Storage JSON API sample.
 *
 * Demonstrates how to make an authenticated API call using the Google Cloud
 * Storage API client library for java, with Application Default Credentials.
 */
public class StorageSample {
	public static void uploadFile(String name, String contentType, File file, String bucketName)
			throws IOException, GeneralSecurityException {
		InputStreamContent contentStream = new InputStreamContent(contentType, new FileInputStream(file));
		// Setting the length improves upload performance
		contentStream.setLength(file.length());
		StorageObject objectMetadata = new StorageObject()
				// Set the destination object name
				.setName(name)
				// Set the access control list to publicly read-only
				.setAcl(Arrays.asList(new ObjectAccessControl().setEntity("allUsers").setRole("READER")));

		// Do the insert
		Storage client = StorageFactory.getService();
		Storage.Objects.Insert insertRequest = client.objects().insert(bucketName, objectMetadata, contentStream);

		insertRequest.execute();
	}
	// [END upload_stream]
}
