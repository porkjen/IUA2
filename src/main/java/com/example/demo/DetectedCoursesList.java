package com.example.demo;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;

@Document(collection = "DetectedCourses")
public class DetectedCoursesList {
    private String studentID;
    private ArrayList<CourseToBeDetected> dCourse;

    public DetectedCoursesList(){}

    public void setStudentID(String id){
        this.studentID = id;
    }

    public void setDetectedCourse(ArrayList<CourseToBeDetected> dCourse){
        this.dCourse = dCourse;
    }

    public String getStudentID() {
        return studentID;
    }

    public ArrayList<CourseToBeDetected> getDetectedCourses(){
        return dCourse;
    }
}
