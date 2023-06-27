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

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @PostMapping("/remained_credits")
    public RemainCredit postRemainCredits (@RequestBody FinishedCourseList finished)throws TesseractException, IOException, InterruptedException{
        ArrayList<FinishedCourse> finishedCourse = new ArrayList<FinishedCourse>();
        System.out.println("*********student ID: " + finished.getStudentID());
        if(fRepository.existsByStudentID(finished.getStudentID())){
            System.out.println("found.");
            finished = fRepository.findByStudentID(finished.getStudentID());
        }
//       finishedCourse = crawler.getFinishedCredict();
//       finished.setFinishedCourses(finishedCourse);
//       fRepository.save(finished);
        RemainCredit remainCredit = remainedService.computeCredit(finished.getStudentID());
        return remainCredit;
    }

    @GetMapping("/rent_load") //list all house posts
    public List<HouseDTO> rentLoad(){
        List<HouseEntity> housePostList = houseRepository.findAll();
        List<HouseDTO> SimpleHousePostList = new ArrayList<>();
        for (HouseEntity post : housePostList) {
            HouseDTO dto = new HouseDTO(post.getPostId(), post.getName(), post.getTitle());
            SimpleHousePostList.add(dto);
        }
        return SimpleHousePostList;
    }

    @PostMapping("/rent_full_post") //get entire post
    public HouseEntity rentFullPost(@RequestBody HouseEntity houseEntity){
        return houseRepository.findByPostId(houseEntity.getPostId());
    }

    @DeleteMapping("/rent_post_delete")
    public ResponseEntity<String> rentPostDelete(@RequestParam("studentID") String studentID, @RequestParam("postId") String postId){
        if(houseRepository.deleteByPostId(postId) !=null){
            //200
            return ResponseEntity.ok("Success");
        }
        else return (ResponseEntity<String>) ResponseEntity.noContent(); //204

    }

    @PutMapping("/rent_post_modify")
    public ResponseEntity<String> rentPostModify(@RequestBody HouseEntity houseEntity){
        if(houseRepository.save(houseEntity) !=null){
            //200
            return ResponseEntity.ok("Success");
        }
        else return (ResponseEntity<String>) ResponseEntity.noContent(); //204
    }

    @GetMapping("/rent_search")
    public List<HouseDTO> rentSearch(@RequestParam(value = "area", required = false) String area,
                                     @RequestParam(value = "gender", required = false) String gender,
                                     @RequestParam(value = "people", required = false) String people,
                                     @RequestParam(value = "style", required = false) String style,
                                     @RequestParam(value = "car", required = false) String car){
        List<HouseDTO> resultList = new ArrayList<>();
        for(HouseEntity house : houseRepository.findAll()){
            if ((Objects.equals(area, "") || house.getArea().equals(area))
                    && (Objects.equals(gender, "") || house.getGender().equals(gender))
                    && (Objects.equals(people, "") || house.getPeople().equals(people))
                    && (Objects.equals(style, "") || house.getStyle().contains(style))
                    && (Objects.equals(car, "") || house.getCar().equals(car))) {
                HouseDTO result = new HouseDTO(house.getPostId(), house.getName(), house.getTitle());
                resultList.add(result);
            }
        }
        return resultList;
    }

    @GetMapping("/curriculum_search")
    public List<TimeTableEntity.Info> curriculumSearch(@RequestParam("studentID") String studentID) throws TesseractException, IOException, InterruptedException {
        TimeTableEntity timeTable = timeTableRepository.findByStudentID(studentID);
        if(timeTable!=null){
            return timeTable.getInfo();
        }
        else{
            String password = basicRepository.findByStudentID(studentID).getPassword();
            password = aesEncryptionDecryption.decrypt(password, secretKey);
            crawler.CrawlerHandle(studentID,password);
            List<TimeTableEntity.Info> myClassList = crawler.getMyClass(studentID,password);
            TimeTableEntity table = new TimeTableEntity();
            table.setStudentID(studentID);
            for(TimeTableEntity.Info i : myClassList){
                System.out.println(i.getName());
                table.setInfo(i);
            }
            timeTableRepository.save(table);
            return myClassList;
        }
    }

    @PutMapping("/favourites")
    public ResponseEntity<String> favourites(@RequestParam("studentID") String studentID, @RequestParam("postId") String postId){
        SavedEntity savedEntity = savedRepository.findByStudentID(studentID);
        if (savedEntity == null) {
            savedEntity = new SavedEntity();
            savedEntity.setStudentID(studentID);
        }
        savedEntity.setPostId(postId);
        savedRepository.save(savedEntity);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/favourites_load")
    public SavedDTO favouritesLoad(@RequestBody Map<String, String> requestData){
        SavedDTO savedDTO = new SavedDTO();
        SavedEntity savedEntity = savedRepository.findByStudentID(requestData.get("studentID"));
        if (savedEntity==null)return savedDTO;
        else{
            for(String post : savedEntity.getPostId()){
                if(post.startsWith("F")){
                    FoodEntity food = foodRepository.findByPostId(post);
                    FoodDTO foodDTO = new FoodDTO(post, food.getNickname(), food.getStore(), food.getRating(), food.getPost_time());
                    savedDTO.setSavedFood(foodDTO);
                }else if(post.startsWith("H")){
                    HouseEntity house = houseRepository.findByPostId(post);
                    HouseDTO houseDTO = new HouseDTO(post, house.getName(), house.getTitle());
                    savedDTO.setSavedHouse(houseDTO);
                }
            }
            return savedDTO;
        }
    }

    @DeleteMapping("/favorites_delete")
    public ResponseEntity<String> favouritesDelete(@RequestBody Map<String, String> requestData){
        System.out.println("/favorites_delete");
        SavedEntity savedEntity = savedRepository.findByStudentID(requestData.get("studentID"));
        for(String postId : savedEntity.getPostId()){
            if(Objects.equals(postId, requestData.get("postId"))){
                savedEntity.removePostId(postId);
                savedRepository.save(savedEntity);
                return ResponseEntity.ok("Success");
            }
        }
        return ResponseEntity.badRequest().body("Invalid request");
    }

    
}


