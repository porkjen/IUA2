package com.example.demo;

import com.example.demo.dao.ChangeCourseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChangeCourseRepository extends MongoRepository<ChangeCourseEntity, String> {
    ChangeCourseEntity findByPostId(String postId);
    ChangeCourseEntity findFirstByOrderByIdDesc();
}
