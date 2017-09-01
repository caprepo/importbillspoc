package com.hsbc.documentingestion;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;

@SpringBootApplication
public class PocNaturalLanguageApiApplication {
//Simple Quick Start Example
	public static void main(String[] args) throws IOException {
		SpringApplication.run(PocNaturalLanguageApiApplication.class, args);
		
		/* LanguageServiceClient language = LanguageServiceClient.create();

		  // The text to analyze
		  String text = "Hello, world!";
		  Document doc = Document.newBuilder()
		          .setContent(text).setType(Type.PLAIN_TEXT).build();

		  // Detects the sentiment of the text
		  Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();
		  

		  System.out.printf("Text: %s%n", text);
		  System.out.printf("Sentiment: %s, %s%n", sentiment.getScore(), sentiment.getMagnitude());
*/
	}

	
	
}
