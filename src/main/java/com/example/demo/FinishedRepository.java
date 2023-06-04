package com.example.demo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FinishedRepository extends MongoRepository<FinishedCourse, String> {
    public FinishedCourse findByStudentID(String studentID);
}
