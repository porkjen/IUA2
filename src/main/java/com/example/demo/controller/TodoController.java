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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class TodoController {
    @Autowired
    PushNotificationService pushNotificationService;
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
    RCourseMajorCSERepository rCourseMajorCSERepository;
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
    @Autowired
    ChatroomRecordRepository chatroomRecordRepository;
    @Autowired
    ChatRoomApiRepository chatRoomApiRepository;
    @Autowired
    RecomdCourseRepository recomdCourseRepository;
    @Autowired
    PECourseRepository peCourseRepository; //體育
    @Autowired
    ForeignLanguageCourseRepository foreignLanguageCourseRepository; //外語
    @Autowired
    EnglishCourseRepository englishCourseRepository;//英文
    @Autowired
    KeyRepository keyRepository;
    @Autowired
    ChangeCourseHaveRepository changeCourseHaveRepository;

    static Crawler crawler = new Crawler();
    String account = "";
    String pwd = "";


    @Autowired
    private TodoService todoService;

    AESEncryptionDecryption aesEncryptionDecryption = new AESEncryptionDecryption();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody HashMap <String, String> user, @RequestHeader("fcmToken")String fcmToken)throws TesseractException, IOException, InterruptedException  {
        System.out.println("/login");
        String secretKey = keyRepository.findByUse("pswKey").getKey();
        //password encrypt
        String studentID = user.get("studentID");
        String password = user.get("password");
        account = studentID;
        todoService.setAccount(account);
        pwd = password;
        System.out.println(studentID);
        System.out.println(password);
        String encryptedpwd = aesEncryptionDecryption.encrypt(password, secretKey);
        System.out.println("加密:"+encryptedpwd);
        BasicEntity personalData = basicRepository.findByStudentID(studentID);
        sendTestNotification(fcmToken);
        //account is not in database
        if(personalData==null){
            //sign in
            crawler.CrawlerHandle(studentID,password);
            System.out.println("login message " +Crawler.loginMessage);
            if (Objects.equals(crawler.loginMessage, "帳號或密碼錯誤")){
                return ResponseEntity.badRequest().body("Invalid request"); // 回傳狀態碼 400
            }
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
            if(!Objects.equals(aesEncryptionDecryption.decrypt(personalData.getPassword(), secretKey), password)){
                //sign in
                crawler.CrawlerHandle(studentID,password);
                System.out.println("login message " +Crawler.loginMessage);
                if (Objects.equals(crawler.loginMessage, "帳號或密碼錯誤")){
                    return ResponseEntity.badRequest().body("Invalid request"); // 回傳狀態碼 400
                }
                personalData.setPassword(encryptedpwd); //user had changed password, update password
                basicRepository.save(personalData);
            }
            System.out.println("Old user!");
            JwtToken jwtToken = new JwtToken();
            String token = jwtToken.generateToken(user); // 取得token
            Map<String, String> t = new HashMap<>();
            t.put("token", token);
            return ResponseEntity.ok(t); // 回傳狀態碼 200
        }
    }

    private ResponseEntity<?> sendTestNotification(String token){
        PushNotificationRequest request = new PushNotificationRequest("Welcome!", "This is IUA", "welcome", token);
        WebPushEntity webPushEntity = new WebPushEntity();
        webPushEntity.setToken(token);
        if(webPushRepository.existsByStudentID(account)){
            webPushEntity = webPushRepository.findByStudentID(account);
            webPushEntity.getNotifications().add(request);
        }
        else{
            ArrayList<PushNotificationRequest> noticications = new ArrayList<PushNotificationRequest>();
            noticications.add(request);
            webPushEntity.setStudentID(account);
            webPushEntity.setNotifications(noticications);
        }
        webPushRepository.save(webPushEntity);
        pushNotificationService.sendPushNotificationToToken(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
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

    @GetMapping("/core_elective_detail")
    public CourseEntity coreElectiveDetail(@RequestParam("number") String number, @RequestParam("grade") String grade)throws InterruptedException {
        List<CourseEntity> list = courseRepository.findByc_number(number);
        CourseEntity result = new CourseEntity();
        for(CourseEntity c : list){
            if(c.getGrade().equals(grade)){
                result = c;
                break;
            }
        }
        return result;
    }

    private static List<CourseEntity> getCourses() throws InterruptedException {
        List<CourseEntity> result = crawler.getCourses();
        return result;
    }

    @GetMapping("/courses")
    public List<?> courses(@RequestParam("category") String category){
        if(Objects.equals(category, "general")){
            List<CourseDTO> courseDTOList = new ArrayList<>();
            for(GeneralCourseEntity g : generalRepository.findAll()){
                CourseDTO courseDTO = new CourseDTO();
                courseDTO.setName(g.getName());
                courseDTO.setClassNum(g.getClassNum());
                courseDTO.setCategory(g.getCategory());
                courseDTO.setTeacher(g.getTeacher());
                courseDTO.setTime(g.getTime().split(","));
                courseDTO.setClassroom(g.getClassroom());
                courseDTO.setTarget(g.getTarget());
                courseDTO.setEvaluation(g.getEvaluation());
                courseDTO.setSyllabus(g.getSyllabus());
                courseDTOList.add(courseDTO);
            }
            return courseDTOList;
        }
        else if(Objects.equals(category, "PE")){
            List<CourseDTO> courseDTOList = new ArrayList<>();
            for(PECourseEntity pe : peCourseRepository.findAll()){
                CourseDTO courseDTO = new CourseDTO();
                courseDTO.setName(pe.getName());
                courseDTO.setClassNum(pe.getClassNum());
                courseDTO.setCategory(pe.getCategory());
                courseDTO.setTeacher(pe.getTeacher());
                courseDTO.setTime(pe.getTime());
                courseDTO.setClassroom(pe.getClassroom());
                courseDTO.setTarget(pe.getTarget());
                courseDTO.setEvaluation(pe.getEvaluation());
                courseDTO.setSyllabus(pe.getSyllabus());
                courseDTOList.add(courseDTO);
            }
            return courseDTOList;
        }
        else if(Objects.equals(category, "foreign_language")){
            List<CourseDTO> courseDTOList = new ArrayList<>();
            for(ForeignLanguageEntity f : foreignLanguageCourseRepository.findAll()){
                CourseDTO courseDTO = new CourseDTO();
                courseDTO.setName(f.getName());
                courseDTO.setClassNum(f.getClassNum());
                courseDTO.setCategory(f.getCategory());
                courseDTO.setTeacher(f.getTeacher());
                courseDTO.setTime(f.getTime());
                courseDTO.setClassroom(f.getClassroom());
                courseDTO.setTarget(f.getTarget());
                courseDTO.setEvaluation(f.getEvaluation());
                courseDTO.setSyllabus(f.getSyllabus());
                courseDTOList.add(courseDTO);
            }
            return courseDTOList;
        }
        else { //english
            List<CourseDTO> courseDTOList = new ArrayList<>();
            for (EnglishCourseEntity en : englishCourseRepository.findAll()) {
                CourseDTO courseDTO = new CourseDTO();
                courseDTO.setName(en.getName());
                courseDTO.setClassNum(en.getClassNum());
                courseDTO.setCategory(en.getCategory());
                courseDTO.setTeacher(en.getTeacher());
                courseDTO.setTime(en.getTime());
                courseDTO.setClassroom(en.getClassroom());
                courseDTO.setTarget(en.getTarget());
                courseDTO.setEvaluation(en.getEvaluation());
                courseDTO.setSyllabus(en.getSyllabus());
                courseDTOList.add(courseDTO);
            }
            return courseDTOList;
        }
    }
    @GetMapping("/curriculum_search")
    public ResponseEntity<?> curriculumSearch(@RequestParam("studentID") String studentID, @RequestHeader("Authorization") String au) throws TesseractException, IOException, InterruptedException {
        String secretKey = keyRepository.findByUse("pswKey").getKey();
        JwtToken jwtToken = new JwtToken();
        try {
            jwtToken.validateToken(au, studentID);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        List<TimeTableDTO> shortTT = new ArrayList<>();
        TimeTableEntity timeTable = timeTableRepository.findByStudentID(studentID);
        if(timeTable!=null && timeTable.getInfo().size()!=0){
            for(TimeTableEntity.Info i : timeTable.getInfo()){
                shortTT.add(new TimeTableDTO(i.getName(), i.getClassNum(), i.getTime(), i.getClassroom(), i.getTeacher(), i.getCategory()));
            }
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
                shortTT.add(new TimeTableDTO(i.getName(), i.getClassNum(), i.getTime(), i.getClassroom(), i.getTeacher(), i.getCategory()));
                table.setInfo(i);
            }
            timeTableRepository.save(table);
        }
        return ResponseEntity.ok(shortTT);
    }

    @PostMapping("/recommend_course_emptyhall")
    public List<RecommandCourseEntity.Display> recommend_course_emptyhall()throws TesseractException, IOException, InterruptedException {
        String[][] last_semester = {{"數值分析","102","103","104","資訊安全領域","一般選修"},{"計算機系統設計", "102","103","104","計算機領域","核心選修"},
                {"Python程式語言","106","107","108","軟體領域&計算科學領域","一般選修"},{"資料庫系統","106","107","108","軟體領域","核心選修"},
                {"進階程式競賽技巧","111","112","113","軟體領域&計算科學領域","一般選修"},{"網頁程式設計","206","207","208","軟體領域","一般選修"},
                {"計算機結構", "206","207","208","計算機領域","核心選修"},{"數位影像處理","206","207","208","人工智慧領域","一般選修"},
                {"軟體工程", "302","303","304","軟體領域","核心選修"},{"電腦圖學","302","303","304","人工智慧領域","一般選修"},
                {"圖論演算法","402","403","404","計算科學領域","一般選修"},{"程式語言","408","409","410","軟體領域","核心選修"},
                {"數位系統設計","506","507","508","計算機領域","核心選修"}};
        /*
        String[][] core_software_next = {{"編譯器", "","",""},{"軟體工程", "","",""},{"系統工程", "","",""},{"人工智慧", "","",""}}; //#6
        String[][] core_computer_next ={{"微處理器原理與組合語言","","",""},{"嵌入式系統設計", "","",""},{"系統程式", "","",""}}; //#3
        String[][] computer_next = {{"Verilog硬體描述語言","","",""},{"ios應用程式語言開發入門","","",""},{"Android行動裝置軟體設計","","",""}};//co  //#3
        String[][] software_next ={{"ASP.NET程式設計","","",""},{"MATLAB程式設計","","",""},{"組合論","","",""}, {"JAVA程式設計","","",""},
                {"巨量資料運算導論","","",""},{"機器學習技術","","",""},{"進階資料庫","","",""},{"物聯網技術與應用","","",""},{"IOS App遊戲開發","","",""}};//sw  //#15
        String[][] artificial_intelligence_next ={{"機器視覺理論應用","","",""},{"3D列印技術與系統","","",""}};//ai  //#2
        String[][] computational_science_next ={{"資訊安全實務管理","","",""}};//cs  //#7
         */
        //cs&sw->Python程式語言 MATLAB程式設計 組合論 進階程式競賽技巧
        //cs&is&sw->資訊安全實務管理
        //ai&sw->巨量資料運算導論 物聯網技術與應用 機器學習技術
        //co&sw->ios應用程式語言開發入門 Android行動裝置軟體設計
        String[] alreadyClass={};
        int num = 0;
        TimeTableEntity timeTable = timeTableRepository.findByStudentID(account);
        List<RecommandCourseEntity.Display> rcEH = new ArrayList<>();
        RecommandCourseEntity recommandCourse = recomdCourseRepository.findByStudentID(account);
        if(recommandCourse == null){
            recommandCourse.setStudentID(account);
        }
        if(timeTable ==null){
            crawler.CrawlerHandle(account,pwd);
            List<TimeTableEntity.Info> myClassList = crawler.getMyClass(account,pwd);
            TimeTableEntity table = new TimeTableEntity();
            if (timeTable != null) { //copy to new
                table.setId(timeTable.getId());
                table.setStudentID(timeTable.getStudentID());
                table.setWholePre_info(timeTable.getPre_info());
            }
            else table.setStudentID(account); //create a new one
            for(TimeTableEntity.Info i : myClassList){
                System.out.println(i.getName());
                table.setInfo(i);
            }
            timeTableRepository.save(table);
        }
        List<TimeTableEntity.Info> timeList = new ArrayList<>();
        timeList = timeTable.getInfo();
        for(TimeTableEntity.Info index : timeList){
            String[] temp = index.getTime();
            for(int i = 0;i < 3 ;i++){
                alreadyClass[num] = temp[i];
                num++;
            }
        }
        for(int i = 0;i < 13;i++){
            RecommandCourseEntity.Display coreShow = new RecommandCourseEntity.Display();
            for(int j = 0; j < num;j++){
                if (!last_semester[i][1].equals(alreadyClass[j])) {
                    for(int k = 0; k < num;k++){
                        if (!last_semester[i][2].equals(alreadyClass[k])) {
                            for(int l = 0; l < num;l++){
                                if (!last_semester[i][3].equals(alreadyClass[l])) {
                                    coreShow.setName(last_semester[i][0]);
                                    coreShow.setCategory(last_semester[i][5]);
                                    coreShow.setField(last_semester[i][4]);
                                }
                            }
                        }
                    }
                }
            }
            rcEH.add(coreShow);
            recommandCourse.setDisplay(coreShow);
        }

        return rcEH;
    }
    
    @PostMapping("/recommend_course_rating")
    public RecommandCourseEntity course_recommand_rating()throws TesseractException, IOException, InterruptedException {
        RecommandCourseEntity recommandCourse = new RecommandCourseEntity();
        recommandCourse.setStudentID(account);
        //String grade = basicRepository.findByStudentID(account).getGrade();
        crawler.CrawlerHandle(account, pwd);
        List<RecommandCourseEntity.Info> hcList = crawler.getHistoryCourse();
        for (RecommandCourseEntity.Info i:hcList){
            System.out.println(i.getName());
            recommandCourse.setInfo(i);
        }
        return recommandCourse;
    }

    @PostMapping("/recommend_course_rating_result")
    public RecommandCourseEntity course_recommand_rating_result(@RequestParam(value = "coreC")String coreC,@RequestParam(value = "coreS")String coreS,@RequestParam(value = "co")String co,
                                                                @RequestParam(value = "sw")String sw,@RequestParam(value = "ai")String ai,@RequestParam(value = "is")String is,
                                                                @RequestParam(value = "cs")String cs)throws TesseractException, IOException, InterruptedException {
        String[] core_software = {"程式語言","資料庫系統","軟體工程","編譯器","系統工程","人工智慧"};//軟體工程有上下兩學期  //#6
        String[] core_computer = {"數位系統設計","微處理器原理與組合語言","計算機系統設計","計算機結構","嵌入式系統設計","系統程式"};//#6
        String[] computer = {"Verilog硬體描述語言","ios應用程式語言開發入門","Android行動裝置軟體設計"};//co  //#3
        String[] software = {"Python程式語言","網頁程式設計","ASP.NET程式設計","MATLAB程式設計","組合論","JAVA程式設計","ios應用程式語言開發入門",
                "巨量資料運算導論","進階程式競賽技巧","機器學習技術","進階資料庫","物聯網技術與應用","Android行動裝置軟體設計","物件導向軟體工程","IOS App遊戲開發"};//sw  //#15
        String[] artificial_intelligence = {"數位影像處理","電腦圖學","巨量資料運算導論","機器視覺理論應用","機器學習技術","物聯網技術與應用","3D列印技術與系統"};//ai  //#7
        String[] information_security = {"資訊安全實務管理"};//資訊安全實務管理有上下兩學期  //is   //#1
        String[] computational_science = {"Python程式語言","MATLAB程式設計","組合論","數值分析","圖論演算法","資訊安全實務管理","進階程式競賽技巧"};//cs  //#7
        //cs&sw->Python程式語言 MATLAB程式設計 組合論 進階程式競賽技巧
        //cs&is&sw->資訊安全實務管理
        //ai&sw->巨量資料運算導論 物聯網技術與應用 機器學習技術
        //co&sw->ios應用程式語言開發入門 Android行動裝置軟體設計
        Integer[] temp = {0,0,0,0,0,0,0};
        RecommandCourseEntity recommandCourse = recomdCourseRepository.findByStudentID(account);
        if(recommandCourse == null){
            recommandCourse.setStudentID(account);
        }
        temp[0] = Integer.parseInt(coreC);
        temp[1] = Integer.parseInt(coreS);
        temp[2] = Integer.parseInt(co);
        temp[3] = Integer.parseInt(sw);
        temp[4] = Integer.parseInt(ai);
        temp[5] = Integer.parseInt(is);
        temp[6] = Integer.parseInt(cs);
        int flag = 0;
        for(int i = 1;i < 7;i++){
            if(temp[i]==temp[i-1]){
                flag++;
            }
        }
        if(flag == 6){
            int randCore = (int)(Math.random()*12);
            int randCSAIC = (int)(Math.random()*23);
            RecommandCourseEntity.Display coreShow = new RecommandCourseEntity.Display();
            if(randCore < 6){
                coreShow.setName(core_computer[randCore]);
                coreShow.setField("計算機領域");
            }
            else{
                randCore -= 6;
                coreShow.setName(core_software[randCore]);
                coreShow.setField("軟體領域");
            }
            coreShow.setCategory("核心選修");
            recommandCourse.setDisplay(coreShow);

            RecommandCourseEntity.Display caiscShow = new RecommandCourseEntity.Display();
            if(randCSAIC < 3){
                caiscShow.setName(computer[randCSAIC]);
                caiscShow.setField("計算機領域");
            }
            else if(randCSAIC < 11){
                randCSAIC -= 3;
                caiscShow.setName(software[randCSAIC]);
                caiscShow.setField("軟體領域");
            }
            else if(randCSAIC < 18){
                randCSAIC -= 11;
                caiscShow.setName(artificial_intelligence[randCSAIC]);
                caiscShow.setField("人工智慧領域");
            }
            else if(randCSAIC < 19){
                randCSAIC -= 18;
                caiscShow.setName(information_security[randCSAIC]);
                caiscShow.setField("資訊安全領域");
            }
            else if(randCSAIC < 23){
                randCSAIC -= 19;
                caiscShow.setName(computational_science[randCSAIC]);
                caiscShow.setField("計算科學領域");
            }
            caiscShow.setCategory("一般選修");
            recommandCourse.setDisplay(caiscShow);
        }
        int max = temp[0];
        for(int i = 1;i < 7;i++){
            if(temp[i]>max){
                max = temp[i];
            }
        }
        Integer[] tempMax={};
        int index = 0;
        for(int i = 0;i < 7;i++){
            if(temp[i]==max){
                tempMax[index] = i;
                index++;
            }
        }
        for(int i = 0;i < index;i++){
            if(tempMax[i] == 0){
                int randCore = (int)(Math.random()*6);
                RecommandCourseEntity.Display coreShow = new RecommandCourseEntity.Display();
                coreShow.setName(core_computer[randCore]);
                coreShow.setField("計算機領域");
                coreShow.setCategory("核心選修");
                recommandCourse.setDisplay(coreShow);
            }
            if(tempMax[i] == 1){
                int randCore = (int)(Math.random()*6);
                RecommandCourseEntity.Display coreShow = new RecommandCourseEntity.Display();
                coreShow.setName(core_software[randCore]);
                coreShow.setField("軟體領域");
                coreShow.setCategory("核心選修");
                recommandCourse.setDisplay(coreShow);
            }
            if(tempMax[i] == 2){
                int randCore = (int)(Math.random()*3);
                RecommandCourseEntity.Display coreShow = new RecommandCourseEntity.Display();
                coreShow.setName(core_software[randCore]);
                coreShow.setField("計算機領域");
                coreShow.setCategory("一般選修");
                recommandCourse.setDisplay(coreShow);
            }
            if(tempMax[i] == 3){
                int randCore = (int)(Math.random()*8);
                RecommandCourseEntity.Display coreShow = new RecommandCourseEntity.Display();
                coreShow.setName(core_software[randCore]);
                coreShow.setField("軟體領域");
                coreShow.setCategory("一般選修");
                recommandCourse.setDisplay(coreShow);
            }
            if(tempMax[i] == 4){
                int randCore = (int)(Math.random()*7);
                RecommandCourseEntity.Display coreShow = new RecommandCourseEntity.Display();
                coreShow.setName(core_software[randCore]);
                coreShow.setField("人工智慧領域");
                coreShow.setCategory("一般選修");
                recommandCourse.setDisplay(coreShow);
            }
            if(tempMax[i] == 5){
                int randCore = (int)(Math.random()*1);
                RecommandCourseEntity.Display coreShow = new RecommandCourseEntity.Display();
                coreShow.setName(core_software[randCore]);
                coreShow.setField("資訊安全領域");
                coreShow.setCategory("一般選修");
                recommandCourse.setDisplay(coreShow);
            }
            if(tempMax[i] == 6){
                int randCore = (int)(Math.random()*4);
                RecommandCourseEntity.Display coreShow = new RecommandCourseEntity.Display();
                coreShow.setName(core_software[randCore]);
                coreShow.setField("計算科學領域");
                coreShow.setCategory("一般選修");
                recommandCourse.setDisplay(coreShow);
            }
        }
        return recommandCourse;
    }
    @GetMapping("/curriculum_search_detail")
    public ResponseEntity<?> curriculumSearchDetail(@RequestParam("studentID") String studentID, @RequestParam("Cname")String name, @RequestHeader("Authorization") String au){
        JwtToken jwtToken = new JwtToken();
        try {
            jwtToken.validateToken(au, studentID);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        TimeTableEntity timeTable = timeTableRepository.findByStudentID(studentID);
        for(TimeTableEntity.Info i : timeTable.getInfo()){
            if(Objects.equals(i.getName(), name))return ResponseEntity.ok(i);
        }
        return ResponseEntity.badRequest().body("Invalid request : postID error"); // 400
    }


    @PostMapping("/course_search_detail")
    public List<RequiredCourseEntity> course_searchDetail( @RequestParam(value = "major") String major, @RequestParam(value = "number") String number,@RequestParam(value = "grade") String grade)throws TesseractException, IOException, InterruptedException {

        System.out.println("/course_search_detail");
        String studentID = account;
        String password = pwd;
        List<RequiredCourseEntity> RC_detail = new ArrayList<>();
        if(major.equals("資工")){
            System.out.println("Detail*"+major+"*"+number+"*"+grade);
            List<RequiredCourseEntityMajorCSE> rCourseEntityMajorCSE = rCourseMajorCSERepository.findByNumberAndGrade(number,grade);
            for (RequiredCourseEntityMajorCSE rcDetail : rCourseEntityMajorCSE) {
                RequiredCourseEntity result = new RequiredCourseEntity();
                result.setCNumber(rcDetail.getCNumber());
                result.setCTeacher(rcDetail.getCTeacher());
                result.setCCredit(rcDetail.getCCredit());
                result.setCGrade(rcDetail.getCGrade());
                result.setCName(rcDetail.getCName());
                result.setCCategory(rcDetail.getCCategory());
                result.setCMajor(rcDetail.getCMajor());
                result.setCTime(rcDetail.getCTime());
                result.setCLocation(rcDetail.getCLocation());
                result.setCPeople(rcDetail.getCPeople());
                result.setCObjective(rcDetail.getCObjective());
                result.setCReference(rcDetail.getCReference());
                result.setCPrecourse(rcDetail.getCPrecourse());
                result.setCOutline(rcDetail.getCOutline());
                result.setCTmethod(rcDetail.getCTmethod());
                result.setCSyllabus(rcDetail.getCSyllabus());
                result.setCEvaluation(rcDetail.getCEvaluation());  //total is 17
                RC_detail.add(result);
            }
        }
        else if(major.equals("商船")){
            System.out.println("*"+major+"*"+number+"*"+grade);
            List<RequiredCourseEntityOuterMajorMS> rCourseEntityOMajorMS = rCourseOMajorMSRepository.findByNumberAndGrade(number,grade);
            for (RequiredCourseEntityOuterMajorMS rcDetail : rCourseEntityOMajorMS) {
                RequiredCourseEntity result = new RequiredCourseEntity();
                result.setCNumber(rcDetail.getCNumber());
                result.setCTeacher(rcDetail.getCTeacher());
                result.setCCredit(rcDetail.getCCredit());
                result.setCGrade(rcDetail.getCGrade());
                result.setCName(rcDetail.getCName());
                result.setCCategory(rcDetail.getCCategory());
                result.setCMajor(rcDetail.getCMajor());
                result.setCTime(rcDetail.getCTime());
                result.setCLocation(rcDetail.getCLocation());
                result.setCPeople(rcDetail.getCPeople());
                result.setCObjective(rcDetail.getCObjective());
                result.setCReference(rcDetail.getCReference());
                result.setCPrecourse(rcDetail.getCPrecourse());
                result.setCOutline(rcDetail.getCOutline());
                result.setCTmethod(rcDetail.getCTmethod());
                result.setCSyllabus(rcDetail.getCSyllabus());
                result.setCEvaluation(rcDetail.getCEvaluation());  //total is 17
                RC_detail.add(result);
            }
        }
        else if(major.equals("航管")){
            System.out.println("*"+major+"*"+number+"*"+grade);
            List<RequiredCourseEntityOuterMajorTC> rCourseEntityOMajorTC = rCourseOMajorTCRepository.findByNumberAndGrade(number,grade);
            for (RequiredCourseEntityOuterMajorTC rcDetail : rCourseEntityOMajorTC) {
                RequiredCourseEntity result = new RequiredCourseEntity();
                result.setCNumber(rcDetail.getCNumber());
                result.setCTeacher(rcDetail.getCTeacher());
                result.setCCredit(rcDetail.getCCredit());
                result.setCGrade(rcDetail.getCGrade());
                result.setCName(rcDetail.getCName());
                result.setCCategory(rcDetail.getCCategory());
                result.setCMajor(rcDetail.getCMajor());
                result.setCTime(rcDetail.getCTime());
                result.setCLocation(rcDetail.getCLocation());
                result.setCPeople(rcDetail.getCPeople());
                result.setCObjective(rcDetail.getCObjective());
                result.setCReference(rcDetail.getCReference());
                result.setCPrecourse(rcDetail.getCPrecourse());
                result.setCOutline(rcDetail.getCOutline());
                result.setCTmethod(rcDetail.getCTmethod());
                result.setCSyllabus(rcDetail.getCSyllabus());
                result.setCEvaluation(rcDetail.getCEvaluation());  //total is 17
                RC_detail.add(result);
            }
        }
        else if(major.equals("運輸")){
            System.out.println("*"+major+"*"+number+"*"+grade);
            List<RequiredCourseEntityOuterMajorTS> rCourseEntityOMajorTS = rCourseOMajorTSRepository.findByNumberAndGrade(number,grade);
            for (RequiredCourseEntityOuterMajorTS rcDetail : rCourseEntityOMajorTS) {
                RequiredCourseEntity result = new RequiredCourseEntity();
                result.setCNumber(rcDetail.getCNumber());
                result.setCTeacher(rcDetail.getCTeacher());
                result.setCCredit(rcDetail.getCCredit());
                result.setCGrade(rcDetail.getCGrade());
                result.setCName(rcDetail.getCName());
                result.setCCategory(rcDetail.getCCategory());
                result.setCMajor(rcDetail.getCMajor());
                result.setCTime(rcDetail.getCTime());
                result.setCLocation(rcDetail.getCLocation());
                result.setCPeople(rcDetail.getCPeople());
                result.setCObjective(rcDetail.getCObjective());
                result.setCReference(rcDetail.getCReference());
                result.setCPrecourse(rcDetail.getCPrecourse());
                result.setCOutline(rcDetail.getCOutline());
                result.setCTmethod(rcDetail.getCTmethod());
                result.setCSyllabus(rcDetail.getCSyllabus());
                result.setCEvaluation(rcDetail.getCEvaluation());  //total is 17
                RC_detail.add(result);
            }
        }
        else if(major.equals("輪機")){
            System.out.println("*"+major+"*"+number+"*"+grade);
            List<RequiredCourseEntityOuterMajorTE> rCourseEntityOMajorTE = rCourseOMajorTERepository.findByNumberAndGrade(number,grade);
            for (RequiredCourseEntityOuterMajorTE rcDetail : rCourseEntityOMajorTE) {
                RequiredCourseEntity result = new RequiredCourseEntity();
                result.setCNumber(rcDetail.getCNumber());
                result.setCTeacher(rcDetail.getCTeacher());
                result.setCCredit(rcDetail.getCCredit());
                result.setCGrade(rcDetail.getCGrade());
                result.setCName(rcDetail.getCName());
                result.setCCategory(rcDetail.getCCategory());
                result.setCMajor(rcDetail.getCMajor());
                result.setCTime(rcDetail.getCTime());
                result.setCLocation(rcDetail.getCLocation());
                result.setCPeople(rcDetail.getCPeople());
                result.setCObjective(rcDetail.getCObjective());
                result.setCReference(rcDetail.getCReference());
                result.setCPrecourse(rcDetail.getCPrecourse());
                result.setCOutline(rcDetail.getCOutline());
                result.setCTmethod(rcDetail.getCTmethod());
                result.setCSyllabus(rcDetail.getCSyllabus());
                result.setCEvaluation(rcDetail.getCEvaluation());  //total is 17
                RC_detail.add(result);
            }
        }
        else if(major.equals("食科")){
            System.out.println("*"+major+"*"+number+"*"+grade);
            List<RequiredCourseEntityOuterMajorFS> rCourseEntityOMajorFS = rCourseOMajorFSRepository.findByNumberAndGrade(number,grade);
            for (RequiredCourseEntityOuterMajorFS rcDetail : rCourseEntityOMajorFS) {
                RequiredCourseEntity result = new RequiredCourseEntity();
                result.setCNumber(rcDetail.getCNumber());
                result.setCTeacher(rcDetail.getCTeacher());
                result.setCCredit(rcDetail.getCCredit());
                result.setCGrade(rcDetail.getCGrade());
                result.setCName(rcDetail.getCName());
                result.setCCategory(rcDetail.getCCategory());
                result.setCMajor(rcDetail.getCMajor());
                result.setCTime(rcDetail.getCTime());
                result.setCLocation(rcDetail.getCLocation());
                result.setCPeople(rcDetail.getCPeople());
                result.setCObjective(rcDetail.getCObjective());
                result.setCReference(rcDetail.getCReference());
                result.setCPrecourse(rcDetail.getCPrecourse());
                result.setCOutline(rcDetail.getCOutline());
                result.setCTmethod(rcDetail.getCTmethod());
                result.setCSyllabus(rcDetail.getCSyllabus());
                result.setCEvaluation(rcDetail.getCEvaluation());  //total is 17
                RC_detail.add(result);
            }
        }
        else if(major.equals("養殖")){
            System.out.println("*"+major+"*"+number+"*"+grade);
            List<RequiredCourseEntityOuterMajorDA> rCourseEntityOMajorDA = rCourseOMajorDARepository.findByNumberAndGrade(number,grade);
            for (RequiredCourseEntityOuterMajorDA rcDetail : rCourseEntityOMajorDA) {
                RequiredCourseEntity result = new RequiredCourseEntity();
                result.setCNumber(rcDetail.getCNumber());
                result.setCTeacher(rcDetail.getCTeacher());
                result.setCCredit(rcDetail.getCCredit());
                result.setCGrade(rcDetail.getCGrade());
                result.setCName(rcDetail.getCName());
                result.setCCategory(rcDetail.getCCategory());
                result.setCMajor(rcDetail.getCMajor());
                result.setCTime(rcDetail.getCTime());
                result.setCLocation(rcDetail.getCLocation());
                result.setCPeople(rcDetail.getCPeople());
                result.setCObjective(rcDetail.getCObjective());
                result.setCReference(rcDetail.getCReference());
                result.setCPrecourse(rcDetail.getCPrecourse());
                result.setCOutline(rcDetail.getCOutline());
                result.setCTmethod(rcDetail.getCTmethod());
                result.setCSyllabus(rcDetail.getCSyllabus());
                result.setCEvaluation(rcDetail.getCEvaluation());  //total is 17
                RC_detail.add(result);
            }
        }
        else if(major.equals("生科")){
            System.out.println("*"+major+"*"+number+"*"+grade);
            List<RequiredCourseEntityOuterMajorBT> rCourseEntityOMajorBT = rCourseOMajorBTRepository.findByNumberAndGrade(number,grade);
            for (RequiredCourseEntityOuterMajorBT rcDetail : rCourseEntityOMajorBT) {
                RequiredCourseEntity result = new RequiredCourseEntity();
                result.setCNumber(rcDetail.getCNumber());
                result.setCTeacher(rcDetail.getCTeacher());
                result.setCCredit(rcDetail.getCCredit());
                result.setCGrade(rcDetail.getCGrade());
                result.setCName(rcDetail.getCName());
                result.setCCategory(rcDetail.getCCategory());
                result.setCMajor(rcDetail.getCMajor());
                result.setCTime(rcDetail.getCTime());
                result.setCLocation(rcDetail.getCLocation());
                result.setCPeople(rcDetail.getCPeople());
                result.setCObjective(rcDetail.getCObjective());
                result.setCReference(rcDetail.getCReference());
                result.setCPrecourse(rcDetail.getCPrecourse());
                result.setCOutline(rcDetail.getCOutline());
                result.setCTmethod(rcDetail.getCTmethod());
                result.setCSyllabus(rcDetail.getCSyllabus());
                result.setCEvaluation(rcDetail.getCEvaluation());  //total is 17
                RC_detail.add(result);
            }
        }
        else if(major.equals("環漁")){
            System.out.println("*"+major+"*"+number+"*"+grade);
            List<RequiredCourseEntityOuterMajorRF> rCourseEntityOMajorRF = rCourseOMajorRFRepository.findByNumberAndGrade(number,grade);
            for (RequiredCourseEntityOuterMajorRF rcDetail : rCourseEntityOMajorRF) {
                RequiredCourseEntity result = new RequiredCourseEntity();
                result.setCNumber(rcDetail.getCNumber());
                result.setCTeacher(rcDetail.getCTeacher());
                result.setCCredit(rcDetail.getCCredit());
                result.setCGrade(rcDetail.getCGrade());
                result.setCName(rcDetail.getCName());
                result.setCCategory(rcDetail.getCCategory());
                result.setCMajor(rcDetail.getCMajor());
                result.setCTime(rcDetail.getCTime());
                result.setCLocation(rcDetail.getCLocation());
                result.setCPeople(rcDetail.getCPeople());
                result.setCObjective(rcDetail.getCObjective());
                result.setCReference(rcDetail.getCReference());
                result.setCPrecourse(rcDetail.getCPrecourse());
                result.setCOutline(rcDetail.getCOutline());
                result.setCTmethod(rcDetail.getCTmethod());
                result.setCSyllabus(rcDetail.getCSyllabus());
                result.setCEvaluation(rcDetail.getCEvaluation());  //total is 17
                RC_detail.add(result);
            }
        }
        else if(major.equals("機械")){
            System.out.println("*"+major+"*"+number+"*"+grade);
            List<RequiredCourseEntityOuterMajorME> rCourseEntityOMajorME = rCourseOMajorMERepository.findByNumberAndGrade(number,grade);
            for (RequiredCourseEntityOuterMajorME rcDetail : rCourseEntityOMajorME) {
                RequiredCourseEntity result = new RequiredCourseEntity();
                result.setCNumber(rcDetail.getCNumber());
                result.setCTeacher(rcDetail.getCTeacher());
                result.setCCredit(rcDetail.getCCredit());
                result.setCGrade(rcDetail.getCGrade());
                result.setCName(rcDetail.getCName());
                result.setCCategory(rcDetail.getCCategory());
                result.setCMajor(rcDetail.getCMajor());
                result.setCTime(rcDetail.getCTime());
                result.setCLocation(rcDetail.getCLocation());
                result.setCPeople(rcDetail.getCPeople());
                result.setCObjective(rcDetail.getCObjective());
                result.setCReference(rcDetail.getCReference());
                result.setCPrecourse(rcDetail.getCPrecourse());
                result.setCOutline(rcDetail.getCOutline());
                result.setCTmethod(rcDetail.getCTmethod());
                result.setCSyllabus(rcDetail.getCSyllabus());
                result.setCEvaluation(rcDetail.getCEvaluation());  //total is 17
                RC_detail.add(result);
            }
        }
        else if(major.equals("系工")){
            System.out.println("*"+major+"*"+number+"*"+grade);
            List<RequiredCourseEntityOuterMajorSE> rCourseEntityOMajorSE = rCourseOMajorSERepository.findByNumberAndGrade(number,grade);
            for (RequiredCourseEntityOuterMajorSE rcDetail : rCourseEntityOMajorSE) {
                RequiredCourseEntity result = new RequiredCourseEntity();
                result.setCNumber(rcDetail.getCNumber());
                result.setCTeacher(rcDetail.getCTeacher());
                result.setCCredit(rcDetail.getCCredit());
                result.setCGrade(rcDetail.getCGrade());
                result.setCName(rcDetail.getCName());
                result.setCCategory(rcDetail.getCCategory());
                result.setCMajor(rcDetail.getCMajor());
                result.setCTime(rcDetail.getCTime());
                result.setCLocation(rcDetail.getCLocation());
                result.setCPeople(rcDetail.getCPeople());
                result.setCObjective(rcDetail.getCObjective());
                result.setCReference(rcDetail.getCReference());
                result.setCPrecourse(rcDetail.getCPrecourse());
                result.setCOutline(rcDetail.getCOutline());
                result.setCTmethod(rcDetail.getCTmethod());
                result.setCSyllabus(rcDetail.getCSyllabus());
                result.setCEvaluation(rcDetail.getCEvaluation());  //total is 17
                RC_detail.add(result);
            }
        }
        else if(major.equals("河工")){
            System.out.println("*"+major+"*"+number+"*"+grade);
            List<RequiredCourseEntityOuterMajorRW> rCourseEntityOMajorRW = rCourseOMajorRWRepository.findByNumberAndGrade(number,grade);
            for (RequiredCourseEntityOuterMajorRW rcDetail : rCourseEntityOMajorRW) {
                RequiredCourseEntity result = new RequiredCourseEntity();
                result.setCNumber(rcDetail.getCNumber());
                result.setCTeacher(rcDetail.getCTeacher());
                result.setCCredit(rcDetail.getCCredit());
                result.setCGrade(rcDetail.getCGrade());
                result.setCName(rcDetail.getCName());
                result.setCCategory(rcDetail.getCCategory());
                result.setCMajor(rcDetail.getCMajor());
                result.setCTime(rcDetail.getCTime());
                result.setCLocation(rcDetail.getCLocation());
                result.setCPeople(rcDetail.getCPeople());
                result.setCObjective(rcDetail.getCObjective());
                result.setCReference(rcDetail.getCReference());
                result.setCPrecourse(rcDetail.getCPrecourse());
                result.setCOutline(rcDetail.getCOutline());
                result.setCTmethod(rcDetail.getCTmethod());
                result.setCSyllabus(rcDetail.getCSyllabus());
                result.setCEvaluation(rcDetail.getCEvaluation());  //total is 17
                RC_detail.add(result);
            }
        }
        else if(major.equals("電機")){
            System.out.println("*"+major+"*"+number+"*"+grade);
            List<RequiredCourseEntityOuterMajorEE> rCourseEntityOMajorEE = rCourseOMajorEERepository.findByNumberAndGrade(number,grade);
            for (RequiredCourseEntityOuterMajorEE rcDetail : rCourseEntityOMajorEE) {
                RequiredCourseEntity result = new RequiredCourseEntity();
                result.setCNumber(rcDetail.getCNumber());
                result.setCTeacher(rcDetail.getCTeacher());
                result.setCCredit(rcDetail.getCCredit());
                result.setCGrade(rcDetail.getCGrade());
                result.setCName(rcDetail.getCName());
                result.setCCategory(rcDetail.getCCategory());
                result.setCMajor(rcDetail.getCMajor());
                result.setCTime(rcDetail.getCTime());
                result.setCLocation(rcDetail.getCLocation());
                result.setCPeople(rcDetail.getCPeople());
                result.setCObjective(rcDetail.getCObjective());
                result.setCReference(rcDetail.getCReference());
                result.setCPrecourse(rcDetail.getCPrecourse());
                result.setCOutline(rcDetail.getCOutline());
                result.setCTmethod(rcDetail.getCTmethod());
                result.setCSyllabus(rcDetail.getCSyllabus());
                result.setCEvaluation(rcDetail.getCEvaluation());  //total is 17
                RC_detail.add(result);
            }
        }
        else if(major.equals("通訊")){
            System.out.println("*"+major+"*"+number+"*"+grade);
            List<RequiredCourseEntityOuterMajorCE> rCourseEntityOMajorCE = rCourseOMajorCERepository.findByNumberAndGrade(number,grade);
            for (RequiredCourseEntityOuterMajorCE rcDetail : rCourseEntityOMajorCE) {
                RequiredCourseEntity result = new RequiredCourseEntity();
                result.setCNumber(rcDetail.getCNumber());
                result.setCTeacher(rcDetail.getCTeacher());
                result.setCCredit(rcDetail.getCCredit());
                result.setCGrade(rcDetail.getCGrade());
                result.setCName(rcDetail.getCName());
                result.setCCategory(rcDetail.getCCategory());
                result.setCMajor(rcDetail.getCMajor());
                result.setCTime(rcDetail.getCTime());
                result.setCLocation(rcDetail.getCLocation());
                result.setCPeople(rcDetail.getCPeople());
                result.setCObjective(rcDetail.getCObjective());
                result.setCReference(rcDetail.getCReference());
                result.setCPrecourse(rcDetail.getCPrecourse());
                result.setCOutline(rcDetail.getCOutline());
                result.setCTmethod(rcDetail.getCTmethod());
                result.setCSyllabus(rcDetail.getCSyllabus());
                result.setCEvaluation(rcDetail.getCEvaluation());  //total is 17
                RC_detail.add(result);
            }
        }
        else if(major.equals("光電")){
            System.out.println("*"+major+"*"+number+"*"+grade);
            List<RequiredCourseEntityOuterMajorPE> rCourseEntityOMajorPE = rCourseOMajorPERepository.findByNumberAndGrade(number,grade);
            for (RequiredCourseEntityOuterMajorPE rcDetail : rCourseEntityOMajorPE) {
                RequiredCourseEntity result = new RequiredCourseEntity();
                result.setCNumber(rcDetail.getCNumber());
                result.setCTeacher(rcDetail.getCTeacher());
                result.setCCredit(rcDetail.getCCredit());
                result.setCGrade(rcDetail.getCGrade());
                result.setCName(rcDetail.getCName());
                result.setCCategory(rcDetail.getCCategory());
                result.setCMajor(rcDetail.getCMajor());
                result.setCTime(rcDetail.getCTime());
                result.setCLocation(rcDetail.getCLocation());
                result.setCPeople(rcDetail.getCPeople());
                result.setCObjective(rcDetail.getCObjective());
                result.setCReference(rcDetail.getCReference());
                result.setCPrecourse(rcDetail.getCPrecourse());
                result.setCOutline(rcDetail.getCOutline());
                result.setCTmethod(rcDetail.getCTmethod());
                result.setCSyllabus(rcDetail.getCSyllabus());
                result.setCEvaluation(rcDetail.getCEvaluation());  //total is 17
                RC_detail.add(result);
            }
        }
        return RC_detail;
    }

    @PostMapping("/course_search")
    public List<RequiredCourseEntity> course_search( @RequestParam(value = "major") String major, @RequestParam(value = "category") String category,@RequestParam(value = "grade") String grade)throws TesseractException, IOException, InterruptedException  {

        System.out.println("/course_search");
        String studentID = account;
        String password = pwd;

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
        if(major.equals("資工")){
            //List<RequiredCourseEntityOuterMajor> RC_result = new ArrayList<>();
            System.out.println("*"+major+"*"+category+"*"+grade);
            List<RequiredCourseEntityMajorCSE> rCourseEntityMajorCSE = rCourseMajorCSERepository.findByCategoryAndGrade(category,grade);
            if (rCourseEntityMajorCSE != null && !rCourseEntityMajorCSE.isEmpty()) {
                System.out.println("find it MajorCSE");
                for (RequiredCourseEntityMajorCSE mCSE : rCourseEntityMajorCSE) {
                    RequiredCourseEntity result = new RequiredCourseEntity();
                    result.setCNumber(mCSE.getCNumber());
                    result.setCTeacher(mCSE.getCTeacher());
                    result.setCCredit(mCSE.getCCredit());
                    result.setCGrade(mCSE.getCGrade());
                    result.setCName(mCSE.getCName());
                    result.setCCategory(mCSE.getCCategory());
                    result.setCMajor(mCSE.getCMajor());
                    RC_result.add(result);
                }
            } else {
                crawler.CrawlerHandle(studentID, password);

                RC_result = crawler.findRCourse(major, category, grade);

                for (RequiredCourseEntity course : RC_result) {
                    RequiredCourseEntityMajorCSE RCourseEntityMajorCSE = new RequiredCourseEntityMajorCSE();
                    RCourseEntityMajorCSE.setCNumber(course.getCNumber());
                    RCourseEntityMajorCSE.setCTeacher(course.getCTeacher());
                    RCourseEntityMajorCSE.setCCredit(course.getCCredit());
                    RCourseEntityMajorCSE.setCGrade(course.getCGrade());
                    RCourseEntityMajorCSE.setCName(course.getCName());
                    RCourseEntityMajorCSE.setCCategory(course.getCCategory());
                    RCourseEntityMajorCSE.setCMajor(major);
                    RCourseEntityMajorCSE.setCTime(course.getCTime());
                    RCourseEntityMajorCSE.setCLocation(course.getCLocation());
                    RCourseEntityMajorCSE.setCPeople(course.getCPeople());
                    RCourseEntityMajorCSE.setCReference(course.getCReference());
                    RCourseEntityMajorCSE.setCObjective(course.getCObjective());
                    RCourseEntityMajorCSE.setCPrecourse(course.getCPrecourse());
                    RCourseEntityMajorCSE.setCOutline(course.getCOutline());
                    RCourseEntityMajorCSE.setCTmethod(course.getCTmethod());
                    RCourseEntityMajorCSE.setCSyllabus(course.getCSyllabus());
                    RCourseEntityMajorCSE.setCEvaluation(course.getCEvaluation()); //total is 17

                    rCourseMajorCSERepository.save(RCourseEntityMajorCSE);
                }
                System.out.println("Total : " + RC_result.size());
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
                    RCourseEntityOuterMajorMS.setCMajor(major);
                    RCourseEntityOuterMajorMS.setCTime(course.getCTime());
                    RCourseEntityOuterMajorMS.setCLocation(course.getCLocation());
                    RCourseEntityOuterMajorMS.setCPeople(course.getCPeople());
                    RCourseEntityOuterMajorMS.setCObjective(course.getCObjective());
                    RCourseEntityOuterMajorMS.setCPrecourse(course.getCPrecourse());
                    RCourseEntityOuterMajorMS.setCOutline(course.getCOutline());
                    RCourseEntityOuterMajorMS.setCTmethod(course.getCTmethod());
                    RCourseEntityOuterMajorMS.setCSyllabus(course.getCSyllabus());
                    RCourseEntityOuterMajorMS.setCEvaluation(course.getCEvaluation());

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
                    RCourseEntityOuterMajorTC.setCMajor(major);
                    RCourseEntityOuterMajorTC.setCTime(course.getCTime());
                    RCourseEntityOuterMajorTC.setCLocation(course.getCLocation());
                    RCourseEntityOuterMajorTC.setCPeople(course.getCPeople());
                    RCourseEntityOuterMajorTC.setCObjective(course.getCObjective());
                    RCourseEntityOuterMajorTC.setCPrecourse(course.getCPrecourse());
                    RCourseEntityOuterMajorTC.setCOutline(course.getCOutline());
                    RCourseEntityOuterMajorTC.setCTmethod(course.getCTmethod());
                    RCourseEntityOuterMajorTC.setCSyllabus(course.getCSyllabus());
                    RCourseEntityOuterMajorTC.setCEvaluation(course.getCEvaluation());

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
                    RCourseEntityOuterMajorTS.setCMajor(major);
                    RCourseEntityOuterMajorTS.setCTime(course.getCTime());
                    RCourseEntityOuterMajorTS.setCLocation(course.getCLocation());
                    RCourseEntityOuterMajorTS.setCPeople(course.getCPeople());
                    RCourseEntityOuterMajorTS.setCObjective(course.getCObjective());
                    RCourseEntityOuterMajorTS.setCPrecourse(course.getCPrecourse());
                    RCourseEntityOuterMajorTS.setCOutline(course.getCOutline());
                    RCourseEntityOuterMajorTS.setCTmethod(course.getCTmethod());
                    RCourseEntityOuterMajorTS.setCSyllabus(course.getCSyllabus());
                    RCourseEntityOuterMajorTS.setCEvaluation(course.getCEvaluation());

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
                    RCourseEntityOuterMajorTE.setCMajor(major);
                    RCourseEntityOuterMajorTE.setCTime(course.getCTime());
                    RCourseEntityOuterMajorTE.setCLocation(course.getCLocation());
                    RCourseEntityOuterMajorTE.setCPeople(course.getCPeople());
                    RCourseEntityOuterMajorTE.setCObjective(course.getCObjective());
                    RCourseEntityOuterMajorTE.setCPrecourse(course.getCPrecourse());
                    RCourseEntityOuterMajorTE.setCOutline(course.getCOutline());
                    RCourseEntityOuterMajorTE.setCTmethod(course.getCTmethod());
                    RCourseEntityOuterMajorTE.setCSyllabus(course.getCSyllabus());
                    RCourseEntityOuterMajorTE.setCEvaluation(course.getCEvaluation());

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
                    RCourseEntityOuterMajorFS.setCMajor(major);
                    RCourseEntityOuterMajorFS.setCTime(course.getCTime());
                    RCourseEntityOuterMajorFS.setCLocation(course.getCLocation());
                    RCourseEntityOuterMajorFS.setCPeople(course.getCPeople());
                    RCourseEntityOuterMajorFS.setCObjective(course.getCObjective());
                    RCourseEntityOuterMajorFS.setCPrecourse(course.getCPrecourse());
                    RCourseEntityOuterMajorFS.setCOutline(course.getCOutline());
                    RCourseEntityOuterMajorFS.setCTmethod(course.getCTmethod());
                    RCourseEntityOuterMajorFS.setCSyllabus(course.getCSyllabus());
                    RCourseEntityOuterMajorFS.setCEvaluation(course.getCEvaluation());

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
                    RCourseEntityOuterMajorDA.setCMajor(major);
                    RCourseEntityOuterMajorDA.setCTime(course.getCTime());
                    RCourseEntityOuterMajorDA.setCLocation(course.getCLocation());
                    RCourseEntityOuterMajorDA.setCPeople(course.getCPeople());
                    RCourseEntityOuterMajorDA.setCObjective(course.getCObjective());
                    RCourseEntityOuterMajorDA.setCPrecourse(course.getCPrecourse());
                    RCourseEntityOuterMajorDA.setCOutline(course.getCOutline());
                    RCourseEntityOuterMajorDA.setCTmethod(course.getCTmethod());
                    RCourseEntityOuterMajorDA.setCSyllabus(course.getCSyllabus());
                    RCourseEntityOuterMajorDA.setCEvaluation(course.getCEvaluation());

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
                    RCourseEntityOuterMajorBT.setCMajor(major);
                    RCourseEntityOuterMajorBT.setCTime(course.getCTime());
                    RCourseEntityOuterMajorBT.setCLocation(course.getCLocation());
                    RCourseEntityOuterMajorBT.setCPeople(course.getCPeople());
                    RCourseEntityOuterMajorBT.setCObjective(course.getCObjective());
                    RCourseEntityOuterMajorBT.setCPrecourse(course.getCPrecourse());
                    RCourseEntityOuterMajorBT.setCOutline(course.getCOutline());
                    RCourseEntityOuterMajorBT.setCTmethod(course.getCTmethod());
                    RCourseEntityOuterMajorBT.setCSyllabus(course.getCSyllabus());
                    RCourseEntityOuterMajorBT.setCEvaluation(course.getCEvaluation());

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
                    RCourseEntityOuterMajorRF.setCMajor(major);
                    RCourseEntityOuterMajorRF.setCTime(course.getCTime());
                    RCourseEntityOuterMajorRF.setCLocation(course.getCLocation());
                    RCourseEntityOuterMajorRF.setCPeople(course.getCPeople());
                    RCourseEntityOuterMajorRF.setCObjective(course.getCObjective());
                    RCourseEntityOuterMajorRF.setCPrecourse(course.getCPrecourse());
                    RCourseEntityOuterMajorRF.setCOutline(course.getCOutline());
                    RCourseEntityOuterMajorRF.setCTmethod(course.getCTmethod());
                    RCourseEntityOuterMajorRF.setCSyllabus(course.getCSyllabus());
                    RCourseEntityOuterMajorRF.setCEvaluation(course.getCEvaluation());

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
                    RCourseEntityOuterMajorME.setCMajor(major);
                    RCourseEntityOuterMajorME.setCTime(course.getCTime());
                    RCourseEntityOuterMajorME.setCLocation(course.getCLocation());
                    RCourseEntityOuterMajorME.setCPeople(course.getCPeople());
                    RCourseEntityOuterMajorME.setCObjective(course.getCObjective());
                    RCourseEntityOuterMajorME.setCPrecourse(course.getCPrecourse());
                    RCourseEntityOuterMajorME.setCOutline(course.getCOutline());
                    RCourseEntityOuterMajorME.setCTmethod(course.getCTmethod());
                    RCourseEntityOuterMajorME.setCSyllabus(course.getCSyllabus());
                    RCourseEntityOuterMajorME.setCEvaluation(course.getCEvaluation());

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
                    RCourseEntityOuterMajorSE.setCMajor(major);
                    RCourseEntityOuterMajorSE.setCTime(course.getCTime());
                    RCourseEntityOuterMajorSE.setCLocation(course.getCLocation());
                    RCourseEntityOuterMajorSE.setCPeople(course.getCPeople());
                    RCourseEntityOuterMajorSE.setCObjective(course.getCObjective());
                    RCourseEntityOuterMajorSE.setCPrecourse(course.getCPrecourse());
                    RCourseEntityOuterMajorSE.setCOutline(course.getCOutline());
                    RCourseEntityOuterMajorSE.setCTmethod(course.getCTmethod());
                    RCourseEntityOuterMajorSE.setCSyllabus(course.getCSyllabus());
                    RCourseEntityOuterMajorSE.setCEvaluation(course.getCEvaluation());


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
                    RCourseEntityOuterMajorRW.setCMajor(major);
                    RCourseEntityOuterMajorRW.setCTime(course.getCTime());
                    RCourseEntityOuterMajorRW.setCLocation(course.getCLocation());
                    RCourseEntityOuterMajorRW.setCPeople(course.getCPeople());
                    RCourseEntityOuterMajorRW.setCObjective(course.getCObjective());
                    RCourseEntityOuterMajorRW.setCPrecourse(course.getCPrecourse());
                    RCourseEntityOuterMajorRW.setCOutline(course.getCOutline());
                    RCourseEntityOuterMajorRW.setCTmethod(course.getCTmethod());
                    RCourseEntityOuterMajorRW.setCSyllabus(course.getCSyllabus());
                    RCourseEntityOuterMajorRW.setCEvaluation(course.getCEvaluation());

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
                    RCourseEntityOuterMajorEE.setCMajor(major);
                    RCourseEntityOuterMajorEE.setCTime(course.getCTime());
                    RCourseEntityOuterMajorEE.setCLocation(course.getCLocation());
                    RCourseEntityOuterMajorEE.setCPeople(course.getCPeople());
                    RCourseEntityOuterMajorEE.setCObjective(course.getCObjective());
                    RCourseEntityOuterMajorEE.setCPrecourse(course.getCPrecourse());
                    RCourseEntityOuterMajorEE.setCOutline(course.getCOutline());
                    RCourseEntityOuterMajorEE.setCTmethod(course.getCTmethod());
                    RCourseEntityOuterMajorEE.setCSyllabus(course.getCSyllabus());
                    RCourseEntityOuterMajorEE.setCEvaluation(course.getCEvaluation());

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
                    RCourseEntityOuterMajorCE.setCMajor(major);
                    RCourseEntityOuterMajorCE.setCTime(course.getCTime());
                    RCourseEntityOuterMajorCE.setCLocation(course.getCLocation());
                    RCourseEntityOuterMajorCE.setCPeople(course.getCPeople());
                    RCourseEntityOuterMajorCE.setCObjective(course.getCObjective());
                    RCourseEntityOuterMajorCE.setCPrecourse(course.getCPrecourse());
                    RCourseEntityOuterMajorCE.setCOutline(course.getCOutline());
                    RCourseEntityOuterMajorCE.setCTmethod(course.getCTmethod());
                    RCourseEntityOuterMajorCE.setCSyllabus(course.getCSyllabus());
                    RCourseEntityOuterMajorCE.setCEvaluation(course.getCEvaluation());

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
                    RCourseEntityOuterMajorPE.setCMajor(major);
                    RCourseEntityOuterMajorPE.setCTime(course.getCTime());
                    RCourseEntityOuterMajorPE.setCLocation(course.getCLocation());
                    RCourseEntityOuterMajorPE.setCPeople(course.getCPeople());
                    RCourseEntityOuterMajorPE.setCObjective(course.getCObjective());
                    RCourseEntityOuterMajorPE.setCPrecourse(course.getCPrecourse());
                    RCourseEntityOuterMajorPE.setCOutline(course.getCOutline());
                    RCourseEntityOuterMajorPE.setCTmethod(course.getCTmethod());
                    RCourseEntityOuterMajorPE.setCSyllabus(course.getCSyllabus());
                    RCourseEntityOuterMajorPE.setCEvaluation(course.getCEvaluation());

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

    @PutMapping("/change_post_status") //both house and change course posts
    public ResponseEntity<?> changePostStatus(@RequestBody Map<String, String> requestData){
        if(requestData.get("postId").startsWith("H")){
            HouseEntity house = houseRepository.findByPostId(requestData.get("postId"));
            if(!Objects.equals(house.getStudentID(), requestData.get("studentID")))return ResponseEntity.badRequest().body("Invalid request : not the author");//400
            house.setDecided(Integer.parseInt(house.getPeople()));
            house.setStatus("已租");
            houseRepository.save(house);
            return ResponseEntity.ok("Success");
        }
        //requestData.get("postId").startsWith("C")
        else {
            ChangeCourseEntity changeCourse = changeCourseRepository.findByPostId(requestData.get("postId"));
            if (!Objects.equals(changeCourse.getStudentID(), requestData.get("studentID")))
                return ResponseEntity.badRequest().body("Invalid request : not the author");//400
            List<String> nickName = new ArrayList<>();
            for (String id : changeCourse.getContact()) {
                nickName.add(basicRepository.findByStudentID(id).getNickname());
            }
            return ResponseEntity.ok(nickName);
        }
    }
    @PostMapping("/change_post_decided")
    public ResponseEntity<String> changePostDecided(@RequestBody Map<String, String> requestData)
    {
        System.out.println("/change_post_decided : "+requestData.get("nickname"));
        ChangeCourseEntity changeCourse = changeCourseRepository.findByPostId(requestData.get("postId"));
        changeCourse.setDecided(basicRepository.findByNickname(requestData.get("nickname")).getStudentID());
        changeCourse.setStatus("已換");
        changeCourseRepository.save(changeCourse);
        for (String t : changeCourse.getTime()){
            ChangeCourseHaveEntity have = changeCourseHaveRepository.findByTime(t);
            have.setHave(have.getHave()-1);
            changeCourseHaveRepository.save(have);
        }
        return ResponseEntity.ok("Success");
    }
    public static Map<Integer, List<TimeTableEntity.Pre_Info>> groupPreInfoByWeekday(List<TimeTableEntity.Pre_Info> preInfoList) {
        Map<Integer, List<TimeTableEntity.Pre_Info>> groupedByWeekday = new HashMap<>();
        int lastweekday;
        for (TimeTableEntity.Pre_Info preInfo : preInfoList) {
            lastweekday = 0;
            for (String time : preInfo.getP_time()) {
                int weekday = Integer.parseInt(time.substring(0, 1));
                groupedByWeekday.putIfAbsent(weekday, new ArrayList<>());
                if(lastweekday!=weekday)groupedByWeekday.get(weekday).add(preInfo);
                lastweekday = weekday;
            }
        }
        return groupedByWeekday;
    }
    @PostMapping("/my_pre_curriculum")
    public ResponseEntity<?> myPreCurriculumSearch(@RequestBody Map<String, String> requestData, @RequestHeader("Authorization") String au){
        System.out.println("/my_pre_curriculum");
        JwtToken jwtToken = new JwtToken();
        try {
            jwtToken.validateToken(au, requestData.get("studentID"));
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        TimeTableEntity timeTable = timeTableRepository.findByStudentID(requestData.get("studentID"));
        if(timeTable==null)return null;
        List<TimeTableEntity.Pre_Info> preInfoList = timeTable.getPre_info();
        return ResponseEntity.ok(groupPreInfoByWeekday(preInfoList));
    }

    @PostMapping("/pre_curriculum")
    public ResponseEntity<String> preCurriculum(@RequestBody TimeTableEntity t, @RequestHeader("Authorization") String au){
        System.out.println("/pre_curriculum : 加入預選課程");
        JwtToken jwtToken = new JwtToken();
        try {
            jwtToken.validateToken(au, t.getStudentID());
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        TimeTableEntity timeTable = timeTableRepository.findByStudentID(t.getStudentID());
        TimeTableEntity.Pre_Info pre_info = t.getPre_info().get(0);

        if (timeTable == null) {
            timeTable = new TimeTableEntity();
            timeTable.setStudentID(t.getStudentID());
            timeTable.setPre_info(pre_info);
        }
        else{timeTable.setPre_info(pre_info);}
        timeTableRepository.save(timeTable);
        return ResponseEntity.ok("Success"); // 200
    }

    @DeleteMapping("/cancel_pre_curriculum")
    public ResponseEntity<String> cancelPreCurriculum(@RequestParam("studentID") String studentID, @RequestParam("p_classNum") String p_classNum, @RequestHeader("Authorization") String au){
        System.out.println("/cancel_pre_curriculum");
        JwtToken jwtToken = new JwtToken();
        try {
            jwtToken.validateToken(au, studentID);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
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
        if(deleted)return ResponseEntity.ok("Success"); //200
        else return ResponseEntity.badRequest().body("Invalid request : Class not found"); //400
    }

    @GetMapping("/pre_curriculum_search")
    public List<?> preCurriculumSearch(@RequestParam("name") String name, @RequestParam("category") String category){
        List<CourseDTO> courseDTOList = new ArrayList<>();
        if(Objects.equals(category, "general")||Objects.equals(category, "")){
            List<GeneralCourseEntity> generalCourseEntityList = generalRepository.findByNameContaining(name);
            for(GeneralCourseEntity g : generalCourseEntityList){
                CourseDTO courseDTO = new CourseDTO();
                courseDTO.setName(g.getName());
                courseDTO.setCategory(g.getCategory());
                courseDTO.setClassNum(g.getClassNum());
                courseDTO.setTeacher(g.getTeacher());
                courseDTO.setTime(g.getTime().split(","));
                courseDTO.setClassroom(g.getClassroom());
                courseDTO.setTarget(g.getTarget());
                courseDTO.setEvaluation(g.getEvaluation());
                courseDTO.setSyllabus(g.getSyllabus());
                courseDTOList.add(courseDTO);
            }
        }
        if(Objects.equals(category, "PE")||Objects.equals(category, "")){
            List<PECourseEntity> peCourseEntityList = peCourseRepository.findByNameContaining(name);
            for(PECourseEntity pe : peCourseEntityList){
                CourseDTO courseDTO = new CourseDTO();
                courseDTO.setName(pe.getName());
                courseDTO.setClassNum(pe.getClassNum());
                courseDTO.setCategory(pe.getCategory());
                courseDTO.setTeacher(pe.getTeacher());
                courseDTO.setTime(pe.getTime());
                courseDTO.setClassroom(pe.getClassroom());
                courseDTO.setTarget(pe.getTarget());
                courseDTO.setEvaluation(pe.getEvaluation());
                courseDTO.setSyllabus(pe.getSyllabus());
                courseDTOList.add(courseDTO);

            }
        }
        if(Objects.equals(category, "english")||Objects.equals(category, "")){
            List<EnglishCourseEntity> englishCourseEntityList = englishCourseRepository.findByNameContaining(name);
            for(EnglishCourseEntity en : englishCourseEntityList){
                CourseDTO courseDTO = new CourseDTO();
                courseDTO.setName(en.getName());
                courseDTO.setClassNum(en.getClassNum());
                courseDTO.setCategory(en.getCategory());
                courseDTO.setTeacher(en.getTeacher());
                courseDTO.setTime(en.getTime());
                courseDTO.setClassroom(en.getClassroom());
                courseDTO.setTarget(en.getTarget());
                courseDTO.setEvaluation(en.getEvaluation());
                courseDTO.setSyllabus(en.getSyllabus());
                courseDTOList.add(courseDTO);
            }
        }
        if(Objects.equals(category, "foreign_language")||Objects.equals(category, "")){
            List<ForeignLanguageEntity> foreignLanguageEntityList = foreignLanguageCourseRepository.findByNameContaining(name);
            for(ForeignLanguageEntity f : foreignLanguageEntityList){
                CourseDTO courseDTO = new CourseDTO();
                courseDTO.setName(f.getName());
                courseDTO.setClassNum(f.getClassNum());
                courseDTO.setCategory(f.getCategory());
                courseDTO.setTeacher(f.getTeacher());
                courseDTO.setTime(f.getTime());
                courseDTO.setClassroom(f.getClassroom());
                courseDTO.setTarget(f.getTarget());
                courseDTO.setEvaluation(f.getEvaluation());
                courseDTO.setSyllabus(f.getSyllabus());
                courseDTOList.add(courseDTO);
            }
        }
        return courseDTOList;
    }
    @PostMapping("/general_education")
    public List<GeneralCourseEntity> generalEducation(@RequestParam("field") String field) throws InterruptedException, TesseractException, IOException {
        if(generalRepository.count() == 0){
            List<GeneralCourseEntity> gcList = getGeneralCourses();
            for(GeneralCourseEntity gc : gcList){
                generalRepository.save(gc);
            }
        }
        List<GeneralCourseEntity> result = generalRepository.findBySubfield(field);
        return result;
    }

    private static List<GeneralCourseEntity> getGeneralCourses() throws InterruptedException, TesseractException, IOException {
        List<GeneralCourseEntity> result = crawler.getAllGeneralClass();
        return result;
    }
    @GetMapping("/PECoursesData")
    public void getPECourse(@RequestParam("studentID") String studentID, @RequestParam("password") String password) throws InterruptedException, TesseractException, IOException {
        crawler.CrawlerHandle(studentID, password);
        List<PECourseEntity> peCourseEntityList = crawler.getPE();
        System.out.println(peCourseEntityList.size());
        for(PECourseEntity pe : peCourseEntityList){
            System.out.println(pe.getName());
            peCourseRepository.save(pe);
        }
        System.out.println("done");
    }
    @GetMapping("/foreign_language_courses_data")
    public void foreignLanguageCoursesData(@RequestParam("studentID") String studentID, @RequestParam("password") String password) throws InterruptedException, TesseractException, IOException {
        crawler.CrawlerHandle(studentID, password);
        List<ForeignLanguageEntity> fCourseEntityList = crawler.getForeignLanguageClass();
        System.out.println(fCourseEntityList.size());
        for(ForeignLanguageEntity f : fCourseEntityList){
            System.out.println(f.getName());
            foreignLanguageCourseRepository.save(f);
        }
        System.out.println("done");
    }
    @GetMapping("/english_courses_data")
    public void englishCoursesData(@RequestParam("studentID") String studentID, @RequestParam("password") String password) throws InterruptedException, TesseractException, IOException {
        crawler.CrawlerHandle(studentID, password);
        List<EnglishCourseEntity> eCourseEntityList = crawler.getEnglishClass();
        System.out.println(eCourseEntityList.size());
        for(EnglishCourseEntity e : eCourseEntityList){
            System.out.println(e.getName());
            englishCourseRepository.save(e);
        }
        System.out.println("done");
    }

    @PostMapping("/loadChatRecord")
    public List<ChatroomRecordEntity> loadChatRecord(@RequestParam(value = "where") String where){
        List<ChatroomRecordEntity> load_chat = new ArrayList<>();
        List<ChatroomRecordEntity> chatRecord = chatroomRecordRepository.findByRoom(where);
        for(ChatroomRecordEntity cRecord: chatRecord){
            ChatroomRecordEntity record = new ChatroomRecordEntity();
            record.setfrom(cRecord.getfrom());
            System.out.println(record.getfrom());
            record.settext(cRecord.gettext());
            record.setatWhere(cRecord.getatWhere());
            load_chat.add(record);
        }
        System.out.println("Loading is finished! ->(ChatRoomRecord)");
        return load_chat;
    }

    @PostMapping("/pickRoomApi")
    public ChatroomApiEntity pickRoomApi(@RequestParam(value = "first") String first, @RequestParam(value = "second") String second, @RequestParam(value = "postId") String postId){

        if(postId.startsWith("C")){
            ChangeCourseEntity changeCourse = changeCourseRepository.findByPostId(postId);
            changeCourse.setContact(first);
            changeCourseRepository.save(changeCourse);
        }
        ChatroomApiEntity Api = new ChatroomApiEntity();
        ChatroomApiEntity chatRoomApi1 = chatRoomApiRepository.findByStudentID(first,second);
        if (chatRoomApi1 != null && chatRoomApi1.getRoomApi() != null) {
            Api.setRoomApi(chatRoomApi1.getRoomApi());
            Api.setFirstStudentID(chatRoomApi1.getFirstStudentID());
            Api.setSecondStudentID(chatRoomApi1.getSecondStudentID());
        }
        else {
            ChatroomApiEntity chatRoomApi2 = chatRoomApiRepository.findByStudentID(second,first);
            if (chatRoomApi2 != null && chatRoomApi2.getRoomApi() != null) {
                Api.setRoomApi(chatRoomApi2.getRoomApi());
                Api.setFirstStudentID(chatRoomApi2.getFirstStudentID());
                Api.setSecondStudentID(chatRoomApi2.getSecondStudentID());
            }
            else {
                Random random = new Random();
                int min = 1;
                int max = 100;
                int randomNumber = random.nextInt(max - min + 1) + min;
                int intValue1 = Integer.parseInt(first, 16);
                int intValue2 = Integer.parseInt(second, 16);
                int intTotal = intValue1 + intValue2 +randomNumber;
                String randomHex = Integer.toHexString(intTotal);
                while (true){
                    ChatroomApiEntity roomApi = chatRoomApiRepository.findByRoomApi(randomHex);
                    if (roomApi != null && roomApi.getRoomApi() != null){
                        int randomTemp = random.nextInt(max - min + 1) + min;
                        int intTemp = intValue1 + intValue2 +randomTemp;
                        randomHex = Integer.toHexString(intTemp);
                    }
                    else{
                        break;
                    } 
                }
                Api.setFirstStudentID(first);
                Api.setSecondStudentID(second);
                Api.setRoomApi(randomHex);
                chatRoomApiRepository.save(Api);

                System.out.println("16 进制数: " + randomHex);
            }
        }
        return Api;
    }
}
