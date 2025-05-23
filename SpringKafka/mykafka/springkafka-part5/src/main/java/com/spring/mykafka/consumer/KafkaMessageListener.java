package com.spring.mykafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaMessageListener {

    @KafkaListener(topics = "myTopic", groupId = "Group1")
    public void listen1(String message) {
        log.info("Received Message 1 = ["+ message +"]");
    }


    @KafkaListener(topics = "myTopic", groupId = "Group1")
    public void listen2(String message) {
        log.info("Received Message 2 = ["+ message +"]");
    }


    @KafkaListener(topics = "myTopic", groupId = "Group1")
    public void listen3(String message) {
        log.info("Received Message 3 = ["+ message +"]");
    }



    @KafkaListener(topics = "myTopic", groupId = "Group1")
    public void listen4(String message) {
        log.info("Received Message 4 = ["+ message +"]");
    }

}
