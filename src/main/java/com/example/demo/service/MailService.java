package com.example.chatbot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender sender ;
    public void sendMail(String toPerson,String subject,String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("face02021616@gmail.com");
        message.setTo(toPerson);
        message.setText(body);
        message.setSubject(subject);
        sender.send(message);
        System.out.println("success to send");

    }


}
