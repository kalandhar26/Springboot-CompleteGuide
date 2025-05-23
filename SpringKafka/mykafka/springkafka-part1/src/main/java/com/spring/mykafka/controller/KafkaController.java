package com.spring.mykafka.controller;

import com.spring.mykafka.entities.Student;
import com.spring.mykafka.producer.JsonProducer;
import com.spring.mykafka.producer.KafkaProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@Tag(name = "Sample API", description = "Sample endpoints")
public class KafkaController {

    private final KafkaProducer kafkaProducer;

    private final JsonProducer jsonProducer;

    @PostMapping
    @Operation(summary = "Get greeting", description = "Returns a greeting message")
    public ResponseEntity<String> sendMessage( @RequestBody String message) {
        kafkaProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent successfully");
    }

    @PostMapping("/json")
    @Operation(summary = "Greeting", description = "Returns a greetings message")
    public ResponseEntity<String> sendJsonMessage( @RequestBody Student message) {
        jsonProducer.sendMessage(message);
        return ResponseEntity.ok("Json Message sent successfully");
    }
}
