package com.bekzataitymov.Service;

import com.bekzataitymov.Model.Response.MailResponse;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    public MailResponse sendGreetingMessage(){
        return new MailResponse();
    }
}
