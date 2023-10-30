package com.example.demo.controller.chat;

import com.example.demo.Ws.MessageTemplate;
import com.example.demo.Ws.WebSocketSessions;
import com.example.demo.dao.ChatroomRecordEntity;
import com.example.demo.repository.ChatroomRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class ChatController {

    @Autowired
    private WebSocketSessions sessions;

    @Autowired
    ChatroomRecordRepository chatroomRecordRepository;

    String mxgWhere;

    @MessageMapping("/{dynamicData}")
    @SendTo( "/topic/{dynamicData}")
    public Output send(@PathVariable("dynamicData") String dynamicData, final Message message) throws Exception {
        final String time = new Date().toString();
//        message.setFrom("Anthon");
//        System.out.println(message+"&"+mxgWhere);
        ChatroomRecordEntity record = new ChatroomRecordEntity();
        record.setfrom(message.getFrom());
        record.settext(message.getText());
        record.setatWhere(mxgWhere);//聊天室
        chatroomRecordRepository.save(record);

        return new Output(time, message);
    }
    @PostMapping("/updateRoom")
    @ResponseBody
    public String updatRoom(@RequestParam(value = "where") String where) {
        // 訪問TodoController的studentID
        mxgWhere = where ;
        //ChatroomRecordEntity record = new ChatroomRecordEntity();
        System.out.println(mxgWhere);

        // 返回純文字
        return mxgWhere;
    }

}
