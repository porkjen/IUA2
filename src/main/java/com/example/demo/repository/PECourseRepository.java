package com.example.demo.repository;

import com.example.demo.dao.ForeignLanguageEntity;
import com.example.demo.dao.PECourseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PECourseRepository extends MongoRepository<PECourseEntity, String> {
    List<PECourseEntity> findByNameContaining(String name);
}
