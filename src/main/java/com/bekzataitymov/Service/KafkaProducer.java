package com.bekzataitymov.Service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate kafkaTemplate){

        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message){

        kafkaTemplate.send("EMAIL_SENDING_TASKS", message);
    }
}
