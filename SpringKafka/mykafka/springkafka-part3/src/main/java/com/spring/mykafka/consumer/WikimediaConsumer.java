package com.spring.mykafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WikimediaConsumer {

    @KafkaListener(topics = "wikimedia",groupId = "MyGroup")
    public void consumeMessage(String message) {
        log.info(String.format("Message consumed successfully from wikimedia:: %s",message));

        // We can do some business logic here with data we got from wikimedia
    }
}
