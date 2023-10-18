package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;


import com.example.demo.dao.ChangeCourseEntity;
import com.example.demo.dao.ExchangePushCondition;
import com.example.demo.dao.NotificationCondition;
import com.example.demo.dao.PushNotificationRequest;
import com.example.demo.dao.PushNotificationResponse;
import com.example.demo.dao.RentPushCondition;
import com.example.demo.repository.ChangeCourseRepository;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.WebPushRepository;
import com.example.demo.service.PushNotificationService;

import net.sourceforge.tess4j.TesseractException;

import java.util.Map;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PushNotificationController {
    private PushNotificationService pushNotificationService;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private WebPushRepository webPushRepository;

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

    @PostMapping("/exchange_web_push")
    public void rentWebPush(@RequestBody Map<String, String> studentID)throws TesseractException, IOException, InterruptedException{
        System.out.println("/exchange_web_push");
        ArrayList<ExchangePushCondition> exchangeCondition = notificationRepository.findByStudentID(studentID.get("studentID")).getExchangeCondition();
        ArrayList<String> desired = notificationRepository.findByStudentID(studentID.get("studentID")).getDesireClasses();
        List<ChangeCourseEntity> cList = changeCourseRepository.findAll();
        ArrayList<String> sentPosts = new ArrayList<String>();
        PushNotificationRequest request = new PushNotificationRequest();
        while(!exchangeCondition.isEmpty()){
            Thread.sleep(1500);
            for(ChangeCourseEntity cPost : cList){
                if(sentPosts.contains(cPost.getPostId()))
                    break;
                if(desired.contains(cPost.getCourse()) && !cPost.getStatus().equals("已換")){
                    System.out.println("[course name] " + cPost.getCourse() + "\n[post id] " + cPost.getPostId());
                    request.setTitle("您想換的課程[" + cPost.getCourse() + "] 已有新貼文！");
                    request.setMessage("發布者 " + cPost.getNickname() + "已發布貼文\n[" + cPost.getCourse() + "] " + cPost.getContent());
                    request.setToken(webPushRepository.findByStudentID(studentID.get("studentID")).getToken());
                    pushNotificationService.sendPushNotificationToToken(request);
                    sentPosts.add(cPost.getPostId());
                    break;
                }
                for(ExchangePushCondition condition : exchangeCondition){
                    if(cPost.getStatus().equals("已換"))
                        break;
                    String[] postTime = cPost.getTime();
                    String[] conTime = condition.getTime();
                    if(conTime == null)
                        break;
                    outter:
                    for(String p : postTime){
                        for(String c : conTime){
                            if(p.equals(c)){
                                System.out.println("[course time] " + c + "\n[post id] " + cPost.getPostId());
                                request.setTitle("您想換的課程[" + cPost.getCourse() + "] 已有新貼文！");
                                request.setMessage("發布者 " + cPost.getNickname() + "已發布貼文\n[" + cPost.getCourse() + "] " + cPost.getContent());
                                request.setToken(webPushRepository.findByStudentID(studentID.get("studentID")).getToken());
                                pushNotificationService.sendPushNotificationToToken(request);
                                sentPosts.add(cPost.getPostId());
                                break outter;
                            }
                        }
                    }
                }
            }

        }
    }

    @GetMapping("/get_all_notifications")
    public ArrayList<PushNotificationRequest> getAllNotification(@RequestParam("studentID") String studentID){
        System.out.println("/get_all_notifications");
        ArrayList<PushNotificationRequest> result = new ArrayList<PushNotificationRequest>();
        if(webPushRepository.existsByStudentID(studentID)){
            ArrayList<PushNotificationRequest> n = webPushRepository.findByStudentID(studentID).getNotifications();
            int count = webPushRepository.findByStudentID(studentID).getNotifications().size();
            System.out.println(count);
            if(count > 10){
                for(int i = count-1; i >= 10; i--){
                System.out.println(i);
                result.add(n.get(i));
            }
            }
            else{
                for(int i = count-1; i >= 0; i--){
                    System.out.println(i);
                    result.add(n.get(i));
                }
            }

        }
        return result;
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
