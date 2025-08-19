package com.bekzataitymov.Configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic newTopic(){

        return new NewTopic("EMAIL_SENDING_TASKS", 3, (short)1);
    }
}
