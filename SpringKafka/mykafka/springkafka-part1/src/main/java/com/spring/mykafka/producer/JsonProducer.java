package com.spring.mykafka.producer;

import com.spring.mykafka.entities.Student;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static com.spring.mykafka.constants.MyConstants.TOPIC_NAME;

@Service
@RequiredArgsConstructor
public class JsonProducer {

private final KafkaTemplate<String, Student> kafkaTemplate;

public void sendMessage(Student student) {
    Message<Student> message = MessageBuilder.withPayload(student)
            .setHeader(KafkaHeaders.TOPIC,TOPIC_NAME).build();
        kafkaTemplate.send(message);
    }
}
