package com.spring.mykafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.spring.mykafka.constants.MyConstants.TOPIC_NAME;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = TOPIC_NAME,groupId = "MyGroup")
    public void consumeMessage(String message) {
        log.info(String.format("Message consumed successfully from Topic:: %s",message));
    }
}
