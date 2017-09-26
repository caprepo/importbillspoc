package com.capgemini.importbills.helper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageSource;
import com.google.cloud.vision.v1.Feature.Type;

public class VisionOCRAnalysis {
	private static final String TARGET_URL = "https://vision.googleapis.com/v1/images:annotate?";
	private static final String API_KEY = "key=AIzaSyDzYqDGtLFzXX07z-a-fdYJmHukwRHntRI";

	public String OCRAnalysis(String filename) throws IOException {
		URL serverUrl = new URL(TARGET_URL + API_KEY);
		URLConnection urlConnection = serverUrl.openConnection();
		HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
		httpConnection.setRequestMethod("POST");
		httpConnection.setRequestProperty("Content-Type", "application/json");
		httpConnection.setDoOutput(true);
		BufferedWriter httpRequestBodyWriter = new BufferedWriter(
				new OutputStreamWriter(httpConnection.getOutputStream()));
		httpRequestBodyWriter.write("{\"requests\":  [{ \"features\":  [ {\"type\": \"DOCUMENT_TEXT_DETECTION\""
				+ "}], \"image\": {\"source\": { \"gcsImageUri\":" + " \"gs://poc-importbills/" + filename + "\"}}}]}");
		httpRequestBodyWriter.close();
		String response = httpConnection.getResponseMessage();

		//System.out.println("response------------->" + response);
		if (httpConnection.getInputStream() == null) {
			System.out.println("No stream");
			return null;
		}
		Scanner httpResponseScanner = new Scanner(httpConnection.getInputStream());
		String resp = "";
		int i =0;
		while (httpResponseScanner.hasNext()) {
			i++;
			String line = httpResponseScanner.nextLine();
			resp += line;
			
			if (i % 10000 == 0)
				System.out.print(".");
			//System.out.println(line); // alternatively, print the line of
										// response
		}
		httpResponseScanner.close();
		System.out.println("Response length = " + resp.length());
		return resp;
	}
	public static void detectTextGcs(String gcsPath, PrintStream out) throws Exception, IOException {
		List<AnnotateImageRequest> requests = new ArrayList<AnnotateImageRequest>();

		ImageSource imgSource = ImageSource.newBuilder().setGcsImageUri(gcsPath).build();
		Image img = Image.newBuilder().setSource(imgSource).build();
		Feature feat = Feature.newBuilder().setType(Type.TEXT_DETECTION).build();
		AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
		requests.add(request);

		try {
			ImageAnnotatorClient client = ImageAnnotatorClient.create();
			BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
			List<AnnotateImageResponse> responses = response.getResponsesList();

			for (AnnotateImageResponse res : responses) {
				if (res.hasError()) {
					out.printf("Error: %s\n", res.getError().getMessage());
					return;
				}

				// For full list of available annotations, see
				// http://g.co/cloud/vision/docs
				for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
					out.printf("Text: %s\n", annotation.getDescription());
					out.printf("Position : %s\n", annotation.getBoundingPoly());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
}
