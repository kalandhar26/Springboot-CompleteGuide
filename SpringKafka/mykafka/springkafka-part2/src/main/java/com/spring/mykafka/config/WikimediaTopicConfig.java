package com.spring.mykafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WikimediaTopicConfig {

    @Bean
    public NewTopic wikimediaTopic() {
        return new NewTopic("wikimedia", 1, (short) 1);
    }
}
