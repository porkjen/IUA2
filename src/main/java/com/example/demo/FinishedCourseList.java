package com.example.demo;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;

@Document(collection = "F_CourseCollection")
public class FinishedCourseList{
    private String studentID;
    private ArrayList<FinishedCourse> fCourse;

    public FinishedCourseList(String id){
        this.studentID = id;
    }

    public void setFinishedCourses(ArrayList<FinishedCourse> fc){
        this.fCourse = fc;
    }

    public ArrayList<FinishedCourse> getFinishedCourses(){
        return fCourse;
    }

    public String getStudentID() {
        return studentID;
    }
}