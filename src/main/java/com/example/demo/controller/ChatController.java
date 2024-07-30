package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.model.OutputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ChatController {

    @MessageMapping("/send/message")
    @SendTo("/topic/messages")
    public OutputMessage sendMessage(Message message) {
        return new OutputMessage(
                message.getFrom(),
                message.getText(),
                new SimpleDateFormat("HH:mm").format(new Date())
        );
    }
}