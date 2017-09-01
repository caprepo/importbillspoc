package com.capgemini.importbills.helper;

import java.io.File;
import java.io.IOException;

public class PocCloudStorageApplication {
	private static final String TEST_FILENAME = "Invoice001.gif";

	public static void main(String[] args) {

		try {
			File tempFile = new File("/home/umamagscg/googlecloudpoc/docs/samples/invoice001/Invoice001.gif");
			StorageSample uploadData = new StorageSample();
			uploadData.uploadFile(TEST_FILENAME, "image/gif", tempFile, "poc-importbills");

			VisionOCRAnalysis ocrAnalysis = new VisionOCRAnalysis();
			ocrAnalysis.OCRAnalysis(TEST_FILENAME);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}
}
