package com.example.demo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DetectedRepository extends MongoRepository<DetectedCoursesList, String>{
    public DetectedCoursesList findByStudentID(String studentID);
    public boolean existsByStudentID(String studentID);
}
