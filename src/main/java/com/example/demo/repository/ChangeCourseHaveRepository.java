package com.example.demo.repository;

import com.example.demo.dao.ChangeCourseHaveEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangeCourseHaveRepository extends MongoRepository<ChangeCourseHaveEntity, String> {
    ChangeCourseHaveEntity findByTime(String time);
}
