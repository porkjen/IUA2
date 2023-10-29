package com.example.demo.controller;

//import com.example.demo.*;
//import com.example.demo.dao.*;
import com.example.demo.*;
import com.example.demo.dao.*;
import com.example.demo.repository.*;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ChangeCourseController {
    @Autowired
    ChangeCourseHaveRepository changeCourseHaveRepository;
    @Autowired
    ChangeCourseRepository changeCourseRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    BasicRepository basicRepository;
    @Autowired
    TimeTableRepository timeTableRepository;
    @Autowired
    SavedRepository savedRepository;

    @GetMapping("/course_change_have") //whether there is class at this time
    public List<ChangeCourseHaveEntity> courseChangeHave(@RequestParam("studentID") String studentID){
        System.out.println("/course_change_have");
        List<ChangeCourseHaveEntity> courseTimeList = changeCourseHaveRepository.findAll();
        for(String c : notificationRepository.findByStudentID(studentID).getDesireClasses()){
            System.out.println(c);
            for(ChangeCourseEntity coursePost : changeCourseRepository.findByCourse(c)){
                System.out.println(Arrays.toString(coursePost.getTime()));
                for(int i=0;i<coursePost.getTime().length;i++){
                    int idx = 14*(Integer.parseInt(coursePost.getTime()[i])/100-1)+(Integer.parseInt(coursePost.getTime()[i])-Integer.parseInt(coursePost.getTime()[i])/100*100)-1;
                    System.out.println("desire : "+idx);
                    courseTimeList.get(idx).setPair();
                }
            }
        }
        for(String c : savedRepository.findByStudentID(studentID).getPosted()){
            if(c.startsWith("C")){
                ChangeCourseEntity coursePost = changeCourseRepository.findByPostId(c);
                for(int i=0;i<coursePost.getTime().length;i++){
                    int idx = 14*(Integer.parseInt(coursePost.getTime()[i])/100-1)+(Integer.parseInt(coursePost.getTime()[i])-Integer.parseInt(coursePost.getTime()[i])/100*100)-1;
                    System.out.println("mypost : "+idx);
                    courseTimeList.get(idx).setMine();
                }
            }

        }
        return courseTimeList;
    }


    @GetMapping("/course_change")
    public List<ChangeCourseEntity> courseChange(@RequestParam("time") String time, @RequestParam("studentID") String studentID){
        List<ChangeCourseEntity> thisTimeCourses = new ArrayList<>();
        NotificationCondition  nt = notificationRepository.findByStudentID(studentID);
        List<String>desiredClassList = new ArrayList<>();
        if(nt!=null)desiredClassList = nt.getDesireClasses();
        boolean pair = false;
        int pos=0;
        for(ChangeCourseEntity c : changeCourseRepository.findAll()){//all posts
            String[] timeArray = c.getTime();
            for (String value : timeArray) {
                if (Objects.equals(value, time)) {
                    if(Objects.equals(c.getStatus(), "未換")){
                        for (String s : desiredClassList) {
                            if (Objects.equals(c.getCourse(), s)) {
                                thisTimeCourses.add(0, c);
                                pos++;
                                pair = true;
                                break;
                            }
                        }
                        if (!pair) {
                            thisTimeCourses.add(pos,c);
                            break;
                        }
                    }
                    if (!pair) thisTimeCourses.add(c);
                    break;
                }
            }
            pair = false;
        }
        return thisTimeCourses;
    }

    @PostMapping("/course_full_post")
    public ChangeCourseEntity courseFullPost(@RequestBody Map<String, String> requestData){
        return changeCourseRepository.findByPostId(requestData.get("postId"));
    }

    @PostMapping("/exchange_course_post")
    public ResponseEntity<?> exchangeCoursePost(@RequestBody ChangeCourseEntity course, @RequestHeader("Authorization")String au){
        System.out.println("/exchange_course_post, 換課發文");
        JwtToken jwtToken = new JwtToken();
        try {
            jwtToken.validateToken(au, course.getStudentID());
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        if(changeCourseRepository.findFirstByOrderByIdDesc() == null)course.setPostId("C00001");
        else{
            String post_id; //get new post_id
            NextPostId nextPostId = new NextPostId();
            post_id = nextPostId.getNextCourseString(changeCourseRepository.findFirstByOrderByIdDesc().getPostId());
            course.setPostId(post_id);
        }
        System.out.println("postId : "+course.getPostId());
        for(int i=0;i< course.getTime().length;i++){
            ChangeCourseHaveEntity thisTime = changeCourseHaveRepository.findByTime(course.getTime()[i]);
            thisTime.setHave(thisTime.getHave() + 1);
            changeCourseHaveRepository.save(thisTime);
        }
        String dateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd")//date today
                .format(LocalDateTime.now());
        course.setPost_time(dateTime);
        course.setNickname(basicRepository.findByStudentID(course.getStudentID()).getNickname());
        changeCourseRepository.save(course);
        if(course.getDesiredClass()!=null){
            NotificationCondition n = notificationRepository.findByStudentID(course.getStudentID());
            if(n==null) n=new NotificationCondition(course.getStudentID());
            n.setDesireClasses(course.getDesiredClass());
            notificationRepository.save(n);
        }
        //加入到我的文章
        SavedEntity saved = savedRepository.findByStudentID(course.getStudentID());
        saved.setPosted(course.getPostId());//add
        savedRepository.save(saved);
        System.out.println("發文成功");
        //adjust timetable "change_avail" status
        TimeTableEntity timeTable = timeTableRepository.findByStudentID(course.getStudentID());
        for(TimeTableEntity.Info info : timeTable.getInfo()){
            if(Objects.equals(info.getName(), course.getCourse())) {
                info.setChange_avail(false);
                break;
            }
        }
        timeTableRepository.save(timeTable);
        return ResponseEntity.ok(course);
    }

    @PutMapping("/course_post_modify")
    public ResponseEntity<?> coursePostModify(@RequestBody ChangeCourseEntity modifyCourse, @RequestHeader("Authorization")String au){
        System.out.println("/course_post_modify, 修改換課貼文");
        JwtToken jwtToken = new JwtToken();
        try {
            jwtToken.validateToken(au, modifyCourse.getStudentID());
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
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
        System.out.println("修改換課貼文成功");
        return ResponseEntity.ok(original);
    }

    @DeleteMapping("/course_post_delete")
    public ResponseEntity<String> coursePostDelete(@RequestBody Map<String, String> requestData, @RequestHeader("Authorization")String au){
        System.out.println("/course_post_delete, 刪除換課貼文");
        JwtToken jwtToken = new JwtToken();
        try {
            jwtToken.validateToken(au, requestData.get("studentID"));
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        if(changeCourseRepository.findByPostId(requestData.get("postId")) != null){
            ChangeCourseEntity thisCourse = changeCourseRepository.findByPostId(requestData.get("postId"));
            for(int i=0;i< thisCourse.getTime().length;i++){
                ChangeCourseHaveEntity thisTime = changeCourseHaveRepository.findByTime(thisCourse.getTime()[i]);
                thisTime.setHave(thisTime.getHave() - 1);
                changeCourseHaveRepository.save(thisTime);
            }
            changeCourseRepository.delete(thisCourse);
            //刪除我的貼文
            SavedEntity saved = savedRepository.findByStudentID(requestData.get("studentID"));
            saved.removePosted(requestData.get("postId"));
            savedRepository.save(saved);
            System.out.println("刪除成功");
            return ResponseEntity.ok("Success"); //200
        }
        else {
            System.out.println("找不到換課貼文，刪除失敗");
            return (ResponseEntity<String>) ResponseEntity.noContent(); //204
        }
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

    @GetMapping("/my_course_posts")
    public List<ChangeCourseEntity> myRentPosts(@RequestParam("studentID") String studentID){
        System.out.println("/my_course_posts, studentID : "+studentID);
        SavedEntity savedEntity = savedRepository.findByStudentID(studentID);
        List<ChangeCourseEntity> C_post = new ArrayList<>();
        for(String postId : savedEntity.getPosted()){
            if(postId.startsWith("C")){
                ChangeCourseEntity c = changeCourseRepository.findByPostId(postId);
                if(Objects.equals(c.getStatus(),"已換"))C_post.add(c);
                else C_post.add(0,c);
            }
        }
        return C_post;
    }
}
