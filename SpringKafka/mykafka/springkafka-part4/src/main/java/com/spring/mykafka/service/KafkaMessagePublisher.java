package com.spring.mykafka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaMessagePublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishMessageToTopic(String message) {
        CompletableFuture<SendResult<String, String>> myFuture = kafkaTemplate.send("myTopic", message);
        /*
         * send method returns a CompletableFuture<SendResult<K, V>>
         *If we want to block the sending thread until the message is sent, we can use the get() method of the CompletableFuture object.
         * Then the thread will wait until the results come, This will slow down the sending process and producer.
         * As we know kafka is a fast and processing platform. It is better to handle results asynchronously, so that the subsequent message will not wait for the results of the previous messages.
         * We can use the thenAccept() method of the CompletableFuture object to handle the results asynchronously.
         * we can also use the thenApply() method to handle the results asynchronously.
         * we can use callback methods to handle the results asynchronously.
         */
        myFuture.whenComplete((result, exception) -> {
            if(exception == null){
                System.out.println("Sent Message = ["+ message +"] with offset=["+ result.getRecordMetadata().offset() +"]");
            }else{
                System.out.println("Unable to send message=["+ message +"] due to :"+exception.getMessage());
            }
        });

    }
}
