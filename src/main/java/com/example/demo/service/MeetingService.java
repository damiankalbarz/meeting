package com.example.demo.service;

import com.example.demo.repository.MeetingRepository;
import com.example.demo.config.TwilioConfig;
import com.example.demo.model.Meeting;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Meeting updateMeeting(Long id, Meeting meetingDetails) {
        Optional<Meeting> optionalMeeting = meetingRepository.findById(id);
        if (optionalMeeting.isPresent()) {
            Meeting meeting = optionalMeeting.get();
            meeting.setTitle(meetingDetails.getTitle());
            meeting.setDescription(meetingDetails.getDescription());
            meeting.setStartTime(meetingDetails.getStartTime());
            meeting.setEndTime(meetingDetails.getEndTime());
            meeting.setPhoneNumber(meetingDetails.getPhoneNumber());

            meeting = meetingRepository.save(meeting);


            String messageContent = "Nastapiła zmianna w spotkaniu: " + meeting.getTitle() + " o " + meeting.getStartTime();
            Message.creator(
                    new PhoneNumber(meeting.getPhoneNumber()),
                    new PhoneNumber(twilioInitializer.getFromPhoneNumber()),
                    messageContent).create();

            return meeting;
        }
        return null;
    }

    public void deleteMeeting(Long id) {
        Optional<Meeting> optionalMeeting = meetingRepository.findById(id);
        if (optionalMeeting.isPresent()) {
            Meeting meeting = optionalMeeting.get();
            meetingRepository.delete(meeting);

            String messageContent = "Spotkanie zostało odwołane: " + meeting.getTitle() + " o " + meeting.getStartTime();
            Message.creator(
                    new PhoneNumber(meeting.getPhoneNumber()),
                    new PhoneNumber(twilioInitializer.getFromPhoneNumber()),
                    messageContent).create();
        }
    }
}
