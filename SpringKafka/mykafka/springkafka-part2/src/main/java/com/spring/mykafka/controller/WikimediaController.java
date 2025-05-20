package com.spring.mykafka.controller;

import com.spring.mykafka.consumer.WikimediaStreamConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wikimedia")
@Slf4j
@RequiredArgsConstructor
public class WikimediaController {

    private final WikimediaStreamConsumer wikimediaStreamConsumer;

    @GetMapping("/stream")
    public void startPublishing() {
        wikimediaStreamConsumer.consumerStreamAndPublish();
    }
}
