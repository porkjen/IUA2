package com.example.demo.controller;
import org.apache.catalina.util.ErrorPageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.example.demo.CourseToBeDetected;
import com.example.demo.DetectedCoursesList;
import com.example.demo.dao.ChangeCourseEntity;
import com.example.demo.dao.ExchangePushCondition;
import com.example.demo.dao.FakeCourseData;
import com.example.demo.dao.NotificationCondition;
import com.example.demo.dao.PushNotificationRequest;
import com.example.demo.dao.PushNotificationResponse;
import com.example.demo.dao.RentPushCondition;
import com.example.demo.dao.WebPushEntity;
import com.example.demo.dao.HouseEntity;
import com.example.demo.repository.ChangeCourseRepository;
import com.example.demo.repository.DetectedRepository;
import com.example.demo.repository.FakeCourseRepository;
import com.example.demo.repository.HouseRepository;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.WebPushRepository;
import com.example.demo.service.PushNotificationService;

import net.sourceforge.tess4j.TesseractException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    ChangeCourseRepository changeCourseRepository;
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    DetectedRepository dRepository;
    @Autowired
    FakeCourseRepository fakeCourseRepository;

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

    @GetMapping("/load_rent_condition")
    public RentPushCondition loadRentCondition(@RequestParam("studentID") String studentID){
        System.out.println("/load_rent_condition");
        RentPushCondition rentCondition = notificationRepository.findByStudentID(studentID).getRentCondition();
        return rentCondition;
    }

    @DeleteMapping("/delete_rent_condition")
    public ResponseEntity<String> deleteRentCondition(@RequestParam("studentID") String studentID){
        System.out.println("/delete_rent_condition");
        NotificationCondition notificationCondition = notificationRepository.findByStudentID(studentID);
        RentPushCondition rentPushCondition = notificationCondition.getRentCondition();
        rentPushCondition = null;
        notificationRepository.findByStudentID(studentID).setRentCondition(rentPushCondition);
        notificationRepository.save(notificationCondition);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/add_detect_course")
    public ResponseEntity<String> addDetectCourse(@RequestBody CourseToBeDetected requestData)throws TesseractException, IOException, InterruptedException{
        ArrayList<CourseToBeDetected> courses = new ArrayList<CourseToBeDetected>();
        DetectedCoursesList courseList = new DetectedCoursesList();
        Boolean isExist = false;
        System.out.println("course number: " + requestData.getNumber());

        if(dRepository.existsByStudentID(requestData.getStudentID())){
            courseList = dRepository.findByStudentID(requestData.getStudentID());
            courses = courseList.getDetectedCourses();
            for(CourseToBeDetected c : courses){
                if(c.getNumber().equals(requestData.getNumber())){
                    isExist = true;
                    break;
                }
            }
        }
        else{
            courseList.setStudentID(requestData.getStudentID());
        }
        if(!isExist){
            courses.add(requestData);
            courseList.setDetectedCourse(courses);
            dRepository.save(courseList);
            return ResponseEntity.ok("Success");
        }
        else{
            System.out.println("Already added.");
            return ResponseEntity.badRequest().body("Invalid request");
        }
    }

    @DeleteMapping("/delete_detect_course")
    public ResponseEntity<String> deleteDetectCourse(@RequestParam("studentID") String studentID, @RequestParam("number") String number){
        System.out.println("/delete_detect_course");
        DetectedCoursesList courseList = dRepository.findByStudentID(studentID);
        ArrayList<CourseToBeDetected> courses = courseList.getDetectedCourses();
        boolean isFound = false;
        for(int i = 0; i < courses.size(); i++){
            if(courses.get(i).getNumber().equals(number)){
                courses.remove(i);
                courseList.setDetectedCourse(courses);
                dRepository.save(courseList);
                isFound = true;
            }
        }
        if(isFound){
            System.out.println("Successfully delete!");
            return ResponseEntity.ok("Success");
        }
        else{
            System.out.println("Not found.");
            return ResponseEntity.badRequest().body("Invalid request");
        }
    }

    @GetMapping("/load_detect_course")
    public ArrayList<CourseToBeDetected> loadDetectCourse(@RequestParam("studentID") String studentID){
        System.out.println("/load_detect_course");
        DetectedCoursesList courseList = dRepository.findByStudentID(studentID);
        return courseList.getDetectedCourses();
    }

    @PostMapping("/new_fake_course")
    public ResponseEntity<String> newFakeCourse(@RequestBody FakeCourseData fakeCourse){
        if(fakeCourseRepository.existsByCourseNumber(fakeCourse.getCourseNumber())){
            System.out.println("Already added.");
            return ResponseEntity.badRequest().body("Invalid request");
        }
        else{
            fakeCourseRepository.save(fakeCourse);
            return ResponseEntity.ok("Succese");
        }
    }

@PostMapping("/detect_course")
    public void detectCourse(@RequestBody Map<String, String> studentID)throws TesseractException, IOException, InterruptedException{
        System.out.println("/detect_course");
        WebPushEntity webPush = webPushRepository.findByStudentID(studentID.get("studentID"));
        DetectedCoursesList courseList = dRepository.findByStudentID(studentID.get("studentID"));
        ArrayList<CourseToBeDetected> courses = courseList.getDetectedCourses();
        PushNotificationRequest request = new PushNotificationRequest();
        while(!courses.isEmpty()){
            Thread.sleep(1500);
            System.out.println("Start detection.");
            for(int i = 0; i < courses.size(); i++){
                if(fakeCourseRepository.existsByCourseNumber(courses.get(i).getNumber())){
                    FakeCourseData fakeCourseData = fakeCourseRepository.findByCourseNumber(courses.get(i).getNumber());
                    if(Integer.parseInt(fakeCourseData.getNowPeople()) < Integer.parseInt(fakeCourseData.getMaxPeople())){
                        System.out.println("Found course.\nRemove [" + courses.get(i).getNumber() + "]");
                        request.setTitle("您想要的課程 [" + courses.get(i).getName() + "] 已釋出名額！");
                        request.setMessage("[" + fakeCourseData.getCourseName() + "]\n目前人數/限制人數: " + fakeCourseData.getNowPeople() + "/" + fakeCourseData.getMaxPeople() + "人");
                        request.setToken(webPush.getToken());
                        pushNotificationService.sendPushNotificationToToken(request);
                        ArrayList<PushNotificationRequest> noticications = webPush.getNotifications();
                        noticications.add(request);
                        webPush.setNotifications(noticications);
                        webPushRepository.save(webPush);
                        courses.remove(i);
                        courseList.setDetectedCourse(courses);
                        dRepository.save(courseList);
                    }
                    else{
                        System.out.println("[" + courses.get(i).getNumber() + "] is full, keep detecting.");
                    }
                }

            }
        }
    }

/* 
    @PostMapping("/detect_course")
    public void detectCourse(@RequestBody Map<String, String> studentID)throws TesseractException, IOException, InterruptedException{
        System.out.println("/detect_course");
        System.out.println("student id is " + studentID.get("studentID"));
        DetectedCoursesList courseList = dRepository.findByStudentID(studentID.get("studentID"));
        ArrayList<CourseToBeDetected> courses = courseList.getDetectedCourses();
        while(!courses.isEmpty()){
            Thread.sleep(1500);
            System.out.println("Start detection.");
            crawler.detectCoureses(courses);
            for(int i = 0; i < courses.size(); i++){
                if(!courses.get(i).getIsFull()){
                    System.out.println("Remove [" + courses.get(i).getNumber() + "]");
                    courses.remove(i);
                    courseList.setDetectedCourse(courses);
                    dRepository.save(courseList);
                }
                else{
                    System.out.println("[" + courses.get(i).getNumber() + "] is full, keep detecting.");
                }
            }
        }
    }
*/

    @PutMapping("/notification_update")
    public ResponseEntity<String> notificationUpdate(@RequestParam("studentID") String studentID){
        WebPushEntity webPush = webPushRepository.findByStudentID(studentID);
        webPush.setUpdate(false);
        webPushRepository.save(webPush);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/exchange_web_push")
    public void exchangeWebPush(@RequestBody Map<String, String> studentID)throws TesseractException, IOException, InterruptedException{
        System.out.println("/exchange_web_push");
        WebPushEntity webPush = webPushRepository.findByStudentID(studentID.get("studentID"));
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
                    request.setToken(webPush.getToken());
                    pushNotificationService.sendPushNotificationToToken(request);
                    ArrayList<PushNotificationRequest> noticications = webPush.getNotifications();
                    noticications.add(request);
                    webPush.setNotifications(noticications);
                    webPushRepository.save(webPush);
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
                                request.setToken(webPush.getToken());
                                //request.setLink("http://localhost:3000/rentArticle");
                                pushNotificationService.sendPushNotificationToToken(request);
                                ArrayList<PushNotificationRequest> noticications = webPush.getNotifications();
                                noticications.add(request);
                                webPush.setNotifications(noticications);
                                webPush.setUpdate(true);
                                webPushRepository.save(webPush);
                                sentPosts.add(cPost.getPostId());
                                break outter;
                            }
                        }
                    }
                }
            }

        }
    }

    @PostMapping("/rent_web_push")
    public void rentWebPush(@RequestBody Map<String, String> studentID)throws TesseractException, IOException, InterruptedException{
        System.out.println("/rent_web_push");
        WebPushEntity webPush = webPushRepository.findByStudentID(studentID.get("studentID"));
        RentPushCondition rentCondition = notificationRepository.findByStudentID(studentID.get("studentID")).getRentCondition();
        ArrayList<String> sentPosts = new ArrayList<String>();
        PushNotificationRequest request = new PushNotificationRequest();
        while(rentCondition != null){
            String money = rentCondition.getRent();
            String gender = rentCondition.getGender();
            String people = rentCondition.getPeople();
            String floor = rentCondition.getFloor();
            String parking = rentCondition.getParking();
            boolean moneyMatch = false;
            boolean genderMatch = false;
            boolean peopleMatch = false;
            boolean floorMatch = false;
            boolean parkingMatch = false;
            boolean powerMatch = false;
            boolean waterMatch = false;
            Thread.sleep(1500);
            for(String style : rentCondition.getStyle()){
                for(String area : rentCondition.getRegion()){
                    List<HouseEntity> hList = houseRepository.findByStyleAndArea(style, area);
                    for(HouseEntity hPost : hList){
                        if(sentPosts.contains(hPost.getPostId()))
                            break;
                        if(hPost.getStatus().equals("未租")){
                            for(String power : rentCondition.getPower()){
                                if(power.equals("一度") && hPost.getPower().startsWith("一")){
                                    String powerMoney = hPost.getPower().replaceAll("[^0-9]", "");
                                    if(Integer.parseInt(powerMoney) <= rentCondition.getPowerMoney())
                                        powerMatch = true;
                                }
                                else if(hPost.getPower().equals(power))
                                    powerMatch = true;
                            }
                            for(String water : rentCondition.getWater()){
                                if(water.equals("月繳") && hPost.getWater().startsWith("月")){
                                    String waterMoney = hPost.getWater().replaceAll("[^0-9]", "");
                                    if(Integer.parseInt(waterMoney) <= rentCondition.getWaterMoney())
                                        waterMatch = true;
                                }
                                else if(hPost.getWater().equals(water))
                                    waterMatch = true;
                            }
                            if(money == null || Integer.parseInt(hPost.getMoney()) <= Integer.parseInt(money))   moneyMatch = true;
                            if(gender != null){
                               if(gender.equals("無限制") || hPost.getGender().equals(gender))
                                    genderMatch = true;
                            }
                            if(people == null || Integer.parseInt(hPost.getPeople()) <= Integer.parseInt(people))  peopleMatch = true;
                            if(floor == null || Integer.parseInt(hPost.getFloor()) <= Integer.parseInt(floor)) floorMatch = true;
                            if(parking == null || hPost.getCar().equals(parking))   parkingMatch = true;
                            if(moneyMatch && genderMatch && peopleMatch && floorMatch && parkingMatch){    
                                System.out.println("[title] " + hPost.getTitle() + "\n[style] " + hPost.getStyle() + "\n[post id] " + hPost.getPostId());
                                request.setTitle("[租屋版]已發現新貼文！");
                                request.setMessage("標題：" + hPost.getTitle());
                                request.setToken(webPush.getToken());
                                //request.setLink("https://www.youtube.com/");
                                pushNotificationService.sendPushNotificationToToken(request);
                                ArrayList<PushNotificationRequest> noticications = webPush.getNotifications();
                                noticications.add(request);
                                webPush.setNotifications(noticications);
                                webPush.setUpdate(true);
                                webPushRepository.save(webPush);
                                sentPosts.add(hPost.getPostId());}
                        }
                    }
                }
            }

        }
    }

    @GetMapping("/get_all_notifications")
    public WebPushEntity getAllNotification(@RequestParam("studentID") String studentID){
        System.out.println("/get_all_notifications");
        WebPushEntity webPushes = new WebPushEntity();
        ArrayList<PushNotificationRequest> result = new ArrayList<PushNotificationRequest>();
        if(webPushRepository.existsByStudentID(studentID)){
            ArrayList<PushNotificationRequest> n = webPushRepository.findByStudentID(studentID).getNotifications();
            webPushes.setStudentID(studentID);
            webPushes.setUpdate(webPushRepository.findByStudentID(studentID).getUpdate());
            webPushes.setToken(webPushRepository.findByStudentID(studentID).getToken());
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
            webPushes.setNotifications(result);
        }
        return webPushes;
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
