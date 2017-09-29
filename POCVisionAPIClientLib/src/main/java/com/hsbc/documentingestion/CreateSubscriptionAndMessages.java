package com.hsbc.documentingestion;

import java.io.IOException;

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
					Analyze.detectDocumentTextGcs(submessages, System.out);
				} catch (Exception ie) {
					ie.printStackTrace();
				}

				System.out.println("SUCEESSSSSSS");
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
			}, MoreExecutors.directExecutor());
			subscriber.startAsync().awaitRunning();

			// Thread.sleep(60000);
		} finally {
		}
	}
}
