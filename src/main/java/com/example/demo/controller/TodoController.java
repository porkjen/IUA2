package com.example.demo.controller;

import com.example.demo.*;

import com.example.demo.dao.*;

import com.example.demo.service.*;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class TodoController {
    @Autowired
    TodoService todoService;//取得Service物件
    @Autowired
    RemainedService remainedService;
    @Autowired
    FinishedRepository fRepository;
    @Autowired
    BasicRepository basicRepository;
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    TimeTableRepository timeTableRepository;
    @Autowired
    SavedRepository savedRepository;
    @Autowired
    FoodRepository foodRepository;
    @Autowired
    RCourseG1MustRepository rcourseG1MustRepository;
    @Autowired
    RCourseG1SelectRepository rcourseG1SelectRepository;
    @Autowired
    RCourseG2MustRepository rcourseG2MustRepository;
    @Autowired
    RCourseG2SelectRepository rcourseG2SelectRepository;
    @Autowired
    RCourseG3MustRepository rcourseG3MustRepository;
    @Autowired
    RCourseG3SelectRepository rcourseG3SelectRepository;
    @Autowired
    RCourseG4MustRepository rcourseG4MustRepository;
    @Autowired
    RCourseG4SelectRepository rcourseG4SelectRepository;

    String secretKey = "au4a83";

    Crawler crawler = new Crawler();
    AESEncryptionDecryption aesEncryptionDecryption = new AESEncryptionDecryption();

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody BasicEntity basic)throws TesseractException, IOException, InterruptedException  {
        System.out.println("/login");
        //password encrypt
        String studentID = basic.getStudentID();
        String password = basic.getPassword();
        System.out.println(studentID);
        String encryptedpwd = aesEncryptionDecryption.encrypt(password, secretKey);
        String decryptedpwd = aesEncryptionDecryption.decrypt(encryptedpwd, secretKey);
        //account is not in database
        if(basicRepository.findByStudentID(studentID)==null){
            crawler.CrawlerHandle(studentID,password);
            System.out.println("login message " +Crawler.loginMessage);
            if (Objects.equals(crawler.loginMessage, "帳號或密碼錯誤")){
                return ResponseEntity.badRequest().body("Invalid request"); // 回傳狀態碼 400
            }
            basic = crawler.getBasicData(studentID,password);
            basic.setPassword(encryptedpwd);
            basic.setEmail(studentID + "@mail.ntou.edu.tw");
            basicRepository.save(basic);
            System.out.println("New user!");

            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
        }
        else {
            if(!Objects.equals(basicRepository.findByStudentID(studentID).getPassword(), encryptedpwd)){
                System.out.println("password error");
                return ResponseEntity.badRequest().body("Invalid request"); // 回傳狀態碼 400
            }
            basicRepository.findByStudentID(studentID).setPassword(encryptedpwd); //user may change password, update password everytime
            System.out.println("This account has login before!");
        }
        System.out.println("加密:"+encryptedpwd);
        System.out.println("original:"+decryptedpwd);

        return ResponseEntity.ok("Success"); // 回傳狀態碼 200

    }

    @PostMapping("/nickname")
    public void postnickname (@RequestBody BasicEntity basic)throws TesseractException, IOException, InterruptedException  {
        System.out.println(basic.getStudentID());
        BasicEntity oldProduct = basicRepository.findByStudentID(basic.getStudentID());
        oldProduct.setNickname(basic.getNickname());
        basicRepository.save(oldProduct);
    }

    @PostMapping("/remained_credits")
    public RemainCredit postRemainCredits (@RequestBody FinishedCourseList finished)throws TesseractException, IOException, InterruptedException{
        ArrayList<FinishedCourse> finishedCourse = new ArrayList<FinishedCourse>();
        System.out.println("*********student ID: " + finished.getStudentID());
        if(fRepository.existsByStudentID(finished.getStudentID())){
            System.out.println("found.");
            FinishedCourseList oriList = fRepository.findByStudentID(finished.getStudentID());
            ArrayList<FinishedCourse> oriCourses =  oriList.getFinishedCourses();
            String sem = oriCourses.get(oriCourses.size() - 1).getSemester();

            finishedCourse = crawler.getFinishedCredict(oriCourses, sem);
            oriList.setFinishedCourses(finishedCourse);
            fRepository.save(oriList);
        }
        else{
            finishedCourse = crawler.getFinishedCredict(finishedCourse, "");
            finished.setFinishedCourses(finishedCourse);
            fRepository.save(finished);
        }

        RemainCredit remainCredit = remainedService.computeCredit(finished.getStudentID());
        return remainCredit;
    }

    @PostMapping("/add_detect_course")
    public void addDetectCourse()throws TesseractException, IOException, InterruptedException{
        crawler.detectCoureses();
    }
    
    @PostMapping("/detect_course")
    public void detectCourse()throws TesseractException, IOException, InterruptedException{

    }


    @PostMapping("/rent_post")
    public HouseEntity rentPost(@RequestBody HouseEntity house){
        System.out.println("/rent_post");
        String dateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd")//date today
                .format(LocalDateTime.now());
        house.setPost_time(dateTime);
        String post_id; //get new post_id
        NextPostId nextPostId = new NextPostId();
        if(houseRepository.findFirstByOrderByIdDesc()==null){post_id = "H00001";}
        else{
            post_id = nextPostId.getNextHouseString(houseRepository.findFirstByOrderByIdDesc().getPostId());
        }
        house.setPostId(post_id);
        house.setName(basicRepository.findByStudentID(house.getStudentID()).getName());//real name
        houseRepository.save(house);
        return house;
    }

    @GetMapping("/rent_load") //list all house posts
    public List<HouseDTO> rentLoad(){
        List<HouseEntity> housePostList = houseRepository.findAll();
        List<HouseDTO> SimpleHousePostList = new ArrayList<>();
        for (HouseEntity post : housePostList) {
            HouseDTO dto = new HouseDTO(post.getPostId(), post.getName(), post.getTitle(), post.getPost_time());
            SimpleHousePostList.add(dto);
        }
        return SimpleHousePostList;
    }

    @PostMapping("/rent_full_post") //get entire post
    public HouseEntity rentFullPost(@RequestBody HouseEntity houseEntity){
        System.out.println("/rent_full_post");
        HouseEntity houseEntity1 = houseRepository.findByPostId(houseEntity.getPostId());
        //no one save this post
        if(houseEntity1.getSaved().size()==0){
            houseEntity1.savefirst("false");
            return houseEntity1;
        }
        for(String user : houseEntity1.getSaved()){
            //user saved this post
            if(Objects.equals(user, houseEntity.getStudentID())) {
                houseEntity1.savefirst("true");
                return houseEntity1;
            }
        }
        //user doesn't save this post
        houseEntity1.savefirst("false");
        return houseEntity1;
    }

    @DeleteMapping("/rent_post_delete")
    public ResponseEntity<String> rentPostDelete(@RequestParam("studentID") String studentID, @RequestParam("postId") String postId){
        if(houseRepository.deleteByPostId(postId) !=null){
            //200
            return ResponseEntity.ok("Success");
        }
        else return ResponseEntity.badRequest().body("Invalid request"); // 400

    }

    @PutMapping("/rent_post_modify")
    public ResponseEntity<String> rentPostModify(@RequestBody HouseEntity houseEntity){
        HouseEntity thisHouse = houseRepository.findByPostId(houseEntity.getPostId());
        houseEntity.setId(thisHouse.getId());
        houseEntity.setPost_time(thisHouse.getPost_time());
        houseEntity.setName(thisHouse.getName());
        if(thisHouse.getSaved().size()!=0){
            for(String save : thisHouse.getSaved())houseEntity.setSaved(save);
        }
        houseRepository.save(houseEntity);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/rent_search")
    public List<HouseDTO> rentSearch(@RequestParam(value = "area", required = false) String area,
                                     @RequestParam(value = "gender", required = false) String gender,
                                     @RequestParam(value = "people", required = false) String people,
                                     @RequestParam(value = "style", required = false) String style,
                                     @RequestParam(value = "car", required = false) String car){
        System.out.println("/rent_search");
        List<HouseDTO> resultList = new ArrayList<>();
        for(HouseEntity house : houseRepository.findAll()){
            if ((Objects.equals(area, "") || house.getArea().equals(area))
                    && (Objects.equals(gender, "") || house.getGender().equals(gender))
                    && (Objects.equals(people, "") || house.getPeople().equals(people))
                    && (Objects.equals(style, "") || house.getStyle().contains(style))
                    && (Objects.equals(car, "") || house.getCar().equals(car))) {
                HouseDTO result = new HouseDTO(house.getPostId(), house.getName(), house.getTitle(), house.getPost_time());
                resultList.add(result);
            }
        }
        return resultList;
    }

    @GetMapping("/curriculum_search")
    public List<TimeTableEntity.Info> curriculumSearch(@RequestParam("studentID") String studentID) throws TesseractException, IOException, InterruptedException {
        TimeTableEntity timeTable = timeTableRepository.findByStudentID(studentID);
        if(timeTable!=null && timeTable.getInfo().size()!=0){
            return timeTable.getInfo();
        }
        else{
            String password = basicRepository.findByStudentID(studentID).getPassword();
            password = aesEncryptionDecryption.decrypt(password, secretKey);
            crawler.CrawlerHandle(studentID,password);
            List<TimeTableEntity.Info> myClassList = crawler.getMyClass(studentID,password);
            TimeTableEntity table = new TimeTableEntity();
            if (timeTable != null) { //copy to new
                table.setId(timeTable.getId());
                table.setStudentID(timeTable.getStudentID());
                table.setWholePre_info(timeTable.getPre_info());
            }
            else table.setStudentID(studentID); //create a new one
            for(TimeTableEntity.Info i : myClassList){
                System.out.println(i.getName());
                table.setInfo(i);
            }
            timeTableRepository.save(table);
            return myClassList;
        }
    }


    @PostMapping("/course_search")
    public List<RequiredCourseEntity> course_search(@RequestParam(value = "category") String category,@RequestParam(value = "grade") String grade)throws TesseractException, IOException, InterruptedException  {

        System.out.println("/course_search");
        String studentID ="00957039";
        String password = "kl29nhpo";

        List<RequiredCourseEntity> RC_result = new ArrayList<>();;
        if(grade.equals("大一")) {
            System.out.println("大一");
            if(category.equals("必修")){
                System.out.println("必修");
                List<RequiredCourseEntityG1must> rCourseEntityG1must = rcourseG1MustRepository.findByc_category(category);
                if (rCourseEntityG1must != null && !rCourseEntityG1must.isEmpty()){
                    System.out.println("find it");
                    for (RequiredCourseEntityG1must G1must : rCourseEntityG1must) {
                        RequiredCourseEntity result = new RequiredCourseEntity();
                        result.setCNumber(G1must.getCNumber());
                        result.setCTeacher(G1must.getCTeacher());
                        result.setCCredit(G1must.getCCredit());
                        result.setCGrade(G1must.getCGrade());
                        result.setCName(G1must.getCName());
                        result.setCCategory(G1must.getCCategory());
                        RC_result.add(result);
                    }
                }
                else{
                    crawler.CrawlerHandle(studentID,password);

                    RC_result = crawler.findRCourse(category,grade);

                    for (RequiredCourseEntity course : RC_result) {
                        System.out.println("課號: " + course.getCNumber());
                        System.out.println("老師: " + course.getCTeacher());
                        System.out.println("學分: " + course.getCCredit());
                        System.out.println("年級: " + course.getCGrade());
                        System.out.println("課名: " + course.getCName());
                        System.out.println("課別: " + course.getCCategory());
                        RequiredCourseEntityG1must RCourseEntityG1must = new RequiredCourseEntityG1must();
                        RCourseEntityG1must.setCNumber(course.getCNumber());
                        RCourseEntityG1must.setCTeacher(course.getCTeacher());
                        RCourseEntityG1must.setCCredit(course.getCCredit());
                        RCourseEntityG1must.setCGrade(course.getCGrade());
                        RCourseEntityG1must.setCName(course.getCName());
                        RCourseEntityG1must.setCCategory(course.getCCategory());

                        rcourseG1MustRepository.save(RCourseEntityG1must);
                    }
                    System.out.println(RC_result.size());
                }
            }
            else if(category.equals("選修")){
                System.out.println("選修");
                List<RequiredCourseEntityG1select> rCourseEntityG1select = rcourseG1SelectRepository.findByc_category(category);
                if (rCourseEntityG1select != null && !rCourseEntityG1select.isEmpty()){
                    System.out.println("find it");
                    for (RequiredCourseEntityG1select G1select : rCourseEntityG1select) {
                        RequiredCourseEntity result = new RequiredCourseEntity();
                        result.setCNumber(G1select.getCNumber());
                        result.setCTeacher(G1select.getCTeacher());
                        result.setCCredit(G1select.getCCredit());
                        result.setCGrade(G1select.getCGrade());
                        result.setCName(G1select.getCName());
                        result.setCCategory(G1select.getCCategory());
                        RC_result.add(result);
                    }
                }
                else{
                    crawler.CrawlerHandle(studentID,password);

                    RC_result = crawler.findRCourse(category,grade);

                    for (RequiredCourseEntity course : RC_result) {
                        System.out.println("課號: " + course.getCNumber());
                        System.out.println("老師: " + course.getCTeacher());
                        System.out.println("學分: " + course.getCCredit());
                        System.out.println("年級: " + course.getCGrade());
                        System.out.println("課名: " + course.getCName());
                        System.out.println("課別: " + course.getCCategory());
                        RequiredCourseEntityG1select RCourseEntityG1select = new RequiredCourseEntityG1select();
                        RCourseEntityG1select.setCNumber(course.getCNumber());
                        RCourseEntityG1select.setCTeacher(course.getCTeacher());
                        RCourseEntityG1select.setCCredit(course.getCCredit());
                        RCourseEntityG1select.setCGrade(course.getCGrade());
                        RCourseEntityG1select.setCName(course.getCName());
                        RCourseEntityG1select.setCCategory(course.getCCategory());

                        rcourseG1SelectRepository.save(RCourseEntityG1select);
                    }
                    System.out.println(RC_result.size());
                }
            }
        }
        else if(grade.equals("大二")) {
            System.out.println("大二");
            if(category.equals("必修")){
                System.out.println("必修");
                List<RequiredCourseEntityG2must> rCourseEntityG2must = rcourseG2MustRepository.findByc_category(category);
                if (rCourseEntityG2must != null && !rCourseEntityG2must.isEmpty()){
                    System.out.println("find it");
                    for (RequiredCourseEntityG2must G2must : rCourseEntityG2must) {
                        RequiredCourseEntity result = new RequiredCourseEntity();
                        result.setCNumber(G2must.getCNumber());
                        result.setCTeacher(G2must.getCTeacher());
                        result.setCCredit(G2must.getCCredit());
                        result.setCGrade(G2must.getCGrade());
                        result.setCName(G2must.getCName());
                        result.setCCategory(G2must.getCCategory());
                        RC_result.add(result);
                    }
                }
                else{
                    crawler.CrawlerHandle(studentID,password);

                    RC_result = crawler.findRCourse(category,grade);

                    for (RequiredCourseEntity course : RC_result) {
                        System.out.println("課號: " + course.getCNumber());
                        System.out.println("老師: " + course.getCTeacher());
                        System.out.println("學分: " + course.getCCredit());
                        System.out.println("年級: " + course.getCGrade());
                        System.out.println("課名: " + course.getCName());
                        System.out.println("課別: " + course.getCCategory());
                        RequiredCourseEntityG2must RCourseEntityG2must = new RequiredCourseEntityG2must();
                        RCourseEntityG2must.setCNumber(course.getCNumber());
                        RCourseEntityG2must.setCTeacher(course.getCTeacher());
                        RCourseEntityG2must.setCCredit(course.getCCredit());
                        RCourseEntityG2must.setCGrade(course.getCGrade());
                        RCourseEntityG2must.setCName(course.getCName());
                        RCourseEntityG2must.setCCategory(course.getCCategory());

                        rcourseG2MustRepository.save(RCourseEntityG2must);
                    }
                    System.out.println(RC_result.size());
                }
            }
            else if(category.equals("選修")){
                System.out.println("選修");
                List<RequiredCourseEntityG2select> rCourseEntityG2select = rcourseG2SelectRepository.findByc_category(category);
                if (rCourseEntityG2select != null && !rCourseEntityG2select.isEmpty()){
                    System.out.println("find it");
                    for (RequiredCourseEntityG2select G2select : rCourseEntityG2select) {
                        RequiredCourseEntity result = new RequiredCourseEntity();
                        result.setCNumber(G2select.getCNumber());
                        result.setCTeacher(G2select.getCTeacher());
                        result.setCCredit(G2select.getCCredit());
                        result.setCGrade(G2select.getCGrade());
                        result.setCName(G2select.getCName());
                        result.setCCategory(G2select.getCCategory());
                        RC_result.add(result);
                    }
                }
                else{
                    crawler.CrawlerHandle(studentID,password);

                    RC_result = crawler.findRCourse(category,grade);

                    for (RequiredCourseEntity course : RC_result) {
                        System.out.println("課號: " + course.getCNumber());
                        System.out.println("老師: " + course.getCTeacher());
                        System.out.println("學分: " + course.getCCredit());
                        System.out.println("年級: " + course.getCGrade());
                        System.out.println("課名: " + course.getCName());
                        System.out.println("課別: " + course.getCCategory());
                        RequiredCourseEntityG2select RCourseEntityG2select = new RequiredCourseEntityG2select();
                        RCourseEntityG2select.setCNumber(course.getCNumber());
                        RCourseEntityG2select.setCTeacher(course.getCTeacher());
                        RCourseEntityG2select.setCCredit(course.getCCredit());
                        RCourseEntityG2select.setCGrade(course.getCGrade());
                        RCourseEntityG2select.setCName(course.getCName());
                        RCourseEntityG2select.setCCategory(course.getCCategory());

                        rcourseG2SelectRepository.save(RCourseEntityG2select);
                    }
                    System.out.println(RC_result.size());
                }
            }
        }
        else if(grade.equals("大三")) {
            System.out.println("大三");
            if(category.equals("必修")){
                System.out.println("必修");
                List<RequiredCourseEntityG3must> rCourseEntityG3must = rcourseG3MustRepository.findByc_category(category);
                if (rCourseEntityG3must != null && !rCourseEntityG3must.isEmpty()){
                    System.out.println("find it");
                    for (RequiredCourseEntityG3must G3must : rCourseEntityG3must) {
                        RequiredCourseEntity result = new RequiredCourseEntity();
                        result.setCNumber(G3must.getCNumber());
                        result.setCTeacher(G3must.getCTeacher());
                        result.setCCredit(G3must.getCCredit());
                        result.setCGrade(G3must.getCGrade());
                        result.setCName(G3must.getCName());
                        result.setCCategory(G3must.getCCategory());
                        RC_result.add(result);
                    }
                }
                else{
                    crawler.CrawlerHandle(studentID,password);

                    RC_result = crawler.findRCourse(category,grade);

                    for (RequiredCourseEntity course : RC_result) {
                        System.out.println("課號: " + course.getCNumber());
                        System.out.println("老師: " + course.getCTeacher());
                        System.out.println("學分: " + course.getCCredit());
                        System.out.println("年級: " + course.getCGrade());
                        System.out.println("課名: " + course.getCName());
                        System.out.println("課別: " + course.getCCategory());
                        RequiredCourseEntityG3must RCourseEntityG3must = new RequiredCourseEntityG3must();
                        RCourseEntityG3must.setCNumber(course.getCNumber());
                        RCourseEntityG3must.setCTeacher(course.getCTeacher());
                        RCourseEntityG3must.setCCredit(course.getCCredit());
                        RCourseEntityG3must.setCGrade(course.getCGrade());
                        RCourseEntityG3must.setCName(course.getCName());
                        RCourseEntityG3must.setCCategory(course.getCCategory());

                        rcourseG3MustRepository.save(RCourseEntityG3must);
                    }
                    System.out.println(RC_result.size());
                }
            }
            else if(category.equals("選修")){
                System.out.println("選修");
                List<RequiredCourseEntityG3select> rCourseEntityG3select = rcourseG3SelectRepository.findByc_category(category);
                if (rCourseEntityG3select != null && !rCourseEntityG3select.isEmpty()){
                    System.out.println("find it");
                    for (RequiredCourseEntityG3select G3select : rCourseEntityG3select) {
                        RequiredCourseEntity result = new RequiredCourseEntity();
                        result.setCNumber(G3select.getCNumber());
                        result.setCTeacher(G3select.getCTeacher());
                        result.setCCredit(G3select.getCCredit());
                        result.setCGrade(G3select.getCGrade());
                        result.setCName(G3select.getCName());
                        result.setCCategory(G3select.getCCategory());
                        RC_result.add(result);
                    }
                }
                else{
                    crawler.CrawlerHandle(studentID,password);

                    RC_result = crawler.findRCourse(category,grade);

                    for (RequiredCourseEntity course : RC_result) {
                        System.out.println("課號: " + course.getCNumber());
                        System.out.println("老師: " + course.getCTeacher());
                        System.out.println("學分: " + course.getCCredit());
                        System.out.println("年級: " + course.getCGrade());
                        System.out.println("課名: " + course.getCName());
                        System.out.println("課別: " + course.getCCategory());
                        RequiredCourseEntityG3select RCourseEntityG3select = new RequiredCourseEntityG3select();
                        RCourseEntityG3select.setCNumber(course.getCNumber());
                        RCourseEntityG3select.setCTeacher(course.getCTeacher());
                        RCourseEntityG3select.setCCredit(course.getCCredit());
                        RCourseEntityG3select.setCGrade(course.getCGrade());
                        RCourseEntityG3select.setCName(course.getCName());
                        RCourseEntityG3select.setCCategory(course.getCCategory());

                        rcourseG3SelectRepository.save(RCourseEntityG3select);
                    }
                    System.out.println(RC_result.size());
                }
            }
        }
        else if(grade.equals("大四")) {
            System.out.println("大四");
            if(category.equals("必修")){
                System.out.println("必修");
                List<RequiredCourseEntityG4must> rCourseEntityG4must = rcourseG4MustRepository.findByc_category(category);
                if (rCourseEntityG4must != null && !rCourseEntityG4must.isEmpty()){
                    System.out.println("find it");
                    for (RequiredCourseEntityG4must G4must : rCourseEntityG4must) {
                        RequiredCourseEntity result = new RequiredCourseEntity();
                        result.setCNumber(G4must.getCNumber());
                        result.setCTeacher(G4must.getCTeacher());
                        result.setCCredit(G4must.getCCredit());
                        result.setCGrade(G4must.getCGrade());
                        result.setCName(G4must.getCName());
                        result.setCCategory(G4must.getCCategory());
                        RC_result.add(result);
                    }
                }
                else{
                    crawler.CrawlerHandle(studentID,password);

                    RC_result = crawler.findRCourse(category,grade);

                    for (RequiredCourseEntity course : RC_result) {
                        System.out.println("課號: " + course.getCNumber());
                        System.out.println("老師: " + course.getCTeacher());
                        System.out.println("學分: " + course.getCCredit());
                        System.out.println("年級: " + course.getCGrade());
                        System.out.println("課名: " + course.getCName());
                        System.out.println("課別: " + course.getCCategory());
                        RequiredCourseEntityG4must RCourseEntityG4must = new RequiredCourseEntityG4must();
                        RCourseEntityG4must.setCNumber(course.getCNumber());
                        RCourseEntityG4must.setCTeacher(course.getCTeacher());
                        RCourseEntityG4must.setCCredit(course.getCCredit());
                        RCourseEntityG4must.setCGrade(course.getCGrade());
                        RCourseEntityG4must.setCName(course.getCName());
                        RCourseEntityG4must.setCCategory(course.getCCategory());

                        rcourseG4MustRepository.save(RCourseEntityG4must);
                    }
                    System.out.println(RC_result.size());
                }
            }
            else if(category.equals("選修")){
                System.out.println("選修");
                List<RequiredCourseEntityG4select> rCourseEntityG4select = rcourseG4SelectRepository.findByc_category(category);
                if (rCourseEntityG4select != null && !rCourseEntityG4select.isEmpty()){
                    System.out.println("find it");
                    for (RequiredCourseEntityG4select G4select : rCourseEntityG4select) {
                        RequiredCourseEntity result = new RequiredCourseEntity();
                        result.setCNumber(G4select.getCNumber());
                        result.setCTeacher(G4select.getCTeacher());
                        result.setCCredit(G4select.getCCredit());
                        result.setCGrade(G4select.getCGrade());
                        result.setCName(G4select.getCName());
                        result.setCCategory(G4select.getCCategory());
                        RC_result.add(result);
                    }
                }
                else{
                    crawler.CrawlerHandle(studentID,password);

                    RC_result = crawler.findRCourse(category,grade);

                    for (RequiredCourseEntity course : RC_result) {
                        System.out.println("課號: " + course.getCNumber());
                        System.out.println("老師: " + course.getCTeacher());
                        System.out.println("學分: " + course.getCCredit());
                        System.out.println("年級: " + course.getCGrade());
                        System.out.println("課名: " + course.getCName());
                        System.out.println("課別: " + course.getCCategory());
                        RequiredCourseEntityG4select RCourseEntityG4select = new RequiredCourseEntityG4select();
                        RCourseEntityG4select.setCNumber(course.getCNumber());
                        RCourseEntityG4select.setCTeacher(course.getCTeacher());
                        RCourseEntityG4select.setCCredit(course.getCCredit());
                        RCourseEntityG4select.setCGrade(course.getCGrade());
                        RCourseEntityG4select.setCName(course.getCName());
                        RCourseEntityG4select.setCCategory(course.getCCategory());

                        rcourseG4SelectRepository.save(RCourseEntityG4select);
                    }
                    System.out.println(RC_result.size());
                }
            }
        }
        System.out.println("finish!!");

        return RC_result;
    }


    @PostMapping("/favorites")
    public ResponseEntity<String> favorites(@RequestBody Map<String, String> requestData){
        System.out.println("/favorites");
        //get user's favorites
        SavedEntity savedEntity = savedRepository.findByStudentID(requestData.get("studentID"));
        //user haven't create
        if (savedEntity == null) {
            savedEntity = new SavedEntity();
            savedEntity.setStudentID(requestData.get("studentID"));
        }
        else {
            for (String post : savedEntity.getPostId()){
                if (Objects.equals(requestData.get("postId"), post))
                    return ResponseEntity.badRequest().body("Invalid request");//400
            }
        }
        savedEntity.setPostId(requestData.get("postId"));
        savedRepository.save(savedEntity);
        if(requestData.get("postId").startsWith("F")){
            FoodEntity food = foodRepository.findByPostId(requestData.get("postId"));
            food.setSaved(requestData.get("studentID"));
            foodRepository.save(food);
        } else if (requestData.get("postId").startsWith("H")) {
            HouseEntity house = houseRepository.findByPostId(requestData.get("postId"));
            house.setSaved(requestData.get("studentID"));
            houseRepository.save(house);
        }
        return ResponseEntity.ok("Success");
    }


    @PostMapping("/favorites_load")
    public SavedDTO favoritesLoad(@RequestBody Map<String, String> requestData){
        System.out.println("/favorites_load");
        SavedDTO savedDTO = new SavedDTO();
        SavedEntity savedEntity = savedRepository.findByStudentID(requestData.get("studentID"));
        if(savedEntity==null)return savedDTO;
        else{
            for(String post : savedEntity.getPostId()){
                if(post.startsWith("F")){
                    FoodEntity food = foodRepository.findByPostId(post);
                    FoodDTO foodDTO = new FoodDTO(post, food.getNickname(), food.getStore(), food.getRating(), food.getPost_time());
                    savedDTO.setSavedFood(foodDTO);
                }else if(post.startsWith("H")){
                    HouseEntity house = houseRepository.findByPostId(post);
                    HouseDTO houseDTO = new HouseDTO(post, house.getName(), house.getTitle(), house.getPost_time());
                    savedDTO.setSavedHouse(houseDTO);
                }
            }
            return savedDTO;
        }
    }

    @DeleteMapping("/favorites_delete")
    public ResponseEntity<String> favoritesDelete(@RequestBody Map<String, String> requestData){
        System.out.println("/favorites_delete");
        SavedEntity savedEntity = savedRepository.findByStudentID(requestData.get("studentID"));
        for(String postId : savedEntity.getPostId()){
            if(Objects.equals(postId, requestData.get("postId"))){
                savedEntity.removePostId(postId);
                savedRepository.save(savedEntity);
                if(requestData.get("postId").startsWith("F")){
                    FoodEntity food = foodRepository.findByPostId(requestData.get("postId"));
                    food.removeSaved(requestData.get("studentID"));
                    foodRepository.save(food);
                } else if (requestData.get("postId").startsWith("H")) {
                    HouseEntity house = houseRepository.findByPostId(requestData.get("postId"));
                    house.removeSaved(requestData.get("studentID"));
                    houseRepository.save(house);
                }
                return ResponseEntity.ok("Success");
            }
        }
        return ResponseEntity.badRequest().body("Invalid request");
    }

    @PostMapping("/pre_curriculum_search")
    public List<TimeTableEntity.Pre_Info> preCurriculumSearch(@RequestBody Map<String, String> requestData){
        System.out.println("/pre_curriculum_search");
        TimeTableEntity timeTable = timeTableRepository.findByStudentID(requestData.get("studentID"));
        if(timeTable==null){
            /*timeTable = new TimeTableEntity();
            timeTable.setStudentID(requestData.get("studentID"));
            timeTableRepository.save(timeTable);*/
            List<TimeTableEntity.Pre_Info> list = new ArrayList<>();
            return list; //empty
        }
        return timeTable.getPre_info();
    }

    @PostMapping("/pre_curriculum")
    public ResponseEntity<String> preCurriculum(@RequestBody Map<String, String> requestData){
        System.out.println("/pre_curriculum");
        TimeTableEntity timeTable = timeTableRepository.findByStudentID(requestData.get("studentID"));
        TimeTableEntity.Pre_Info pre_info = new TimeTableEntity.Pre_Info();
        pre_info.setP_class(requestData.get("p_class"));
        pre_info.setP_classNum(requestData.get("p_classNum"));
        String[] timeArray = requestData.get("p_time").split(",");
        pre_info.setP_time(timeArray);
        pre_info.setP_classroom(requestData.get("p_classroom"));
        if (timeTable == null) {
            timeTable = new TimeTableEntity();
            timeTable.setStudentID(requestData.get("studentID"));
            timeTable.setPre_info(pre_info);
        }
        else{timeTable.setPre_info(pre_info);}
        timeTableRepository.save(timeTable);
        return ResponseEntity.ok("Success"); // 200
    }

    @DeleteMapping("/cancel_pre_curriculum")
    public ResponseEntity<String> cancelPreCurriculum(@RequestParam("studentID") String studentID, @RequestParam("p_classNum") String p_classNum){
        System.out.println("/cancel_pre_curriculum");
        TimeTableEntity timeTable = timeTableRepository.findByStudentID(studentID);
        boolean deleted = false;
        if(timeTable == null)return ResponseEntity.badRequest().body("Invalid request : Entity not found"); //400
        for(TimeTableEntity.Pre_Info pre : timeTable.getPre_info()){
            if(Objects.equals(pre.getP_classNum(), p_classNum)){
                timeTable.removePre_info(pre);
                timeTableRepository.save(timeTable);
                deleted=true;
                break;
            }
        }
        if(deleted)return ResponseEntity.ok("Success"); //400
        else return ResponseEntity.badRequest().body("Invalid request : Class not found"); //400
    }

}


