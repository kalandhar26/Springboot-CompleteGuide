package com.spring.mykafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.spring.mykafka.constants.MyConstants.TOPIC_NAME;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        log.info("Sending message= '{}' sent to topic= '{}'", message, TOPIC_NAME);
        kafkaTemplate.send( TOPIC_NAME, message);
    }
}
