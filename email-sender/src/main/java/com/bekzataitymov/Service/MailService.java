package com.bekzataitymov.Service;

import com.bekzataitymov.Model.Request.MailGreetingRequest;
import com.bekzataitymov.Model.Request.MailTrackerRequest;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class MailService {
    @Value("${mailjet.email}")
    private String email;
    @Value("${mailjet.name}")
    private String name;
    @Value("${mailjet.api-key}")
    private String apiKey;
    @Value("${mailjet.secret-key}")
    private String secretKey;

    private final MailjetClient client = new MailjetClient(apiKey, secretKey);


    public void sendGreetingMessage(MailGreetingRequest mailGreetingRequest){
        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", email)
                                        .put("Name", name))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", mailGreetingRequest.getEmail())
                                                .put("Name", name)))
                                .put(Emailv31.Message.SUBJECT, mailGreetingRequest.getHeader())
                                .put(Emailv31.Message.HTMLPART, mailGreetingRequest.getMessage())
                                .put(Emailv31.Message.CUSTOMID, UUID.randomUUID().toString())));
        try {
            client.post(request);
        } catch (MailjetException e) {
            log.info("Error while sending greeting message", e);
        }
        log.info("Greeting message sended successfully");
    }

    public void sendTrackerMessage(MailTrackerRequest message) {
        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", email)
                                        .put("Name", name))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", message.getEmail())
                                                .put("Name", name)))
                                .put(Emailv31.Message.HTMLPART,
                                        message.getFinishedTaskRequest() + "<br><br>" + message.getUnfinishedTaskRequest())
                                .put(Emailv31.Message.CUSTOMID, UUID.randomUUID().toString())));

        try {
            client.post(request);
        } catch (MailjetException e) {
            log.info("Error while sending greeting message", e);
        }
        log.info("Tracker message sended successfully");
    }
}
