package com.spring.mykafka.controller;

import com.spring.mykafka.service.KafkaMessagePublisher;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
@Tag(name = "Sample API", description = "Sample endpoints")
public class EventController {

    private final KafkaMessagePublisher kafkaMessagePublisher;

    public EventController(KafkaMessagePublisher kafkaMessagePublisher) {
        this.kafkaMessagePublisher = kafkaMessagePublisher;
    }

    @GetMapping("/publish/{message}")
    @Operation(summary = "send Events", description = "Events are sent successfully")
    public ResponseEntity<?> publishEvent( @PathVariable String message){
        try {
            for(int i=0;i<10000;i++) {
                kafkaMessagePublisher.publishMessageToTopic(message+" "+i);
            }
            return ResponseEntity.ok("Message sent successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
