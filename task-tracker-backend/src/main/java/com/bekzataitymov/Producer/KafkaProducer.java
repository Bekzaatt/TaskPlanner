package com.bekzataitymov.Producer;

import com.bekzataitymov.Model.Request.MailRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private KafkaTemplate<String, MailRequest> kafkaTemplate;

    public KafkaProducer(KafkaTemplate kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(MailRequest message){
        kafkaTemplate.send("EMAIL_SENDING_TASKS", "email.greeting", message);
    }
}
