package com.bekzataitymov.Consumer;

import com.bekzataitymov.Model.Request.MailGreetingRequest;
import com.bekzataitymov.Model.Request.MailTrackerRequest;
import com.bekzataitymov.Service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {
    private final MailService mailService;

    public KafkaConsumer(MailService mailService){
        this.mailService = mailService;
    }

    @KafkaListener(topics = "EMAIL_SENDING_TASKS", groupId = "email.greeting")
    public void listenGreetingMessage(MailGreetingRequest message){
        log.info("Email-greeting-sender method");
        mailService.sendGreetingMessage(message);
    }

    @KafkaListener(topics = "EMAIL_SENDING_TASKS", groupId = "email.tracker")
    public void listenMailTracker(MailTrackerRequest message){
        log.info("Email-tracker-sender method");
        mailService.sendTrackerMessage(message);
    }
}
