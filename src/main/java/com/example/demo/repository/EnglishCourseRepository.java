package com.example.demo.repository;

import com.example.demo.dao.EnglishCourseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EnglishCourseRepository extends MongoRepository<EnglishCourseEntity, String> {
    List<EnglishCourseEntity> findByNameContaining(String name);
}
