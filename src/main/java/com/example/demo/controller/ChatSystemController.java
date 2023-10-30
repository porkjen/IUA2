package com.example.demo.controller;

import com.example.demo.controller.chat.Output;
import com.example.demo.controller.chat.Message;
import com.example.demo.Ws.MessageTemplate;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping(value = "/system", method = RequestMethod.POST)
public class ChatSystemController {

    @Autowired
    private MessageTemplate template;

    @Autowired
    private TodoService todoService;

    @RequestMapping("/broadcast")
    public Output broadcast(@RequestBody Message message) {
        Output outputMessage = new Output(new Date().toString(), message);
        template.broadcast(outputMessage);
        //System.out.println("broadcast");
        return outputMessage;
    }

    @GetMapping("/namecast")//登錄的使用者是誰
    @ResponseBody
    public String namecast() {
        // 訪問TodoController的studentID
        String account = todoService.getAccount();
        System.out.println(account);

        // 返回純文字
        return account;
    }

    @RequestMapping("/send/{user}")
    public Output broadcast(@PathVariable("user") String user, @RequestBody Message message) {
        Output output = new Output(new Date().toString(), message);
        template.sendMsgToUser(user, output);
        //System.out.println("send_user");
        return output;

    }

}
