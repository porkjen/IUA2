package com.example.demo.controller;

import com.example.demo.*;

import com.example.demo.dao.BasicEntity;
import com.example.demo.dao.HouseDTO;
import com.example.demo.dao.HouseEntity;

import com.example.demo.service.TodoService;
import jakarta.servlet.http.HttpServletResponse;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class TodoController {
    private String sID;
    @Autowired
    TodoService todoService;//取得Service物件
    @Autowired
    FinishedRepository fRepository;
    @Autowired
    BasicRepository basicRepository;
    @Autowired
    HouseRepository houseRepository;
    String secretKey = "au4a83";

    Crawler crawler = new Crawler();


    @PostMapping("/login")
    public void login(@RequestBody BasicEntity basic)throws TesseractException, IOException, InterruptedException  {

        AESEncryptionDecryption aesEncryptionDecryption = new AESEncryptionDecryption();
        String studentID = basic.getStudentID();
        String password = basic.getPassword();
        System.out.println(studentID);
        String encryptedpwd = aesEncryptionDecryption.encrypt(password, secretKey);
        String decryptedpwd = aesEncryptionDecryption.decrypt(encryptedpwd, secretKey);
        if(basicRepository.findByStudentID(studentID)==null){
            // Crawler crawler = new Crawler();
            crawler.CrawlerHandle(studentID,password);
            basic = crawler.getBasicData(studentID,password);
            basic.setPassword(encryptedpwd);
            basicRepository.save(basic);
        }
        else System.out.println("This account has login before!");
        System.out.println("加密:"+encryptedpwd);
        System.out.println("original:"+decryptedpwd);

        sID = studentID;
    }
    @GetMapping("/hello")
    public String hello() {
        return "Hello, the time at the server is now " + new Date() + "\n";
    }

    @PostMapping("/nickname")
    public void postnickname (@RequestBody BasicEntity basic, HttpServletResponse response)throws TesseractException, IOException, InterruptedException  {
        System.out.println(basic.getStudentID());
        BasicEntity oldProduct = basicRepository.findByStudentID(basic.getStudentID());
        oldProduct.setNickname(basic.getNickname());
        basicRepository.save(oldProduct);
    }
    @PostMapping("/rent_post")
    public HouseEntity rentPost(@RequestBody HouseEntity house){
        System.out.println("/rent_post");
        String dateTime = DateTimeFormatter.ofPattern("yyyy MM dd")//date today
                .format(LocalDateTime.now());
        String post_id; //get new post_id
        NextPostId nextPostId = new NextPostId();
        if(houseRepository.findFirstByOrderByIdDesc()==null){post_id = "H00001";}
        else{
            post_id = nextPostId.getNextHouseString(houseRepository.findFirstByOrderByIdDesc().getPostId());
        }
        HouseEntity h = new HouseEntity();
        h.setPostId(post_id);
        h.setStudentID(house.getStudentID());
        h.setName(basicRepository.findByStudentID(house.getStudentID()).getName());//real name
        //h.setName(house.getName());
        h.setTitle(house.getTitle());
        h.setMoney(house.getMoney());
        h.setPeople(house.getPeople());
        h.setAddress(house.getAddress());
        h.setArea(house.getArea());
        h.setGender(house.getGender());
        h.setStyle(house.getStyle());
        h.setWater(house.getWater());
        h.setPower(house.getPower());
        h.setCar(house.getCar());
        h.setFloor(house.getFloor());
        h.setRent_date(house.getRent_date());
        h.setNote(house.getNote());
        h.setPost_time(dateTime);
        houseRepository.save(h);
        return h;
    }

    @PostMapping("/remained_credits")
    public FinishedCourseList postRemainCredits (@RequestBody FinishedCourseList finished)throws TesseractException, IOException, InterruptedException{
        ArrayList<FinishedCourse> finishedCourse = new ArrayList<FinishedCourse>();
        FinishedCourseList fc = new FinishedCourseList(finished.getStudentID());
        finishedCourse = crawler.getCredict();
        fc.setFinishedCourses(finishedCourse);
        fRepository.save(fc);
        return fc;
    }

    @GetMapping("/rent_load")
    public List<HouseDTO> rentLoad(){
        List<HouseEntity> housePostList = houseRepository.findAll();
        List<HouseDTO> SimpleHousePostList = new ArrayList<>();
        for (HouseEntity post : housePostList) {
            HouseDTO dto = new HouseDTO(post.getPostId(), post.getName(), post.getTitle());
            SimpleHousePostList.add(dto);
        }
        return SimpleHousePostList;
    }

    @GetMapping("/rent_full_post")
    public HouseEntity rentFullPost(@RequestBody HouseEntity houseEntity){
        return houseRepository.findByPostId(houseEntity.getPostId());
    }
}


