package com.example.demo.controller.chat;

import com.example.demo.Ws.MessageTemplate;
import com.example.demo.Ws.WebSocketSessions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class ChatController {

    @Autowired
    private WebSocketSessions sessions;

    @MessageMapping("/{dynamicData}")
    @SendTo( "/topic/{dynamicData}")
    public Output send(@PathVariable("dynamicData") String dynamicData, final Message message) throws Exception {
        final String time = new Date().toString();
        return new Output(time, message);
    }

}
