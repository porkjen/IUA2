package com.example.demo.repository;
import com.example.demo.DetectedCoursesList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DetectedRepository extends MongoRepository<DetectedCoursesList, String>{
    DetectedCoursesList findByStudentID(String studentID);
    boolean existsByStudentID(String studentID);
}
