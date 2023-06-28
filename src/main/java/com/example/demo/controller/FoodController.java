package com.example.demo.controller;

import com.example.demo.BasicRepository;
import com.example.demo.FoodRepository;
import com.example.demo.NextPostId;
import com.example.demo.dao.FoodDTO;
import com.example.demo.dao.FoodEntity;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/food_posts")//post a food post
    public FoodEntity foodPosts(@RequestBody FoodEntity food){
        String post_id; //get new post_id
        NextPostId nextPostId = new NextPostId();
        if(foodRepository.findFirstByOrderByIdDesc()==null){food.setPostId("F00001");}
        else{
            food.setPostId(nextPostId.getNextFoodString(foodRepository.findFirstByOrderByIdDesc().getPostId()));
        }
        food.setNickname(basicRepository.findByStudentID(food.getStudentID()).getNickname());
        food.setRating(food.getReview().get(0).getP_rate());
        food.setRating_num(1); //rating people : 1(by author)
        food.setPost_time(DateTimeFormatter.ofPattern("yyyy/MM/dd")//date today
                .format(LocalDateTime.now()));
        //review
        food.getReview().get(0).setP_studentID(food.getStudentID());
        food.getReview().get(0).setP_name(basicRepository.findByStudentID(food.getStudentID()).getNickname());
        food.getReview().get(0).setP_time(DateTimeFormatter.ofPattern("yyyy/MM/dd")//date today
                .format(LocalDateTime.now()));

        foodRepository.save(food);
        return food;
    }

    @PostMapping("/food_full_post")
    public FoodEntity foodFullPost(@RequestBody Map<String, String> requestData){
        System.out.println("/food_full_post");
        FoodEntity foodEntity = foodRepository.findByPostId(requestData.get("postId"));
        if(Objects.equals(foodEntity.getSaved().get(0), requestData.get("studentID"))){
            foodEntity.savefirst("true");
            return foodEntity;
        }
        else foodEntity.savefirst("false");
        for(String user : foodEntity.getSaved()){
            if(Objects.equals(user, requestData.get("studentID")))
                foodEntity.savefirst("true");
        }
        return foodEntity;
    }

    @GetMapping("/food_load") //load all food posts
    public List<FoodDTO> foodLoad(){
        List<FoodEntity> foodPostList = foodRepository.findAllByOrderByIdDesc();
        List<FoodDTO> shortenedPostList = new ArrayList<>();
        for (FoodEntity post : foodPostList) {
            FoodDTO dto = new FoodDTO(post.getPostId(), post.getNickname(), post.getStore(), post.getRating(), post.getPost_time());
            shortenedPostList.add(dto);
        }
        return shortenedPostList;
    }
    @PutMapping("/food_modify")
    public FoodEntity foodModify(@RequestBody FoodEntity food){
        System.out.println("/food_modify");
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
        foodRepository.save(modifyFoodPost);
        return modifyFoodPost;
    }

    @DeleteMapping("/food_post_delete")
    public ResponseEntity<String> foodPostDelete(@RequestParam("studentID") String studentID, @RequestParam("postId") String postId){
        System.out.println("/food_post_delete");
        if(foodRepository.deleteByPostId(postId) !=null){//200
            return ResponseEntity.ok("Success");
        }
        else return (ResponseEntity<String>) ResponseEntity.noContent(); //204
    }

    @PutMapping("/food_review_modify")
    public FoodEntity.p foodReviewModify(@RequestBody Map<String, String> requestData) {
        System.out.println("/food_review_modify");
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
        return newReview;
    }

    @DeleteMapping("/food_review_delete")
    public ResponseEntity<String> foodReviewDelete(@RequestBody Map<String, String> requestData){
        System.out.println("/food_review_delete");
        double newRate = 0;
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        //find the post by postId
        FoodEntity thisPost = foodRepository.deleteByPostId(requestData.get("postId"));
        //find the review by studentID
        FoodEntity.p thisReview = new FoodEntity.p();
        if(thisPost !=null){
            for(FoodEntity.p review : thisPost.getReview()){
                if(Objects.equals(review.getP_studentID(), requestData.get("studentID"))){
                    thisReview = review;
                }
            }
            if(thisReview.getP_rate()!=0) { //this review has rating
                newRate = (thisPost.getRating() * thisPost.getRating_num() - thisReview.getP_rate()) / (thisPost.getRating_num() - 1);
                thisPost.setRating(Double.parseDouble(decimalFormat.format(newRate)));
                thisPost.setRating_num(thisPost.getRating_num() - 1);
            }
            thisPost.removeReview(thisReview);
            foodRepository.save(thisPost);
            //200
            return ResponseEntity.ok("Success");
        }
        else return (ResponseEntity<String>) ResponseEntity.noContent(); //204
    }

    @PostMapping("/food_review_add")
    public FoodEntity.p foodReviewAdd(@RequestBody Map<String, String> requestData){
        System.out.println("/food_review_add");
        double newRate = 0;
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        //find the post by postId
        FoodEntity thisPost = foodRepository.findByPostId(requestData.get("postId"));
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
        //if user has rate, post : rate_num+1, rate adjust
        if(!Objects.equals(requestData.get("p_rate"), "")) {
            newRate = (thisPost.getRating() * thisPost.getRating_num() + Integer.parseInt(requestData.get("p_rate"))) / (thisPost.getRating_num() + 1);
            thisPost.setRating(Double.parseDouble(decimalFormat.format(newRate)));
            thisPost.setRating_num(thisPost.getRating_num() + 1);
        }
        foodRepository.save(thisPost);
        return newReview;
    }

    @GetMapping("/food_search")
    public List<FoodDTO> foodSearch(@RequestParam(value = "area", required = false) String area, @RequestParam(value = "store", required = false) String store){
        System.out.println("/food_search");
        List<FoodDTO> resultList = new ArrayList<>();
        for(FoodEntity food : foodRepository.findAll()){
            //search for both
            if((store!=null && !store.equals(""))&&(area!=null && !area.equals(""))){
                if(food.getStore().toLowerCase().contains(store.toLowerCase()) && food.getAddress().contains(area)){
                    FoodDTO result = new FoodDTO(food.getPostId(), food.getNickname(), food.getStore(), food.getRating(), food.getPost_time());
                    resultList.add(result);
                }
            }
            //search for store key word
            else if(store!=null && !store.equals("")){
                if(food.getStore().toLowerCase().contains(store.toLowerCase())){
                    FoodDTO result = new FoodDTO(food.getPostId(), food.getNickname(), food.getStore(), food.getRating(), food.getPost_time());
                    resultList.add(result);
                }
            }
            //search for area
            else if(area!=null && !area.equals("")){
                if(food.getAddress().contains(area)){
                    FoodDTO result = new FoodDTO(food.getPostId(), food.getNickname(), food.getStore(), food.getRating(), food.getPost_time());
                    resultList.add(result);
                }
            }
        }
        return resultList;
    }

}
