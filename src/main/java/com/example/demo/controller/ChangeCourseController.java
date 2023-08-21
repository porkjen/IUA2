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

import java.util.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ChangeCourseController {
    @Autowired
    ChangeCourseHaveRepository changeCourseHaveRepository;
    @Autowired
    ChangeCourseRepository changeCourseRepository;
    @Autowired
    BasicRepository basicRepository;
    /*
    @GetMapping("/course_change_have") //whether there is class at this time
    public List<ChangeCourseHaveEntity> courseChangeHave(){
        List<ChangeCourseHaveEntity> courseTimeList = changeCourseHaveRepository.findAll();
        return courseTimeList;
    }*/
    @GetMapping("/course_change_have") //whether there is class at this time
    public List<ChangeCourseHaveEntity> courseChangeHave(){
        List<ChangeCourseHaveEntity> courseTimeList = changeCourseHaveRepository.findAll();
        return courseTimeList;
    }

    /*
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
    }*/

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

    @GetMapping("/course_classify")
    public List<ChangeCourseEntity> course_Classify(@RequestParam(value = "category", required = false) String category, @RequestParam(value = "time", required = false) String time){
        System.out.println("/course_classify");
        List<ChangeCourseEntity> courses = new ArrayList<>();
        if(!Objects.equals(category, "")){
            if(Objects.equals(category, "general")){
                courses = changeCourseRepository.findByCategory("通識");
            }
            else if (Objects.equals(category, "PE")) {
                courses = changeCourseRepository.findByCategory("體育");
            }
            else if(Objects.equals(category, "elective")){
                courses = changeCourseRepository.findByCategory("選修");
            }
            else if(Objects.equals(category, "compulsory")){
                courses = changeCourseRepository.findByCategory("必修");
            }
            else if(Objects.equals(category, "english")){
                courses = changeCourseRepository.findByCategory("英文");
            }
            else{
                courses = changeCourseRepository.findByCategory("第二外語");
            }
            boolean found;
            System.out.println(courses.size());
            if(!Objects.equals(time, "")){
                for(int j=0;j<courses.size();j++){
                    System.out.println(courses.get(j).getCategory());
                    found = false;
                    for(int i=0;i<courses.get(j).getTime().length;i++){
                        if(Objects.equals(courses.get(j).getTime()[i], time)){
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        courses.remove(courses.get(j));
                        j--;
                    }
                }
            }
            return courses;
        }
        else{
            for(ChangeCourseEntity c : changeCourseRepository.findAll()){
                for(int i=0;i<c.getTime().length;i++){
                    if(Objects.equals(c.getTime()[i], time)){
                        courses.add(c);
                        break;
                    }
                }
            }
            return courses;
        }
    }
}
