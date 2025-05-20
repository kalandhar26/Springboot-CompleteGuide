package com.spring.mykafka.consumer;

import com.spring.mykafka.entities.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.spring.mykafka.constants.MyConstants.TOPIC_NAME;

@Service
@Slf4j
public class JsonConsumer {

    @KafkaListener(topics = TOPIC_NAME,groupId = "MyGroup")
    public void consumeJsonMessage(Student student) {
        log.info(String.format("Message consumed successfully from Topic:: %s",student.toString()));
    }
}
