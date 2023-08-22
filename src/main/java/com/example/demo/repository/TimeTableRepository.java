package com.example.demo.repository;

import com.example.demo.dao.TimeTableEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TimeTableRepository extends MongoRepository<TimeTableEntity, String> {
    TimeTableEntity findByStudentID(String studentID);
}
