package com.spring.mykafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WikimediaProducer {

    public final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String message) {
        log.info("Sending message='{}'", message);
        kafkaTemplate.send("wikimedia", message);
    }
}
