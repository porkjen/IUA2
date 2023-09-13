package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.ExchangePushCondition;
import com.example.demo.dao.NotificationCondition;
import com.example.demo.dao.PushNotificationRequest;
import com.example.demo.dao.PushNotificationResponse;
import com.example.demo.dao.RentPushCondition;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.service.PushNotificationService;

import java.util.Map;
import java.util.ArrayList;

@RestController
public class PushNotificationController {
    private PushNotificationService pushNotificationService;
    @Autowired
    private NotificationRepository notificationRepository;
    
    public PushNotificationController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @PostMapping("/exchange_notification_add")
    public ResponseEntity<String> addExchangePushCondition(@RequestBody ExchangePushCondition received){
        System.out.println("/exchange_notification_add");
        NotificationCondition condition = new NotificationCondition();
        if(notificationRepository.existsByStudentID(received.getStudentID())){
            condition = notificationRepository.findByStudentID(received.getStudentID());
            condition.getExchangeCondition().add(received);
        }
        else{
            condition = new NotificationCondition(received.getStudentID());
            ArrayList<ExchangePushCondition> exList = new ArrayList<ExchangePushCondition>();
            exList.add(received);
            condition.setExchangeCondition(exList);
        }
        notificationRepository.save(condition);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/rent_notification_add")
    public ResponseEntity<String> addRentPushCondition(@RequestBody RentPushCondition received){
        System.out.println("/rent_notification_add");
        NotificationCondition condition = new NotificationCondition();
        if(notificationRepository.existsByStudentID(received.getStudentID())){
            condition = notificationRepository.findByStudentID(received.getStudentID());
        }
        else{
            condition = new NotificationCondition(received.getStudentID());
        }
        condition.setRentCondition(received);
        notificationRepository.save(condition);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/notification/topic")
    public ResponseEntity<?> sendNotification(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendPushNotificationWithoutData(request);
        System.out.println("/notification/topic");
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }
    
    @PostMapping("/notification/token")
    public ResponseEntity<?> sendTokenNotification(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendPushNotificationToToken(request);
        System.out.println("/notification/token");
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }
    
}