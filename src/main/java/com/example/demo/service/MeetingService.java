package com.example.demo.service;

import com.example.demo.repository.MeetingRepository;
import com.example.demo.config.TwilioConfig;
import com.example.demo.model.Meeting;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private TwilioConfig.TwilioInitializer twilioInitializer;

    public Meeting createMeeting(Meeting meeting) {
        meeting = meetingRepository.save(meeting);

        String messageContent = "Spotkanie: " + meeting.getTitle() + " odbedzie sie " + meeting.getStartTime();
        Message.creator(
                new PhoneNumber(meeting.getPhoneNumber()),
                new PhoneNumber(twilioInitializer.getFromPhoneNumber()),
                messageContent).create();

        return meeting;
    }
}
