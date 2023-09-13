package com.example.demo.repository;

import com.example.demo.dao.EnglishCourseEntity;
import com.example.demo.dao.ForeignLanguageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ForeignLanguageCourseRepository extends MongoRepository<ForeignLanguageEntity, String> {
    List<ForeignLanguageEntity> findByNameContaining(String name);
}
