package com.example.demo.config;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;

    @Bean
    public TwilioInitializer twilioInitializer() {
        Twilio.init(accountSid, authToken);
        return new TwilioInitializer(fromPhoneNumber);
    }

    public static class TwilioInitializer {
        private final String fromPhoneNumber;

        public TwilioInitializer(String fromPhoneNumber) {
            this.fromPhoneNumber = fromPhoneNumber;
        }

        public String getFromPhoneNumber() {
            return fromPhoneNumber;
        }
    }
}
