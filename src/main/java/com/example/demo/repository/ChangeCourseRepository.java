package com.example.demo.repository;

import com.example.demo.dao.ChangeCourseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChangeCourseRepository extends MongoRepository<ChangeCourseEntity, String> {
    ChangeCourseEntity findByPostId(String postId);
    List<ChangeCourseEntity> findByCategory(String category);
    ChangeCourseEntity findFirstByOrderByIdDesc();
}
