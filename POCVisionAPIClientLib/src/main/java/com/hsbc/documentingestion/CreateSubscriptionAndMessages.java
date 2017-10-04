package com.hsbc.documentingestion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.google.api.gax.core.ExecutorProvider;
import com.google.api.gax.core.InstantiatingExecutorProvider;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.SubscriptionName;
import com.google.pubsub.v1.TopicName;

public class CreateSubscriptionAndMessages {

	
	public static void publishMessages() throws InterruptedException {

		TopicName topic = TopicName.create("CG-HSBC-PoC", "importbills");
		SubscriptionName subscription = SubscriptionName.create("CG-HSBC-PoC", "importbills");
		MessageReceiver receiver = new MessageReceiver() {

			@Override
			public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
				System.out.println("Received message: " + message.getData().toStringUtf8());
				String submessages = message.getData().toStringUtf8();
		
				try {
					String fulltext = Analyze.detectDocumentTextGcs(submessages, System.out);
					Class.forName("org.postgresql.Driver");
					Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db_importbills","postgres", "admin");
					PreparedStatement st;
					ResultSet rs;
					st = conn.prepareStatement("select image_id from Image_Upload order by image_id desc limit 1");
					 rs = st.executeQuery();
					while(rs.next()){
						System.out.println("SUBMESSAGE"+submessages);
						String IdvalueFromPubsub = submessages;
						String DataValue = rs.getString(1);
					if(IdvalueFromPubsub.contains(DataValue)) {
							
					}
					else {
						int tablevalueforpubsub = Integer.valueOf(DataValue);
						st = conn.prepareStatement("Update Image_Upload set vision_api_resp = ? where image_id = ?");
						st.setString(1, fulltext);
						st.setInt(2,tablevalueforpubsub);
						System.out.println("QUERRYYY"+st);
						st.execute();
						System.out.println("SUCEESSSSSSS");
							
						}
						conn.close();
				} 
				}catch (Exception ie) {
					ie.printStackTrace();
				}

				consumer.ack();
			}
		};
		Subscriber subscriber = null;
		ExecutorProvider executorProvider = InstantiatingExecutorProvider.newBuilder().setExecutorThreadCount(1)
				.build();
		try {
			subscriber = Subscriber.defaultBuilder(subscription, receiver).setExecutorProvider(executorProvider)
					.build();
			subscriber.addListener(new Subscriber.Listener() {
				@Override
				public void failed(Subscriber.State from, Throwable failure) {
					// Handle failure. This is called when the Subscriber
					// encountered a fatal error and is shutting down.
					System.err.println("FAILURE" + failure);
				}
			}, 
					MoreExecutors.directExecutor());
			subscriber.startAsync().awaitRunning();
			// Thread.sleep(60000);
		} finally {
		}
	}
}
