package com.example.demo.controller;

import com.example.demo.JwtToken;
import com.example.demo.repository.BasicRepository;
import com.example.demo.repository.FoodRepository;
import com.example.demo.NextPostId;
import com.example.demo.repository.SavedRepository;
import com.example.demo.dao.FoodDTO;
import com.example.demo.dao.FoodEntity;
import com.example.demo.dao.SavedEntity;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class FoodController {
    @Autowired
    FoodRepository foodRepository;
    @Autowired
    BasicRepository basicRepository;

    @Autowired
    SavedRepository savedRepository;
    @PostMapping("/food_posts")//post a food post
    public ResponseEntity<?> foodPosts(@RequestBody FoodEntity food, @RequestHeader("Authorization") String au){
        System.out.println("/food_posts, 發文");
        JwtToken jwtToken = new JwtToken();
        try {
            jwtToken.validateToken(au, food.getStudentID());
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        //get new post_id
        if(foodRepository.findFirstByOrderByIdDesc()==null){food.setPostId("F00001");}
        else{
            NextPostId nextPostId = new NextPostId();
            food.setPostId(nextPostId.getNextFoodString(foodRepository.findFirstByOrderByIdDesc().getPostId()));
        }
        food.setNickname(basicRepository.findByStudentID(food.getStudentID()).getNickname());
        food.setRating(food.getReview().get(0).getP_rate());
        food.setRating_num(1); //rating people : 1(by author)
        food.setReport(0);
        food.setDistance(0);
        food.setPost_time(DateTimeFormatter.ofPattern("yyyy/MM/dd")//date today
                .format(LocalDateTime.now()));
        //review
        food.getReview().get(0).setP_studentID(food.getStudentID());
        food.getReview().get(0).setP_name(food.getNickname());
        food.getReview().get(0).setP_time(food.getPost_time());
        foodRepository.save(food);
        SavedEntity savedEntity = savedRepository.findByStudentID(food.getStudentID());
        savedEntity.setPosted(food.getPostId());//add
        savedRepository.save(savedEntity);
        return ResponseEntity.ok(food);
    }

    @PostMapping("/food_full_post")
    public FoodEntity foodFullPost(@RequestBody Map<String, String> requestData){
        System.out.println("/food_full_post\npostId : "+requestData.get("postId"));
        FoodEntity foodEntity = foodRepository.findByPostId(requestData.get("postId"));
        //find out if the user has commented on the post, to put user's review first
        for(FoodEntity.p review : foodEntity.getReview()){
            if(Objects.equals(review.getP_studentID(), requestData.get("studentID"))) {
                //remove the review and add to the start
                foodEntity.removeReview(review);
                foodEntity.reviewFirst(review);
                break;
            }
        }
        //to know whether user saves the post
        //no one saves this post
        if(foodEntity.getSaved().size()==0){
            foodEntity.saveFirst("false");
            return foodEntity;
        }
        for(String user : foodEntity.getSaved()){
            //user saved this post
            if(Objects.equals(user, requestData.get("studentID"))) {
                foodEntity.saveFirst("true");
                return foodEntity;
            }
        }
        //user doesn't save this post
        foodEntity.saveFirst("false");
        return foodEntity;
    }

    @GetMapping("/food_load") //load all food posts
    public List<FoodDTO> foodLoad(@RequestParam("sort") String sort){
        System.out.println("/food_load, sort : "+sort);
        List<FoodEntity> foodPostList;
        if(Objects.equals(sort, "PostTimeNtoF"))foodPostList = foodRepository.findAllByOrderByIdDesc();
        else if (Objects.equals(sort, "PostTimeFtoN")) foodPostList = foodRepository.findAll();
        else if(Objects.equals(sort, "rate_Decrease")) foodPostList = foodRepository.findAllByOrderByRatingDesc();
        else if(Objects.equals(sort, "rate_Increase")) foodPostList = foodRepository.findAllByOrderByRatingAsc();
        else if(Objects.equals(sort, "distance_Increase"))foodPostList = foodRepository.findAllByOrderByDistanceAsc();
        else foodPostList = foodRepository.findAllByOrderByDistanceDesc(); //distance_Decrease

        List<FoodDTO> shortenedPostList = new ArrayList<>();
        for (FoodEntity post : foodPostList) {
            FoodDTO dto = new FoodDTO(post.getPostId(), post.getNickname(), post.getStore(), post.getRating(), post.getPost_time(), post.getRoad(), post.getDistance());
            shortenedPostList.add(dto);
        }
        return shortenedPostList;
    }
    @PutMapping("/food_modify")
    public ResponseEntity<?> foodModify(@RequestBody FoodEntity food, @RequestHeader("Authorization")String au){
        System.out.println("/food_modify, 修改貼文");
        JwtToken jwtToken = new JwtToken();
        try {
            jwtToken.validateToken(au, food.getStudentID());
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        FoodEntity modifyFoodPost = foodRepository.findByPostId(food.getPostId());
        //modify store name
        if(!Objects.equals(food.getStore(), "") )
            modifyFoodPost.setStore(food.getStore());
        //modify open day
        if(!Objects.equals(food.getWeekday_text()[0], "")){
            for(int i=0;i<7;i++){
                modifyFoodPost.setWeekday_text(food.getWeekday_text()[i], i);
            }
        }
        //modify address
        if(!Objects.equals(food.getAddress(), ""))
            modifyFoodPost.setAddress(food.getAddress());
        //modify road
        if(!Objects.equals(food.getRoad(), ""))
            modifyFoodPost.setRoad(food.getRoad());
        //modify district
        if(!Objects.equals(food.getDistrict(), ""))
            modifyFoodPost.setRoad(food.getDistrict());
        //modify url
        if(!Objects.equals(food.getURL(), ""))
            modifyFoodPost.setRoad(food.getURL());
        foodRepository.save(modifyFoodPost);
        return ResponseEntity.ok(modifyFoodPost);
    }

    @DeleteMapping("/food_post_delete")
    public ResponseEntity<String> foodPostDelete(@RequestParam("studentID") String studentID, @RequestParam("postId") String postId, @RequestHeader("Authorization") String au){
        System.out.println("/food_post_delete, 刪除貼文");
        JwtToken jwtToken = new JwtToken();
        try {
            jwtToken.validateToken(au, studentID);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        if(foodRepository.deleteByPostId(postId) !=null){
            SavedEntity savedEntity =  savedRepository.findByStudentID(studentID);
            savedEntity.removePosted(postId);
            savedRepository.save(savedEntity);
            System.out.println("刪除成功");
            return ResponseEntity.ok("Success");//200
        }
        else {
            System.out.println("貼文未找到，刪除失敗");
            return ResponseEntity.badRequest().body("Invalid request");//400
        }
    }

    @PutMapping("/food_review_modify")
    public ResponseEntity<?> foodReviewModify(@RequestBody Map<String, String> requestData, @RequestHeader("Authorization")String au) {
        System.out.println("/food_review_modify, 修改留言");
        JwtToken jwtToken = new JwtToken();
        try {
            jwtToken.validateToken(au, requestData.get("studentID"));
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        int originalRate = 0;
        double newRate = 0;
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        //find the post by postId
        FoodEntity thisPost = foodRepository.findByPostId(requestData.get("postId"));
        //find the review want to modify by studentID
        FoodEntity.p newReview = new FoodEntity.p();
        for(int i=0; i<thisPost.getReview().size(); i++){
            if(Objects.equals(thisPost.getReview().get(i).getP_studentID(), requestData.get("studentID"))){
                //user modify rating
                if(!Objects.equals(requestData.get("p_rate"), "")){
                    originalRate = thisPost.getReview().get(i).getP_rate();
                    thisPost.getReview().get(i).setP_rate(Integer.parseInt(requestData.get("p_rate")));
                }
                //user modify review content
                if(!Objects.equals(requestData.get("p_review"), "")){
                    thisPost.getReview().get(i).setP_review(requestData.get("p_review"));
                }
                thisPost.getReview().get(i).setP_time(DateTimeFormatter.ofPattern("yyyy/MM/dd")//date today
                        .format(LocalDateTime.now()));
                newReview = thisPost.getReview().get(i);
            }
        }
        //Post rate update
        if(originalRate!=0) {
            newRate = (thisPost.getRating() * thisPost.getRating_num() - originalRate + Integer.parseInt(requestData.get("p_rate"))) / thisPost.getRating_num();
            thisPost.setRating(Double.parseDouble(decimalFormat.format(newRate)));
        }
        foodRepository.save(thisPost);
        return ResponseEntity.ok(newReview);
    }

    @DeleteMapping("/food_review_delete")
    public ResponseEntity<String> foodReviewDelete(@RequestBody Map<String, String> requestData, @RequestHeader("Authorization")String au){
        System.out.println("/food_review_delete, 刪除留言");
        JwtToken jwtToken = new JwtToken();
        try {
            jwtToken.validateToken(au, requestData.get("studentID"));
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        double newRate = 0;
        boolean hasReview = false;
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        //find the post by postId
        FoodEntity thisPost = foodRepository.findByPostId(requestData.get("postId"));
        if(thisPost == null)return ResponseEntity.badRequest().body("Invalid : post not found");//400
        //find the review by studentID
        FoodEntity.p thisReview = new FoodEntity.p();
        for(FoodEntity.p review : thisPost.getReview()){
            if(Objects.equals(review.getP_studentID(), requestData.get("studentID"))){
                thisReview = review;
                hasReview = true;
            }
        }
        if(!hasReview) return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);//404
        if(thisReview.getP_rate()!=0) { //this review has rating
            if((thisPost.getRating_num() - 1)==0)thisPost.setRating(0);
            else {
                newRate = (thisPost.getRating() * thisPost.getRating_num() - thisReview.getP_rate()) / (thisPost.getRating_num() - 1);
                System.out.println(newRate);
                thisPost.setRating(Double.parseDouble(decimalFormat.format(newRate)));
            }
            thisPost.setRating_num(thisPost.getRating_num() - 1);
        }
        thisPost.removeReview(thisReview);
        foodRepository.save(thisPost);
        return ResponseEntity.ok("Success");//200

    }

    @PostMapping("/food_review_add")
    public ResponseEntity<String> foodReviewAdd(@RequestBody Map<String, String> requestData, @RequestHeader("Authorization")String au){
        System.out.println("/food_review_add, 新增留言(一人只能留一次)");
        JwtToken jwtToken = new JwtToken();
        try {
            jwtToken.validateToken(au, requestData.get("studentID"));
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        double newRate = 0;
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        //find the post by postId
        FoodEntity thisPost = foodRepository.findByPostId(requestData.get("postId"));
        //find out if the user has commented on the post
        for(FoodEntity.p review : thisPost.getReview()){
            if(Objects.equals(review.getP_studentID(), requestData.get("studentID"))){
                System.out.println("留言過了!");
                return ResponseEntity.badRequest().body("Invalid request : user has commented on the post");//400
            }

        }
        //create a review structure, and add to the post
        FoodEntity.p newReview = new FoodEntity.p();
        newReview.setP_studentID(requestData.get("studentID"));
        newReview.setP_name(basicRepository.findByStudentID(requestData.get("studentID")).getNickname());
        newReview.setP_review(requestData.get("p_review"));
        newReview.setP_time(DateTimeFormatter.ofPattern("yyyy/MM/dd")//date today
                .format(LocalDateTime.now()));
        if(Objects.equals(requestData.get("p_rate"), ""))newReview.setP_rate(0);
        else newReview.setP_rate(Integer.parseInt(requestData.get("p_rate")));
        thisPost.setReview(newReview);
        //if user rate, post : rate_num+1, rate adjust
        if(!Objects.equals(requestData.get("p_rate"), "")) {
            newRate = (thisPost.getRating() * thisPost.getRating_num() + Integer.parseInt(requestData.get("p_rate"))) / (thisPost.getRating_num() + 1);
            thisPost.setRating(Double.parseDouble(decimalFormat.format(newRate)));
            thisPost.setRating_num(thisPost.getRating_num() + 1);
        }
        foodRepository.save(thisPost);
        System.out.println("留言成功");
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/food_search")
    public List<FoodDTO> foodSearch(
            @RequestParam(value = "area", required = false) String area,
            @RequestParam(value = "store", required = false) String store,
            @RequestParam(value = "addr", required = false) String addr){
        System.out.println("/food_search, 區 : " + area + " 店名 : " + store + " 地址 : " + addr);
        List<FoodDTO> resultList = new ArrayList<>();
        for(FoodEntity food : foodRepository.findAllByOrderByIdDesc()){
            if((Objects.equals(store, "") || food.getStore().toLowerCase().contains(store.toLowerCase())) &&
               (Objects.equals(area, "") || food.getDistrict().contains(area)) &&
               (Objects.equals(addr, "") || food.getAddress().contains(addr))
            ){
                FoodDTO result = new FoodDTO(food.getPostId(), food.getNickname(), food.getStore(), food.getRating(), food.getPost_time(), food.getRoad() , food.getDistance());
                resultList.add(result);
            }
        }
        return resultList;
    }

    @PostMapping("/food_close_report")
    public ResponseEntity<String> foodCloseReport(@RequestBody Map<String, String> requestData){
        System.out.println("/food_close_report");
        FoodEntity post = foodRepository.findByPostId(requestData.get("postId"));
        if(post==null)return ResponseEntity.badRequest().body("Invalid request"); //400
        else{
            post.setReport(post.getReport()+1);
            foodRepository.save(post);
            return ResponseEntity.ok("Success");
        }
    }

    @GetMapping("/my_food_posts")
    public List<FoodDTO> myFoodPosts(@RequestParam("studentID") String studentID){
        System.out.println("/my_food_posts, studentID : "+studentID);
        SavedEntity savedEntity = savedRepository.findByStudentID(studentID);
        List<FoodDTO> shortened = new ArrayList<>();
        System.out.println("My Posts : "+savedEntity.getPosted().size());
        for(String postId : savedEntity.getPosted()){
            if(postId.startsWith("F")){
                FoodEntity foodPost = foodRepository.findByPostId(postId);
                FoodDTO dto = new FoodDTO(foodPost.getPostId(), foodPost.getNickname(), foodPost.getStore(), foodPost.getRating(), foodPost.getPost_time(), foodPost.getRoad(), foodPost.getDistance());
                shortened.add(dto);
            }
        }
        return shortened;
    }
}