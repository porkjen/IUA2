package com.example.demo.controller;

import com.example.demo.*;

import com.example.demo.dao.*;

import com.example.demo.repository.*;
import com.example.demo.service.*;
import jakarta.security.auth.message.AuthException;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class TodoController {
    @Autowired
    RemainedService remainedService;
    @Autowired
    FinishedRepository fRepository;
    @Autowired
    DetectedRepository dRepository;
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
    ChangeCourseRepository changeCourseRepository;
    @Autowired
    GeneralRepository generalRepository;
    @Autowired
    CourseRepository courseRepository;
    //必選修課程DB
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
    //必選修課程系外DB
    @Autowired
    RCourseOMajorMSRepository rCourseOMajorMSRepository;
    @Autowired
    RCourseOMajorTCRepository rCourseOMajorTCRepository;
    @Autowired
    RCourseOMajorTSRepository rCourseOMajorTSRepository;
    @Autowired
    RCourseOMajorTERepository rCourseOMajorTERepository;
    @Autowired
    RCourseOMajorFSRepository rCourseOMajorFSRepository;
    @Autowired
    RCourseOMajorDARepository rCourseOMajorDARepository;
    @Autowired
    RCourseOMajorBTRepository rCourseOMajorBTRepository;
    @Autowired
    RCourseOMajorRFRepository rCourseOMajorRFRepository;
    @Autowired
    RCourseOMajorMERepository rCourseOMajorMERepository;
    @Autowired
    RCourseOMajorSERepository rCourseOMajorSERepository;
    @Autowired
    RCourseOMajorRWRepository rCourseOMajorRWRepository;
    @Autowired
    RCourseOMajorEERepository rCourseOMajorEERepository;
    @Autowired
    RCourseOMajorCERepository rCourseOMajorCERepository;
    @Autowired
    RCourseOMajorPERepository rCourseOMajorPERepository;

    String secretKey = "au4a83";

    static Crawler crawler = new Crawler();
    AESEncryptionDecryption aesEncryptionDecryption = new AESEncryptionDecryption();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody HashMap <String, String> user)throws TesseractException, IOException, InterruptedException  {
        System.out.println("/login");
        //password encrypt
        String studentID = user.get("studentID");
        String password = user.get("password");
        System.out.println(studentID);
        System.out.println(password);
        String encryptedpwd = aesEncryptionDecryption.encrypt(password, secretKey);
        String decryptedpwd = aesEncryptionDecryption.decrypt(encryptedpwd, secretKey);
        System.out.println("加密:"+encryptedpwd);
        //sign in
        crawler.CrawlerHandle(studentID,password);
        System.out.println("login message " +Crawler.loginMessage);
        if (Objects.equals(crawler.loginMessage, "帳號或密碼錯誤")){
            return ResponseEntity.badRequest().body("Invalid request"); // 回傳狀態碼 400
        }
        //account is not in database
        BasicEntity personalData = basicRepository.findByStudentID(studentID);
        if(personalData==null){
            personalData = crawler.getBasicData(studentID,password);
            personalData.setPassword(encryptedpwd);
            personalData.setEmail(studentID + "@mail.ntou.edu.tw");
            basicRepository.save(personalData);
            SavedEntity savedEntity = new SavedEntity();
            savedEntity.setStudentID(studentID);
            savedRepository.save(savedEntity);
            System.out.println("New user!");
            JwtToken jwtToken = new JwtToken();
            String token = jwtToken.generateToken(user); // 取得token
            Map<String, String> t = new HashMap<>();
            t.put("token", token);
            return ResponseEntity.status(HttpStatus.CREATED).body(t);//201
        }
        else {
            personalData.setPassword(encryptedpwd); //user may change password, update password everytime
            basicRepository.save(personalData);
            System.out.println("Old user!");
            JwtToken jwtToken = new JwtToken();
            String token = jwtToken.generateToken(user); // 取得token
            Map<String, String> t = new HashMap<>();
            t.put("token", token);
            return ResponseEntity.ok(t); // 回傳狀態碼 200
        }
    }

    @PostMapping("/nickname")
    public ResponseEntity<String> postnickname (@RequestBody BasicEntity basic)  {
        System.out.println("/nickname, user : "+basic.getStudentID());
        BasicEntity oldProduct = basicRepository.findByStudentID(basic.getStudentID());
        if(oldProduct == null)return ResponseEntity.badRequest().body("Invalid request : user not found"); // 400
        oldProduct.setNickname(basic.getNickname());
        basicRepository.save(oldProduct);
        return ResponseEntity.ok("Success"); // 回傳狀態碼 200
    }

    @PostMapping("/remained_credits")
    public ResponseEntity<?> postRemainCredits (@RequestBody FinishedCourseList finished, @RequestHeader("Authorization") String au)throws TesseractException, IOException, InterruptedException{
        JwtToken jwtToken = new JwtToken();
        try {
            jwtToken.validateToken(au, finished.getStudentID());
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        ArrayList<FinishedCourse> finishedCourse = new ArrayList<FinishedCourse>();
        System.out.println("*********student ID: " + finished.getStudentID());
        if(fRepository.existsByStudentID(finished.getStudentID())){
            System.out.println("found.");
            FinishedCourseList oriList = fRepository.findByStudentID(finished.getStudentID());
            ArrayList<FinishedCourse> oriCourses =  oriList.getFinishedCourses();
            String sem = oriCourses.get(oriCourses.size() - 1).getSemester();
            // System.out.println("semester: " + sem);
            // finishedCourse = crawler.getFinishedCredict(oriCourses, sem);
            // oriList.setFinishedCourses(finishedCourse);
            //fRepository.save(oriList);
        }
        else{
            finishedCourse = crawler.getFinishedCredict(finishedCourse, "");
            finished.setFinishedCourses(finishedCourse);
            fRepository.save(finished);
        }

        RemainCredit remainCredit = remainedService.computeCredit(finished.getStudentID());
        return ResponseEntity.ok(remainCredit);
        //return remainCredit;
    }

    @PostMapping("/add_detect_course")
    public void addDetectCourse(@RequestBody CourseToBeDetected requestData)throws TesseractException, IOException, InterruptedException{
        ArrayList<CourseToBeDetected> courses = new ArrayList<CourseToBeDetected>();
        if(dRepository.existsByStudentID(requestData.getStudentID())){
            DetectedCoursesList oriList = dRepository.findByStudentID(requestData.getStudentID());
            System.out.println("course number: " + requestData.getNumber());
            courses = oriList.getDetectedCourses();
            courses.add(requestData);
            oriList.setDetectedCourse(courses);
            dRepository.save(oriList);
        }
        else{
            DetectedCoursesList newList = new DetectedCoursesList();
            newList.setStudentID(requestData.getStudentID());
            System.out.println("course number: " + requestData.getNumber());
            courses.add(requestData);
            newList.setDetectedCourse(courses);
            dRepository.save(newList);
        }
        crawler.detectCoureses(courses);
    }

    @PostMapping("/detect_course")
    public void detectCourse()throws TesseractException, IOException, InterruptedException{

    }

    @GetMapping("/core_elective")
    public List<CourseEntity> coreElective(@RequestParam("grade") String grade)throws InterruptedException{
        System.out.println("/core_elective");
        String[][] kernalCourseG2 = {{"數位系統設計", "計算機系統領域"}, {"微處理器原理與組合語言", "計算機系統領域"}};
        String[][] kernalCourseG3 = {{"計算機系統設計", "計算機系統領域"}, {"計算機結構", "計算機系統領域"}, {"軟體工程", "軟體領域"}, {"程式語言", "軟體領域"}, {"資料庫系統", "軟體領域"}, {"嵌入式系統設計", "計算機系統領域"}, {"系統程式", "計算機系統領域"}, {"編譯器", "軟體領域"}, {"人工智慧", "軟體領域"}};
        List<CourseEntity> result = new ArrayList<CourseEntity>();
        if(courseRepository.count() == 0){
            List<CourseEntity> cList = getCourses();
            for(CourseEntity c : cList){
                courseRepository.save(c);
            }
        }
        if(grade.equals("2")){
            for(int i = 0; i < 2; i++){
                List<CourseEntity> rc2 = courseRepository.findByc_name(kernalCourseG2[i][0]);
                for(CourseEntity rc : rc2){
                    rc.setField(kernalCourseG2[i][1]);
                    result.add(rc);
                }
                
            }
        }
        else if(grade.equals("3")){
            for(int i = 0; i < 9; i++){
                List<CourseEntity> rc3 = courseRepository.findByc_name(kernalCourseG3[i][0]);
                for(CourseEntity rc : rc3){
                    rc.setField(kernalCourseG3[i][1]);
                    result.add(rc);
                }
            }
        }
        return result;
    }

    private static List<CourseEntity> getCourses() throws InterruptedException {
        List<CourseEntity> result = crawler.getCourses();
        return result;
    }

    @PostMapping("/rent_post")
    public ResponseEntity<?> rentPost(@RequestBody HouseEntity house, @RequestHeader("Authorization")String au){
        System.out.println("/rent_post, 租屋發文");
        JwtToken jwtToken = new JwtToken();
        try {
            jwtToken.validateToken(au, house.getStudentID());
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
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
        //加到我的文章
        SavedEntity savedEntity = savedRepository.findByStudentID(house.getStudentID());
        savedEntity.setPosted(post_id);//add
        savedRepository.save(savedEntity);
        System.out.println("發文成功");
        return ResponseEntity.ok(house);
    }

    @GetMapping("/rent_load") //list all house posts
    public List<HouseDTO> rentLoad(){
        List<HouseEntity> housePostList = houseRepository.findByStatus("已租");
        List<HouseDTO> SimpleHousePostList = new ArrayList<>();
        for (HouseEntity post : housePostList) {
            HouseDTO dto = new HouseDTO(post.getPostId(), post.getName(), post.getTitle(), post.getPost_time(), post.getStatus());
            SimpleHousePostList.add(dto);
        }
        housePostList = houseRepository.findByStatus("未租");
        for (HouseEntity post : housePostList) {
            HouseDTO dto = new HouseDTO(post.getPostId(), post.getName(), post.getTitle(), post.getPost_time(), post.getStatus());
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
    public ResponseEntity<String> rentPostDelete(@RequestParam("studentID") String studentID, @RequestParam("postId") String postId, @RequestHeader("Authorization")String au){
        System.out.println("/rent_post_delete, 刪除租屋發文");
        HouseEntity house = houseRepository.findByPostId(postId);
        if(house==null){
            System.out.println("貼文沒有找到");
            return ResponseEntity.badRequest().body("Invalid request"); // 400
        }
        JwtToken jwtToken = new JwtToken();
        try {
            jwtToken.validateToken(au, houseRepository.findByPostId(postId).getStudentID());
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        houseRepository.deleteByPostId(postId);
        SavedEntity saved = savedRepository.findByStudentID(studentID);
        saved.removePosted(postId);
        savedRepository.save(saved);
        System.out.println("貼文刪除成功");
        return ResponseEntity.ok("Success");//200
    }

    @PutMapping("/rent_post_modify")
    public ResponseEntity<String> rentPostModify(@RequestBody HouseEntity houseEntity, @RequestHeader("Authorization")String au){
        System.out.println("/rent_post_modify, 修改租屋發文");
        JwtToken jwtToken = new JwtToken();
        try {
            jwtToken.validateToken(au, houseEntity.getStudentID());
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        HouseEntity thisHouse = houseRepository.findByPostId(houseEntity.getPostId());
        houseEntity.setId(thisHouse.getId());
        houseEntity.setName(thisHouse.getName());
        houseEntity.setPost_time(thisHouse.getPost_time());
        houseEntity.setName(thisHouse.getName());
        if(thisHouse.getSaved().size()!=0){
            for(String save : thisHouse.getSaved())houseEntity.setSaved(save);
        }
        houseEntity.setStatus(thisHouse.getStatus());
        houseRepository.save(houseEntity);
        System.out.println("修改成功");
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
                HouseDTO result = new HouseDTO(house.getPostId(), house.getName(), house.getTitle(), house.getPost_time(), house.getStatus());
                resultList.add(result);
            }
        }
        return resultList;
    }

    @GetMapping("/my_rent_posts")
    public List<HouseDTO> myRentPosts(@RequestParam("studentID") String studentID){
        System.out.println("/my_rent_posts, studentID : "+studentID);
        SavedEntity savedEntity = savedRepository.findByStudentID(studentID);
        List<HouseDTO> shortened = new ArrayList<>();
        System.out.println("My Posts : "+savedEntity.getPosted().size());
        for(String postId : savedEntity.getPosted()){
            if(postId.startsWith("H")){
                HouseEntity h = houseRepository.findByPostId(postId);
                HouseDTO dto = new HouseDTO(h.getPostId(), h.getName(), h.getTitle(), h.getPost_time(), h.getStatus());
                shortened.add(dto);
            }
        }
        return shortened;
    }

    @GetMapping("/curriculum_search")
    public ResponseEntity<?> curriculumSearch(@RequestParam("studentID") String studentID, @RequestHeader("Authorization") String au) throws TesseractException, IOException, InterruptedException {
        JwtToken jwtToken = new JwtToken();
        try {
            jwtToken.validateToken(au, studentID);
        } catch (AuthException e) {
            //return null;
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }

        TimeTableEntity timeTable = timeTableRepository.findByStudentID(studentID);
        if(timeTable!=null && timeTable.getInfo().size()!=0){
            return ResponseEntity.ok(timeTable.getInfo());
            //return timeTable.getInfo();
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
            return ResponseEntity.ok(myClassList);
            //return myClassList;
        }
    }


    @PostMapping("/course_search")
    public List<RequiredCourseEntity> course_search( @RequestParam(value = "major") String major, @RequestParam(value = "category") String category,@RequestParam(value = "grade") String grade)throws TesseractException, IOException, InterruptedException  {

        System.out.println("/course_search");
        String studentID ="00957039";
        String password = "";
        //Convert grade code
        if(grade.equals("大一")) {
            grade = "1";
        }
        else if(grade.equals("大二")) {
            grade = "2";
        }
        else if(grade.equals("大三")) {
            grade = "3";
        }
        else if(grade.equals("大四")) {
            grade = "4";
        }

        List<RequiredCourseEntity> RC_result = new ArrayList<>();
        if(major.equals("資工")) {
            System.out.println("*"+major+"*"+category+"*"+grade);
            if (grade.equals("1")) {
                System.out.println("大一");
                if (category.equals("必修")) {
                    System.out.println("必修");
                    List<RequiredCourseEntityG1must> rCourseEntityG1must = rcourseG1MustRepository.findByc_category(category);
                    if (rCourseEntityG1must != null && !rCourseEntityG1must.isEmpty()) {
                        System.out.println("find it G1M");
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
                    } else {
                        crawler.CrawlerHandle(studentID, password);

                        RC_result = crawler.findRCourse(major, category, grade);

                        for (RequiredCourseEntity course : RC_result) {
                            RequiredCourseEntityG1must RCourseEntityG1must = new RequiredCourseEntityG1must();
                            RCourseEntityG1must.setCNumber(course.getCNumber());
                            RCourseEntityG1must.setCTeacher(course.getCTeacher());
                            RCourseEntityG1must.setCCredit(course.getCCredit());
                            RCourseEntityG1must.setCGrade(course.getCGrade());
                            RCourseEntityG1must.setCName(course.getCName());
                            RCourseEntityG1must.setCCategory(course.getCCategory());

                            rcourseG1MustRepository.save(RCourseEntityG1must);
                        }
                        System.out.println("Total : " + RC_result.size());
                    }
                } else if (category.equals("選修")) {
                    System.out.println("選修");
                    List<RequiredCourseEntityG1select> rCourseEntityG1select = rcourseG1SelectRepository.findByc_category(category);
                    if (rCourseEntityG1select != null && !rCourseEntityG1select.isEmpty()) {
                        System.out.println("find it G1S");
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
                    } else {
                        crawler.CrawlerHandle(studentID, password);

                        RC_result = crawler.findRCourse(major, category, grade);

                        for (RequiredCourseEntity course : RC_result) {
                            RequiredCourseEntityG1select RCourseEntityG1select = new RequiredCourseEntityG1select();
                            RCourseEntityG1select.setCNumber(course.getCNumber());
                            RCourseEntityG1select.setCTeacher(course.getCTeacher());
                            RCourseEntityG1select.setCCredit(course.getCCredit());
                            RCourseEntityG1select.setCGrade(course.getCGrade());
                            RCourseEntityG1select.setCName(course.getCName());
                            RCourseEntityG1select.setCCategory(course.getCCategory());

                            rcourseG1SelectRepository.save(RCourseEntityG1select);
                        }
                        System.out.println("Total : " + RC_result.size());
                    }
                }
            } else if (grade.equals("2")) {
                System.out.println("大二");
                if (category.equals("必修")) {
                    System.out.println("必修");
                    List<RequiredCourseEntityG2must> rCourseEntityG2must = rcourseG2MustRepository.findByc_category(category);
                    if (rCourseEntityG2must != null && !rCourseEntityG2must.isEmpty()) {
                        System.out.println("find it G2M");
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
                    } else {
                        crawler.CrawlerHandle(studentID, password);

                        RC_result = crawler.findRCourse(major, category, grade);

                        for (RequiredCourseEntity course : RC_result) {
                            RequiredCourseEntityG2must RCourseEntityG2must = new RequiredCourseEntityG2must();
                            RCourseEntityG2must.setCNumber(course.getCNumber());
                            RCourseEntityG2must.setCTeacher(course.getCTeacher());
                            RCourseEntityG2must.setCCredit(course.getCCredit());
                            RCourseEntityG2must.setCGrade(course.getCGrade());
                            RCourseEntityG2must.setCName(course.getCName());
                            RCourseEntityG2must.setCCategory(course.getCCategory());

                            rcourseG2MustRepository.save(RCourseEntityG2must);
                        }
                        System.out.println("Total : " + RC_result.size());
                    }
                } else if (category.equals("選修")) {
                    System.out.println("選修");
                    List<RequiredCourseEntityG2select> rCourseEntityG2select = rcourseG2SelectRepository.findByc_category(category);
                    if (rCourseEntityG2select != null && !rCourseEntityG2select.isEmpty()) {
                        System.out.println("find it G2S");
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
                    } else {
                        crawler.CrawlerHandle(studentID, password);

                        RC_result = crawler.findRCourse(major, category, grade);

                        for (RequiredCourseEntity course : RC_result) {
                            RequiredCourseEntityG2select RCourseEntityG2select = new RequiredCourseEntityG2select();
                            RCourseEntityG2select.setCNumber(course.getCNumber());
                            RCourseEntityG2select.setCTeacher(course.getCTeacher());
                            RCourseEntityG2select.setCCredit(course.getCCredit());
                            RCourseEntityG2select.setCGrade(course.getCGrade());
                            RCourseEntityG2select.setCName(course.getCName());
                            RCourseEntityG2select.setCCategory(course.getCCategory());

                            rcourseG2SelectRepository.save(RCourseEntityG2select);
                        }
                        System.out.println("Total : " + RC_result.size());
                    }
                }
            } else if (grade.equals("3")) {
                System.out.println("大三");
                if (category.equals("必修")) {
                    System.out.println("必修");
                    List<RequiredCourseEntityG3must> rCourseEntityG3must = rcourseG3MustRepository.findByc_category(category);
                    if (rCourseEntityG3must != null && !rCourseEntityG3must.isEmpty()) {
                        System.out.println("find it G3M");
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
                    } else {
                        crawler.CrawlerHandle(studentID, password);

                        RC_result = crawler.findRCourse(major, category, grade);

                        for (RequiredCourseEntity course : RC_result) {
                            RequiredCourseEntityG3must RCourseEntityG3must = new RequiredCourseEntityG3must();
                            RCourseEntityG3must.setCNumber(course.getCNumber());
                            RCourseEntityG3must.setCTeacher(course.getCTeacher());
                            RCourseEntityG3must.setCCredit(course.getCCredit());
                            RCourseEntityG3must.setCGrade(course.getCGrade());
                            RCourseEntityG3must.setCName(course.getCName());
                            RCourseEntityG3must.setCCategory(course.getCCategory());

                            rcourseG3MustRepository.save(RCourseEntityG3must);
                        }
                        System.out.println("Total : " + RC_result.size());
                    }
                } else if (category.equals("選修")) {
                    System.out.println("選修");
                    List<RequiredCourseEntityG3select> rCourseEntityG3select = rcourseG3SelectRepository.findByc_category(category);
                    if (rCourseEntityG3select != null && !rCourseEntityG3select.isEmpty()) {
                        System.out.println("find it G3S");
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
                    } else {
                        crawler.CrawlerHandle(studentID, password);

                        RC_result = crawler.findRCourse(major, category, grade);

                        for (RequiredCourseEntity course : RC_result) {
                            RequiredCourseEntityG3select RCourseEntityG3select = new RequiredCourseEntityG3select();
                            RCourseEntityG3select.setCNumber(course.getCNumber());
                            RCourseEntityG3select.setCTeacher(course.getCTeacher());
                            RCourseEntityG3select.setCCredit(course.getCCredit());
                            RCourseEntityG3select.setCGrade(course.getCGrade());
                            RCourseEntityG3select.setCName(course.getCName());
                            RCourseEntityG3select.setCCategory(course.getCCategory());

                            rcourseG3SelectRepository.save(RCourseEntityG3select);
                        }
                        System.out.println("Total : " + RC_result.size());
                    }
                }
            } else if (grade.equals("4")) {
                System.out.println("大四");
                if (category.equals("必修")) {
                    System.out.println("必修");
                    List<RequiredCourseEntityG4must> rCourseEntityG4must = rcourseG4MustRepository.findByc_category(category);
                    if (rCourseEntityG4must != null && !rCourseEntityG4must.isEmpty()) {
                        System.out.println("find it G4M");
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
                    } else {
                        crawler.CrawlerHandle(studentID, password);

                        RC_result = crawler.findRCourse(major, category, grade);

                        for (RequiredCourseEntity course : RC_result) {
                            RequiredCourseEntityG4must RCourseEntityG4must = new RequiredCourseEntityG4must();
                            RCourseEntityG4must.setCNumber(course.getCNumber());
                            RCourseEntityG4must.setCTeacher(course.getCTeacher());
                            RCourseEntityG4must.setCCredit(course.getCCredit());
                            RCourseEntityG4must.setCGrade(course.getCGrade());
                            RCourseEntityG4must.setCName(course.getCName());
                            RCourseEntityG4must.setCCategory(course.getCCategory());

                            rcourseG4MustRepository.save(RCourseEntityG4must);
                        }
                        System.out.println("Total : " + RC_result.size());
                    }
                } else if (category.equals("選修")) {
                    System.out.println("選修");
                    List<RequiredCourseEntityG4select> rCourseEntityG4select = rcourseG4SelectRepository.findByc_category(category);
                    if (rCourseEntityG4select != null && !rCourseEntityG4select.isEmpty()) {
                        System.out.println("find it G4S");
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
                    } else {
                        crawler.CrawlerHandle(studentID, password);

                        RC_result = crawler.findRCourse(major, category, grade);

                        for (RequiredCourseEntity course : RC_result) {
                            RequiredCourseEntityG4select RCourseEntityG4select = new RequiredCourseEntityG4select();
                            RCourseEntityG4select.setCNumber(course.getCNumber());
                            RCourseEntityG4select.setCTeacher(course.getCTeacher());
                            RCourseEntityG4select.setCCredit(course.getCCredit());
                            RCourseEntityG4select.setCGrade(course.getCGrade());
                            RCourseEntityG4select.setCName(course.getCName());
                            RCourseEntityG4select.setCCategory(course.getCCategory());

                            rcourseG4SelectRepository.save(RCourseEntityG4select);
                        }
                        System.out.println("Total : " + RC_result.size());
                    }
                }
            }
        }
        else if(major.equals("商船")){
            //List<RequiredCourseEntityOuterMajor> RC_result = new ArrayList<>();
            System.out.println("*"+major+"*"+category+"*"+grade);
            List<RequiredCourseEntityOuterMajorMS> rCourseEntityOuterMajorMS = rCourseOMajorMSRepository.findByCategoryAndGrade(category,grade);
            if (rCourseEntityOuterMajorMS != null && !rCourseEntityOuterMajorMS.isEmpty()) {
                System.out.println("find it OuterMajorMS");
                for (RequiredCourseEntityOuterMajorMS omMS : rCourseEntityOuterMajorMS) {
                    RequiredCourseEntity result = new RequiredCourseEntity();
                    result.setCNumber(omMS.getCNumber());
                    result.setCTeacher(omMS.getCTeacher());
                    result.setCCredit(omMS.getCCredit());
                    result.setCGrade(omMS.getCGrade());
                    result.setCName(omMS.getCName());
                    result.setCCategory(omMS.getCCategory());
                    result.setCMajor(omMS.getCMajor());
                    RC_result.add(result);
                }
            } else {
                crawler.CrawlerHandle(studentID, password);

                RC_result = crawler.findRCourse(major, category, grade);

                for (RequiredCourseEntity course : RC_result) {
                    RequiredCourseEntityOuterMajorMS RCourseEntityOuterMajorMS = new RequiredCourseEntityOuterMajorMS();
                    RCourseEntityOuterMajorMS.setCNumber(course.getCNumber());
                    RCourseEntityOuterMajorMS.setCTeacher(course.getCTeacher());
                    RCourseEntityOuterMajorMS.setCCredit(course.getCCredit());
                    RCourseEntityOuterMajorMS.setCGrade(course.getCGrade());
                    RCourseEntityOuterMajorMS.setCName(course.getCName());
                    RCourseEntityOuterMajorMS.setCCategory(course.getCCategory());
                    RCourseEntityOuterMajorMS.setCMajor(course.getCMajor());

                    rCourseOMajorMSRepository.save(RCourseEntityOuterMajorMS);
                }
                System.out.println("Total : " + RC_result.size());
            }
        }
        else if(major.equals("航管")){
            System.out.println("*"+major+"*"+category+"*"+grade);
            List<RequiredCourseEntityOuterMajorTC> rCourseEntityOuterMajorTC = rCourseOMajorTCRepository.findByCategoryAndGrade(category,grade);
            if (rCourseEntityOuterMajorTC != null && !rCourseEntityOuterMajorTC.isEmpty()) {
                System.out.println("find it OuterMajorMS");
                for (RequiredCourseEntityOuterMajorTC omTC : rCourseEntityOuterMajorTC) {
                    RequiredCourseEntity result = new RequiredCourseEntity();
                    result.setCNumber(omTC.getCNumber());
                    result.setCTeacher(omTC.getCTeacher());
                    result.setCCredit(omTC.getCCredit());
                    result.setCGrade(omTC.getCGrade());
                    result.setCName(omTC.getCName());
                    result.setCCategory(omTC.getCCategory());
                    result.setCMajor(omTC.getCMajor());
                    RC_result.add(result);
                }
            } else {
                crawler.CrawlerHandle(studentID, password);

                RC_result = crawler.findRCourse(major, category, grade);

                for (RequiredCourseEntity course : RC_result) {
                    RequiredCourseEntityOuterMajorTC RCourseEntityOuterMajorTC = new RequiredCourseEntityOuterMajorTC();
                    RCourseEntityOuterMajorTC.setCNumber(course.getCNumber());
                    RCourseEntityOuterMajorTC.setCTeacher(course.getCTeacher());
                    RCourseEntityOuterMajorTC.setCCredit(course.getCCredit());
                    RCourseEntityOuterMajorTC.setCGrade(course.getCGrade());
                    RCourseEntityOuterMajorTC.setCName(course.getCName());
                    RCourseEntityOuterMajorTC.setCCategory(course.getCCategory());
                    RCourseEntityOuterMajorTC.setCMajor(course.getCMajor());

                    rCourseOMajorTCRepository.save(RCourseEntityOuterMajorTC);
                }
                System.out.println("Total : " + RC_result.size());
            }
        }
        else if(major.equals("運輸")){
            System.out.println("*"+major+"*"+category+"*"+grade);
            List<RequiredCourseEntityOuterMajorTS> rCourseEntityOuterMajorTS = rCourseOMajorTSRepository.findByCategoryAndGrade(category,grade);
            if (rCourseEntityOuterMajorTS != null && !rCourseEntityOuterMajorTS.isEmpty()) {
                System.out.println("find it OuterMajorMS");
                for (RequiredCourseEntityOuterMajorTS omTS : rCourseEntityOuterMajorTS) {
                    RequiredCourseEntity result = new RequiredCourseEntity();
                    result.setCNumber(omTS.getCNumber());
                    result.setCTeacher(omTS.getCTeacher());
                    result.setCCredit(omTS.getCCredit());
                    result.setCGrade(omTS.getCGrade());
                    result.setCName(omTS.getCName());
                    result.setCCategory(omTS.getCCategory());
                    result.setCMajor(omTS.getCMajor());
                    RC_result.add(result);
                }
            } else {
                crawler.CrawlerHandle(studentID, password);

                RC_result = crawler.findRCourse(major, category, grade);

                for (RequiredCourseEntity course : RC_result) {
                    RequiredCourseEntityOuterMajorTS RCourseEntityOuterMajorTS = new RequiredCourseEntityOuterMajorTS();
                    RCourseEntityOuterMajorTS.setCNumber(course.getCNumber());
                    RCourseEntityOuterMajorTS.setCTeacher(course.getCTeacher());
                    RCourseEntityOuterMajorTS.setCCredit(course.getCCredit());
                    RCourseEntityOuterMajorTS.setCGrade(course.getCGrade());
                    RCourseEntityOuterMajorTS.setCName(course.getCName());
                    RCourseEntityOuterMajorTS.setCCategory(course.getCCategory());
                    RCourseEntityOuterMajorTS.setCMajor(course.getCMajor());

                    rCourseOMajorTSRepository.save(RCourseEntityOuterMajorTS);
                }
                System.out.println("Total : " + RC_result.size());
            }

        }
        else if(major.equals("輪機")){
            System.out.println("*"+major+"*"+category+"*"+grade);
            List<RequiredCourseEntityOuterMajorTE> rCourseEntityOuterMajorTE = rCourseOMajorTERepository.findByCategoryAndGrade(category,grade);
            if (rCourseEntityOuterMajorTE != null && !rCourseEntityOuterMajorTE.isEmpty()) {
                System.out.println("find it OuterMajorMS");
                for (RequiredCourseEntityOuterMajorTE omTE : rCourseEntityOuterMajorTE) {
                    RequiredCourseEntity result = new RequiredCourseEntity();
                    result.setCNumber(omTE.getCNumber());
                    result.setCTeacher(omTE.getCTeacher());
                    result.setCCredit(omTE.getCCredit());
                    result.setCGrade(omTE.getCGrade());
                    result.setCName(omTE.getCName());
                    result.setCCategory(omTE.getCCategory());
                    result.setCMajor(omTE.getCMajor());
                    RC_result.add(result);
                }
            } else {
                crawler.CrawlerHandle(studentID, password);

                RC_result = crawler.findRCourse(major, category, grade);

                for (RequiredCourseEntity course : RC_result) {
                    RequiredCourseEntityOuterMajorTE RCourseEntityOuterMajorTE = new RequiredCourseEntityOuterMajorTE();
                    RCourseEntityOuterMajorTE.setCNumber(course.getCNumber());
                    RCourseEntityOuterMajorTE.setCTeacher(course.getCTeacher());
                    RCourseEntityOuterMajorTE.setCCredit(course.getCCredit());
                    RCourseEntityOuterMajorTE.setCGrade(course.getCGrade());
                    RCourseEntityOuterMajorTE.setCName(course.getCName());
                    RCourseEntityOuterMajorTE.setCCategory(course.getCCategory());
                    RCourseEntityOuterMajorTE.setCMajor(course.getCMajor());

                    rCourseOMajorTERepository.save(RCourseEntityOuterMajorTE);
                }
                System.out.println("Total : " + RC_result.size());
            }

        }
        else if(major.equals("食科")){
            System.out.println("*"+major+"*"+category+"*"+grade);
            List<RequiredCourseEntityOuterMajorFS> rCourseEntityOuterMajorFS = rCourseOMajorFSRepository.findByCategoryAndGrade(category,grade);
            if (rCourseEntityOuterMajorFS != null && !rCourseEntityOuterMajorFS.isEmpty()) {
                System.out.println("find it OuterMajorMS");
                for (RequiredCourseEntityOuterMajorFS omFS : rCourseEntityOuterMajorFS) {
                    RequiredCourseEntity result = new RequiredCourseEntity();
                    result.setCNumber(omFS.getCNumber());
                    result.setCTeacher(omFS.getCTeacher());
                    result.setCCredit(omFS.getCCredit());
                    result.setCGrade(omFS.getCGrade());
                    result.setCName(omFS.getCName());
                    result.setCCategory(omFS.getCCategory());
                    result.setCMajor(omFS.getCMajor());
                    RC_result.add(result);
                }
            } else {
                crawler.CrawlerHandle(studentID, password);

                RC_result = crawler.findRCourse(major, category, grade);

                for (RequiredCourseEntity course : RC_result) {
                    RequiredCourseEntityOuterMajorFS RCourseEntityOuterMajorFS = new RequiredCourseEntityOuterMajorFS();
                    RCourseEntityOuterMajorFS.setCNumber(course.getCNumber());
                    RCourseEntityOuterMajorFS.setCTeacher(course.getCTeacher());
                    RCourseEntityOuterMajorFS.setCCredit(course.getCCredit());
                    RCourseEntityOuterMajorFS.setCGrade(course.getCGrade());
                    RCourseEntityOuterMajorFS.setCName(course.getCName());
                    RCourseEntityOuterMajorFS.setCCategory(course.getCCategory());
                    RCourseEntityOuterMajorFS.setCMajor(course.getCMajor());

                    rCourseOMajorFSRepository.save(RCourseEntityOuterMajorFS);
                }
                System.out.println("Total : " + RC_result.size());
            }

        }
        else if(major.equals("養殖")){
            System.out.println("*"+major+"*"+category+"*"+grade);
            List<RequiredCourseEntityOuterMajorDA> rCourseEntityOuterMajorDA = rCourseOMajorDARepository.findByCategoryAndGrade(category,grade);
            if (rCourseEntityOuterMajorDA != null && !rCourseEntityOuterMajorDA.isEmpty()) {
                System.out.println("find it OuterMajorMS");
                for (RequiredCourseEntityOuterMajorDA omDA : rCourseEntityOuterMajorDA) {
                    RequiredCourseEntity result = new RequiredCourseEntity();
                    result.setCNumber(omDA.getCNumber());
                    result.setCTeacher(omDA.getCTeacher());
                    result.setCCredit(omDA.getCCredit());
                    result.setCGrade(omDA.getCGrade());
                    result.setCName(omDA.getCName());
                    result.setCCategory(omDA.getCCategory());
                    result.setCMajor(omDA.getCMajor());
                    RC_result.add(result);
                }
            } else {
                crawler.CrawlerHandle(studentID, password);

                RC_result = crawler.findRCourse(major, category, grade);

                for (RequiredCourseEntity course : RC_result) {
                    RequiredCourseEntityOuterMajorDA RCourseEntityOuterMajorDA = new RequiredCourseEntityOuterMajorDA();
                    RCourseEntityOuterMajorDA.setCNumber(course.getCNumber());
                    RCourseEntityOuterMajorDA.setCTeacher(course.getCTeacher());
                    RCourseEntityOuterMajorDA.setCCredit(course.getCCredit());
                    RCourseEntityOuterMajorDA.setCGrade(course.getCGrade());
                    RCourseEntityOuterMajorDA.setCName(course.getCName());
                    RCourseEntityOuterMajorDA.setCCategory(course.getCCategory());
                    RCourseEntityOuterMajorDA.setCMajor(course.getCMajor());

                    rCourseOMajorDARepository.save(RCourseEntityOuterMajorDA);
                }
                System.out.println("Total : " + RC_result.size());
            }

        }
        else if(major.equals("生科")){
            System.out.println("*"+major+"*"+category+"*"+grade);
            List<RequiredCourseEntityOuterMajorBT> rCourseEntityOuterMajorBT = rCourseOMajorBTRepository.findByCategoryAndGrade(category,grade);
            if (rCourseEntityOuterMajorBT != null && !rCourseEntityOuterMajorBT.isEmpty()) {
                System.out.println("find it OuterMajorMS");
                for (RequiredCourseEntityOuterMajorBT omBT : rCourseEntityOuterMajorBT) {
                    RequiredCourseEntity result = new RequiredCourseEntity();
                    result.setCNumber(omBT.getCNumber());
                    result.setCTeacher(omBT.getCTeacher());
                    result.setCCredit(omBT.getCCredit());
                    result.setCGrade(omBT.getCGrade());
                    result.setCName(omBT.getCName());
                    result.setCCategory(omBT.getCCategory());
                    result.setCMajor(omBT.getCMajor());
                    RC_result.add(result);
                }
            } else {
                crawler.CrawlerHandle(studentID, password);

                RC_result = crawler.findRCourse(major, category, grade);

                for (RequiredCourseEntity course : RC_result) {
                    RequiredCourseEntityOuterMajorBT RCourseEntityOuterMajorBT = new RequiredCourseEntityOuterMajorBT();
                    RCourseEntityOuterMajorBT.setCNumber(course.getCNumber());
                    RCourseEntityOuterMajorBT.setCTeacher(course.getCTeacher());
                    RCourseEntityOuterMajorBT.setCCredit(course.getCCredit());
                    RCourseEntityOuterMajorBT.setCGrade(course.getCGrade());
                    RCourseEntityOuterMajorBT.setCName(course.getCName());
                    RCourseEntityOuterMajorBT.setCCategory(course.getCCategory());
                    RCourseEntityOuterMajorBT.setCMajor(course.getCMajor());

                    rCourseOMajorBTRepository.save(RCourseEntityOuterMajorBT);
                }
                System.out.println("Total : " + RC_result.size());
            }

        }
        else if(major.equals("環漁")){
            System.out.println("*"+major+"*"+category+"*"+grade);
            List<RequiredCourseEntityOuterMajorRF> rCourseEntityOuterMajorRF = rCourseOMajorRFRepository.findByCategoryAndGrade(category,grade);
            if (rCourseEntityOuterMajorRF != null && !rCourseEntityOuterMajorRF.isEmpty()) {
                System.out.println("find it OuterMajorMS");
                for (RequiredCourseEntityOuterMajorRF omRF : rCourseEntityOuterMajorRF) {
                    RequiredCourseEntity result = new RequiredCourseEntity();
                    result.setCNumber(omRF.getCNumber());
                    result.setCTeacher(omRF.getCTeacher());
                    result.setCCredit(omRF.getCCredit());
                    result.setCGrade(omRF.getCGrade());
                    result.setCName(omRF.getCName());
                    result.setCCategory(omRF.getCCategory());
                    result.setCMajor(omRF.getCMajor());
                    RC_result.add(result);
                }
            } else {
                crawler.CrawlerHandle(studentID, password);

                RC_result = crawler.findRCourse(major, category, grade);

                for (RequiredCourseEntity course : RC_result) {
                    RequiredCourseEntityOuterMajorRF RCourseEntityOuterMajorRF = new RequiredCourseEntityOuterMajorRF();
                    RCourseEntityOuterMajorRF.setCNumber(course.getCNumber());
                    RCourseEntityOuterMajorRF.setCTeacher(course.getCTeacher());
                    RCourseEntityOuterMajorRF.setCCredit(course.getCCredit());
                    RCourseEntityOuterMajorRF.setCGrade(course.getCGrade());
                    RCourseEntityOuterMajorRF.setCName(course.getCName());
                    RCourseEntityOuterMajorRF.setCCategory(course.getCCategory());
                    RCourseEntityOuterMajorRF.setCMajor(course.getCMajor());

                    rCourseOMajorRFRepository.save(RCourseEntityOuterMajorRF);
                }
                System.out.println("Total : " + RC_result.size());
            }

        }
        else if(major.equals("機械")){
            System.out.println("*"+major+"*"+category+"*"+grade);
            List<RequiredCourseEntityOuterMajorME> rCourseEntityOuterMajorME = rCourseOMajorMERepository.findByCategoryAndGrade(category,grade);
            if (rCourseEntityOuterMajorME != null && !rCourseEntityOuterMajorME.isEmpty()) {
                System.out.println("find it OuterMajorMS");
                for (RequiredCourseEntityOuterMajorME omME : rCourseEntityOuterMajorME) {
                    RequiredCourseEntity result = new RequiredCourseEntity();
                    result.setCNumber(omME.getCNumber());
                    result.setCTeacher(omME.getCTeacher());
                    result.setCCredit(omME.getCCredit());
                    result.setCGrade(omME.getCGrade());
                    result.setCName(omME.getCName());
                    result.setCCategory(omME.getCCategory());
                    result.setCMajor(omME.getCMajor());
                    RC_result.add(result);
                }
            } else {
                crawler.CrawlerHandle(studentID, password);

                RC_result = crawler.findRCourse(major, category, grade);

                for (RequiredCourseEntity course : RC_result) {
                    RequiredCourseEntityOuterMajorME RCourseEntityOuterMajorME = new RequiredCourseEntityOuterMajorME();
                    RCourseEntityOuterMajorME.setCNumber(course.getCNumber());
                    RCourseEntityOuterMajorME.setCTeacher(course.getCTeacher());
                    RCourseEntityOuterMajorME.setCCredit(course.getCCredit());
                    RCourseEntityOuterMajorME.setCGrade(course.getCGrade());
                    RCourseEntityOuterMajorME.setCName(course.getCName());
                    RCourseEntityOuterMajorME.setCCategory(course.getCCategory());
                    RCourseEntityOuterMajorME.setCMajor(course.getCMajor());

                    rCourseOMajorMERepository.save(RCourseEntityOuterMajorME);
                }
                System.out.println("Total : " + RC_result.size());
            }

        }
        else if(major.equals("系工")){
            System.out.println("*"+major+"*"+category+"*"+grade);
            List<RequiredCourseEntityOuterMajorSE> rCourseEntityOuterMajorSE = rCourseOMajorSERepository.findByCategoryAndGrade(category,grade);
            if (rCourseEntityOuterMajorSE != null && !rCourseEntityOuterMajorSE.isEmpty()) {
                System.out.println("find it OuterMajorMS");
                for (RequiredCourseEntityOuterMajorSE omSE : rCourseEntityOuterMajorSE) {
                    RequiredCourseEntity result = new RequiredCourseEntity();
                    result.setCNumber(omSE.getCNumber());
                    result.setCTeacher(omSE.getCTeacher());
                    result.setCCredit(omSE.getCCredit());
                    result.setCGrade(omSE.getCGrade());
                    result.setCName(omSE.getCName());
                    result.setCCategory(omSE.getCCategory());
                    result.setCMajor(omSE.getCMajor());
                    RC_result.add(result);
                }
            } else {
                crawler.CrawlerHandle(studentID, password);

                RC_result = crawler.findRCourse(major, category, grade);

                for (RequiredCourseEntity course : RC_result) {
                    RequiredCourseEntityOuterMajorSE RCourseEntityOuterMajorSE = new RequiredCourseEntityOuterMajorSE();
                    RCourseEntityOuterMajorSE.setCNumber(course.getCNumber());
                    RCourseEntityOuterMajorSE.setCTeacher(course.getCTeacher());
                    RCourseEntityOuterMajorSE.setCCredit(course.getCCredit());
                    RCourseEntityOuterMajorSE.setCGrade(course.getCGrade());
                    RCourseEntityOuterMajorSE.setCName(course.getCName());
                    RCourseEntityOuterMajorSE.setCCategory(course.getCCategory());
                    RCourseEntityOuterMajorSE.setCMajor(course.getCMajor());

                    rCourseOMajorSERepository.save(RCourseEntityOuterMajorSE);
                }
                System.out.println("Total : " + RC_result.size());
            }

        }
        else if(major.equals("河工")){
            System.out.println("*"+major+"*"+category+"*"+grade);
            List<RequiredCourseEntityOuterMajorRW> rCourseEntityOuterMajorRW = rCourseOMajorRWRepository.findByCategoryAndGrade(category,grade);
            if (rCourseEntityOuterMajorRW != null && !rCourseEntityOuterMajorRW.isEmpty()) {
                System.out.println("find it OuterMajorMS");
                for (RequiredCourseEntityOuterMajorRW omRW : rCourseEntityOuterMajorRW) {
                    RequiredCourseEntity result = new RequiredCourseEntity();
                    result.setCNumber(omRW.getCNumber());
                    result.setCTeacher(omRW.getCTeacher());
                    result.setCCredit(omRW.getCCredit());
                    result.setCGrade(omRW.getCGrade());
                    result.setCName(omRW.getCName());
                    result.setCCategory(omRW.getCCategory());
                    result.setCMajor(omRW.getCMajor());
                    RC_result.add(result);
                }
            } else {
                crawler.CrawlerHandle(studentID, password);

                RC_result = crawler.findRCourse(major, category, grade);

                for (RequiredCourseEntity course : RC_result) {
                    RequiredCourseEntityOuterMajorRW RCourseEntityOuterMajorRW = new RequiredCourseEntityOuterMajorRW();
                    RCourseEntityOuterMajorRW.setCNumber(course.getCNumber());
                    RCourseEntityOuterMajorRW.setCTeacher(course.getCTeacher());
                    RCourseEntityOuterMajorRW.setCCredit(course.getCCredit());
                    RCourseEntityOuterMajorRW.setCGrade(course.getCGrade());
                    RCourseEntityOuterMajorRW.setCName(course.getCName());
                    RCourseEntityOuterMajorRW.setCCategory(course.getCCategory());
                    RCourseEntityOuterMajorRW.setCMajor(course.getCMajor());

                    rCourseOMajorRWRepository.save(RCourseEntityOuterMajorRW);
                }
                System.out.println("Total : " + RC_result.size());
            }

        }
        else if(major.equals("電機")){
            System.out.println("*"+major+"*"+category+"*"+grade);
            List<RequiredCourseEntityOuterMajorEE> rCourseEntityOuterMajorEE = rCourseOMajorEERepository.findByCategoryAndGrade(category,grade);
            if (rCourseEntityOuterMajorEE != null && !rCourseEntityOuterMajorEE.isEmpty()) {
                System.out.println("find it OuterMajorMS");
                for (RequiredCourseEntityOuterMajorEE omEE : rCourseEntityOuterMajorEE) {
                    RequiredCourseEntity result = new RequiredCourseEntity();
                    result.setCNumber(omEE.getCNumber());
                    result.setCTeacher(omEE.getCTeacher());
                    result.setCCredit(omEE.getCCredit());
                    result.setCGrade(omEE.getCGrade());
                    result.setCName(omEE.getCName());
                    result.setCCategory(omEE.getCCategory());
                    result.setCMajor(omEE.getCMajor());
                    RC_result.add(result);
                }
            } else {
                crawler.CrawlerHandle(studentID, password);

                RC_result = crawler.findRCourse(major, category, grade);

                for (RequiredCourseEntity course : RC_result) {
                    RequiredCourseEntityOuterMajorEE RCourseEntityOuterMajorEE = new RequiredCourseEntityOuterMajorEE();
                    RCourseEntityOuterMajorEE.setCNumber(course.getCNumber());
                    RCourseEntityOuterMajorEE.setCTeacher(course.getCTeacher());
                    RCourseEntityOuterMajorEE.setCCredit(course.getCCredit());
                    RCourseEntityOuterMajorEE.setCGrade(course.getCGrade());
                    RCourseEntityOuterMajorEE.setCName(course.getCName());
                    RCourseEntityOuterMajorEE.setCCategory(course.getCCategory());
                    RCourseEntityOuterMajorEE.setCMajor(course.getCMajor());

                    rCourseOMajorEERepository.save(RCourseEntityOuterMajorEE);
                }
                System.out.println("Total : " + RC_result.size());
            }

        }
        else if(major.equals("通訊")){
            System.out.println("*"+major+"*"+category+"*"+grade);
            List<RequiredCourseEntityOuterMajorCE> rCourseEntityOuterMajorCE = rCourseOMajorCERepository.findByCategoryAndGrade(category,grade);
            if (rCourseEntityOuterMajorCE != null && !rCourseEntityOuterMajorCE.isEmpty()) {
                System.out.println("find it OuterMajorMS");
                for (RequiredCourseEntityOuterMajorCE omCE : rCourseEntityOuterMajorCE) {
                    RequiredCourseEntity result = new RequiredCourseEntity();
                    result.setCNumber(omCE.getCNumber());
                    result.setCTeacher(omCE.getCTeacher());
                    result.setCCredit(omCE.getCCredit());
                    result.setCGrade(omCE.getCGrade());
                    result.setCName(omCE.getCName());
                    result.setCCategory(omCE.getCCategory());
                    result.setCMajor(omCE.getCMajor());
                    RC_result.add(result);
                }
            } else {
                crawler.CrawlerHandle(studentID, password);

                RC_result = crawler.findRCourse(major, category, grade);

                for (RequiredCourseEntity course : RC_result) {
                    RequiredCourseEntityOuterMajorCE RCourseEntityOuterMajorCE = new RequiredCourseEntityOuterMajorCE();
                    RCourseEntityOuterMajorCE.setCNumber(course.getCNumber());
                    RCourseEntityOuterMajorCE.setCTeacher(course.getCTeacher());
                    RCourseEntityOuterMajorCE.setCCredit(course.getCCredit());
                    RCourseEntityOuterMajorCE.setCGrade(course.getCGrade());
                    RCourseEntityOuterMajorCE.setCName(course.getCName());
                    RCourseEntityOuterMajorCE.setCCategory(course.getCCategory());
                    RCourseEntityOuterMajorCE.setCMajor(course.getCMajor());

                    rCourseOMajorCERepository.save(RCourseEntityOuterMajorCE);
                }
                System.out.println("Total : " + RC_result.size());
            }

        }
        else if(major.equals("光電")){
            System.out.println("*"+major+"*"+category+"*"+grade);
            List<RequiredCourseEntityOuterMajorPE> rCourseEntityOuterMajorPE = rCourseOMajorPERepository.findByCategoryAndGrade(category,grade);
            if (rCourseEntityOuterMajorPE != null && !rCourseEntityOuterMajorPE.isEmpty()) {
                System.out.println("find it OuterMajorMS");
                for (RequiredCourseEntityOuterMajorPE omPE : rCourseEntityOuterMajorPE) {
                    RequiredCourseEntity result = new RequiredCourseEntity();
                    result.setCNumber(omPE.getCNumber());
                    result.setCTeacher(omPE.getCTeacher());
                    result.setCCredit(omPE.getCCredit());
                    result.setCGrade(omPE.getCGrade());
                    result.setCName(omPE.getCName());
                    result.setCCategory(omPE.getCCategory());
                    result.setCMajor(omPE.getCMajor());
                    RC_result.add(result);
                }
            } else {
                crawler.CrawlerHandle(studentID, password);

                RC_result = crawler.findRCourse(major, category, grade);

                for (RequiredCourseEntity course : RC_result) {
                    RequiredCourseEntityOuterMajorPE RCourseEntityOuterMajorPE = new RequiredCourseEntityOuterMajorPE();
                    RCourseEntityOuterMajorPE.setCNumber(course.getCNumber());
                    RCourseEntityOuterMajorPE.setCTeacher(course.getCTeacher());
                    RCourseEntityOuterMajorPE.setCCredit(course.getCCredit());
                    RCourseEntityOuterMajorPE.setCGrade(course.getCGrade());
                    RCourseEntityOuterMajorPE.setCName(course.getCName());
                    RCourseEntityOuterMajorPE.setCCategory(course.getCCategory());
                    RCourseEntityOuterMajorPE.setCMajor(course.getCMajor());

                    rCourseOMajorPERepository.save(RCourseEntityOuterMajorPE);
                }
                System.out.println("Total : " + RC_result.size());
            }

        }
        System.out.println("RequiredCourseSearch is end!!");

        return RC_result;
    }



    @PostMapping("/favorites")
    public ResponseEntity<String> favorites(@RequestBody Map<String, String> requestData){
        System.out.println("/favorites");
        System.out.println("user : "+requestData.get("studentID")+", post : "+requestData.get("postId"));
        if(basicRepository.findByStudentID(requestData.get("studentID"))==null)return ResponseEntity.badRequest().body("Invalid request : user not found"); // 400
        //get user's favorites
        SavedEntity savedEntity = savedRepository.findByStudentID(requestData.get("studentID"));
        for (String post : savedEntity.getSaved()){
            if (Objects.equals(requestData.get("postId"), post))
                return ResponseEntity.status(HttpStatus.CREATED).body("post already added");//201
        }
        savedEntity.setSaved(requestData.get("postId"));//add
        savedRepository.save(savedEntity);
        if(requestData.get("postId").startsWith("F")){
            FoodEntity food = foodRepository.findByPostId(requestData.get("postId"));
            food.setSaved(requestData.get("studentID"));//add
            foodRepository.save(food);
        } else if (requestData.get("postId").startsWith("H")) {
            HouseEntity house = houseRepository.findByPostId(requestData.get("postId"));
            house.setSaved(requestData.get("studentID"));
            houseRepository.save(house);
        }
        else {
            return ResponseEntity.badRequest().body("Invalid request : postID error"); // 400
        }
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/favorites_load")
    public SavedDTO favoritesLoad(@RequestBody Map<String, String> requestData){
        System.out.println("/favorites_load");
        SavedDTO savedDTO = new SavedDTO();
        SavedEntity savedEntity = savedRepository.findByStudentID(requestData.get("studentID"));
        if(savedEntity.getSaved().size() == 0) {
            System.out.println("empty");
            return savedDTO; //didn't save any post : return empty
        }
        else{
            ArrayList<String> deleted = new ArrayList<>();
            for(String post : savedEntity.getSaved()){
                if(post.startsWith("F")){
                    FoodEntity food = foodRepository.findByPostId(post);
                    FoodDTO foodDTO;
                    if(food ==null) {deleted.add(post); continue;}
                    else foodDTO = new FoodDTO(post, food.getNickname(), food.getStore(), food.getRating(), food.getPost_time(), food.getRoad(), food.getDistance());
                    savedDTO.setSavedFood(foodDTO);
                }else if(post.startsWith("H")){
                    HouseEntity house = houseRepository.findByPostId(post);
                    HouseDTO houseDTO;
                    if(house !=null) houseDTO = new HouseDTO(post, house.getName(), house.getTitle(), house.getPost_time(), house.getStatus());
                    else {deleted.add(post); continue;}
                    savedDTO.setSavedHouse(houseDTO);
                }
            }
            if(deleted.size()!=0){
                for(String post : deleted)
                    savedEntity.removeSaved(post);

                savedRepository.save(savedEntity);
            }

            return savedDTO;
        }
    }

    @DeleteMapping("/favorites_delete")
    public ResponseEntity<String> favoritesDelete(@RequestBody Map<String, String> requestData){
        System.out.println("/favorites_delete");
        System.out.println("user : "+requestData.get("studentID")+", post : "+requestData.get("postId"));
        SavedEntity savedEntity = savedRepository.findByStudentID(requestData.get("studentID"));
        for(String postId : savedEntity.getSaved()){
            if(Objects.equals(postId, requestData.get("postId"))){
                savedEntity.removeSaved(postId);
                savedRepository.save(savedEntity);
                if(requestData.get("postId").startsWith("F")){
                    FoodEntity food = foodRepository.findByPostId(requestData.get("postId"));
                    if(food ==null)return ResponseEntity.ok("Success");//post has been deleted
                    food.removeSaved(requestData.get("studentID"));
                    foodRepository.save(food);
                } else if (requestData.get("postId").startsWith("H")) {
                    HouseEntity house = houseRepository.findByPostId(requestData.get("postId"));
                    if(house ==null)return ResponseEntity.ok("Success");//post has been deleted
                    house.removeSaved(requestData.get("studentID"));
                    houseRepository.save(house);
                }
                return ResponseEntity.ok("Success");
            }
        }
        return ResponseEntity.badRequest().body("Invalid request");//400
    }

    @PutMapping("/change_post_status")
    public ResponseEntity<String> changePostStatus(@RequestBody Map<String, String> requestData){
        if(requestData.get("postId").startsWith("H")){
            HouseEntity house = houseRepository.findByPostId(requestData.get("postId"));
            if(!Objects.equals(house.getStudentID(), requestData.get("studentID")))return ResponseEntity.badRequest().body("Invalid request : not the author");//400
            house.setStatus("已租");
            houseRepository.save(house);
            return ResponseEntity.ok("Success");
        }
        //requestData.get("postId").startsWith("C")
        else {
            ChangeCourseEntity changeCourse = changeCourseRepository.findByPostId(requestData.get("postId"));
            if(!Objects.equals(changeCourse.getStudentID(), requestData.get("studentID")))return ResponseEntity.badRequest().body("Invalid request : not the author");//400
            changeCourse.setStatus("已換");
            changeCourseRepository.save(changeCourse);
            return ResponseEntity.ok("Success");
        }
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

    @PostMapping("/general_education")
    public List<GeneralCourseEntity> generalEducation(@RequestParam("field") String field) throws InterruptedException {
        if(generalRepository.count() == 0){
            List<GeneralCourseEntity> gcList = getGeneralCourses();
            for(GeneralCourseEntity gc : gcList){
                generalRepository.save(gc);
            }
        }
        List<GeneralCourseEntity> result = generalRepository.findBySubfield(field);
        return result;
    }

    private static List<GeneralCourseEntity> getGeneralCourses() throws InterruptedException {
        List<GeneralCourseEntity> result = crawler.getAllGeneralClass();
        return result;
    }

}

