package com.example.demo.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.dao.GeneralCourseEntity;

public interface GeneralRepository extends MongoRepository<GeneralCourseEntity, String>{
    List<GeneralCourseEntity> findBySubfield(String field);

}
