package com.spring.mykafka.controller;

import com.spring.mykafka.entities.Student;
import com.spring.mykafka.producer.JsonProducer;
import com.spring.mykafka.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaProducer kafkaProducer;

    private final JsonProducer jsonProducer;

    @PostMapping
    public ResponseEntity<String> sendMessage( @RequestBody String message) {
        return ResponseEntity.ok("Message sent successfully");
    }

    @PostMapping("/json")
    public ResponseEntity<String> sendJsonMessage( @RequestBody Student message) {
        return ResponseEntity.ok("Json Message sent successfully");
    }
}
