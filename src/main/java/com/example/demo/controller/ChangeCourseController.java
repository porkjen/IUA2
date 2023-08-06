package com.example.demo.controller;

//import com.example.demo.*;
//import com.example.demo.dao.*;
import com.example.demo.BasicRepository;
import com.example.demo.ChangeCourseHaveRepository;
import com.example.demo.ChangeCourseRepository;
import com.example.demo.NextPostId;
import com.example.demo.dao.ChangeCourseEntity;
import com.example.demo.dao.ChangeCourseHaveEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ChangeCourseController {
    @Autowired
    ChangeCourseHaveRepository changeCourseHaveRepository;
    @Autowired
    ChangeCourseRepository changeCourseRepository;
    @Autowired
    BasicRepository basicRepository;
    @GetMapping("/course_change_have") //whether there is class at this time
    public List<ChangeCourseHaveEntity> courseChangeHave(){
        List<ChangeCourseHaveEntity> courseTimeList = new ArrayList<>();
        for(int i=1;i<14;i++){
            for(int j=100;j<700;j+=100){
                String time = String.valueOf(j+i);
                courseTimeList.add(changeCourseHaveRepository.findByTime(time));
            }
        }

        return courseTimeList;
    }

    @GetMapping("/course_change")
    public List<ChangeCourseEntity> courseChange(@RequestParam("time") String time){
        List<ChangeCourseEntity> thisTimeCourses = new ArrayList<>();
        for(ChangeCourseEntity c : changeCourseRepository.findAll()){
            String[] timeArray = c.getTime();
            for(int i=0;i< timeArray.length;i++){
                if(Objects.equals(timeArray[i], time))thisTimeCourses.add(c);
            }
        }
        return thisTimeCourses;
    }

    @PostMapping("/course_full_post")
    public ChangeCourseEntity courseFullPost(@RequestBody Map<String, String> requestData){
        return changeCourseRepository.findByPostId(requestData.get("postId"));
    }

    @PostMapping("/exchange_course_post")
    public ChangeCourseEntity exchangeCoursePost(@RequestBody ChangeCourseEntity course){
        if(changeCourseRepository.findFirstByOrderByIdDesc() == null)course.setPostId("C00001");
        else{
            String post_id; //get new post_id
            NextPostId nextPostId = new NextPostId();
            post_id = nextPostId.getNextCourseString(changeCourseRepository.findFirstByOrderByIdDesc().getPostId());
            course.setPostId(post_id);
        }
        for(int i=0;i< course.getTime().length;i++){
            ChangeCourseHaveEntity thisTime = changeCourseHaveRepository.findByTime(course.getTime()[i]);
            thisTime.setHave(thisTime.getHave() + 1);
            changeCourseHaveRepository.save(thisTime);
        }
        course.setNickname(basicRepository.findByStudentID(course.getStudentID()).getNickname());
        changeCourseRepository.save(course);
        return course;
    }

    @PutMapping("/course_post_modify")
    public ChangeCourseEntity coursePostModify(@RequestBody ChangeCourseEntity modifyCourse){
        ChangeCourseEntity original = changeCourseRepository.findByPostId(modifyCourse.getPostId());
        original.setCourse(modifyCourse.getCourse());
        original.setCategory(modifyCourse.getCategory());
        for(int i=0;i< modifyCourse.getTime().length;i++){
            ChangeCourseHaveEntity thisTime = changeCourseHaveRepository.findByTime(modifyCourse.getTime()[i]);
            thisTime.setHave(thisTime.getHave() + 1);
            changeCourseHaveRepository.save(thisTime);
        }
        for(int i=0;i< original.getTime().length;i++){
            ChangeCourseHaveEntity thisTime = changeCourseHaveRepository.findByTime(original.getTime()[i]);
            thisTime.setHave(thisTime.getHave() - 1);
            changeCourseHaveRepository.save(thisTime);
        }
        original.setTime(modifyCourse.getTime());
        original.setTeacher(modifyCourse.getTeacher());
        original.setContent(modifyCourse.getContent());
        changeCourseRepository.save(original);
        return original;
    }

    @DeleteMapping("/course_post_delete")
    public ResponseEntity<String> coursePostDelete(@RequestBody Map<String, String> requestData){
        if(changeCourseRepository.findByPostId(requestData.get("postId")) != null){
            ChangeCourseEntity thisCourse = changeCourseRepository.findByPostId(requestData.get("postId"));
            for(int i=0;i< thisCourse.getTime().length;i++){
                ChangeCourseHaveEntity thisTime = changeCourseHaveRepository.findByTime(thisCourse.getTime()[i]);
                thisTime.setHave(thisTime.getHave() - 1);
                changeCourseHaveRepository.save(thisTime);
            }
            changeCourseRepository.delete(thisCourse);
            return ResponseEntity.ok("Success"); //200
        }
        else return (ResponseEntity<String>) ResponseEntity.noContent(); //204
    }
}
