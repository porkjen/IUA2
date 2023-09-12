package com.example.demo.controller;

import com.example.demo.JwtToken;
import com.example.demo.NextPostId;
import com.example.demo.dao.HouseDTO;
import com.example.demo.dao.HouseEntity;
import com.example.demo.dao.SavedEntity;
import com.example.demo.repository.BasicRepository;
import com.example.demo.repository.HouseRepository;
import com.example.demo.repository.SavedRepository;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RentController {
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    SavedRepository savedRepository;
    @Autowired
    BasicRepository basicRepository;
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
}
