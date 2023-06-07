package com.example.demo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FinishedRepository extends MongoRepository<FinishedCourseList, String> {
    public FinishedCourseList findByStudentID(String studentID);
}
