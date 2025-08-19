package com.bekzataitymov.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private MailService mailService;

    @Autowired
    public KafkaConsumer(MailService mailService){
        this.mailService = mailService;
    }

    @KafkaListener(topics = "EMAIL_SENDING_TASKS", groupId = "email.greeting")
    public void listen(String message){

    }

}
