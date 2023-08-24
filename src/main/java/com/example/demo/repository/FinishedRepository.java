package com.example.demo.repository;
import com.example.demo.FinishedCourseList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FinishedRepository extends MongoRepository<FinishedCourseList, String> {
    public FinishedCourseList findByStudentID(String studentID);

    public boolean existsByStudentID(String studentID);
}
