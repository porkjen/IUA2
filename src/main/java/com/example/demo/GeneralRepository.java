package com.example.demo;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.dao.GeneralCourseEntity;

public interface GeneralRepository extends MongoRepository<GeneralCourseEntity, String>{
    @Query(value = "{'subfield': ?0}")
    List<GeneralCourseEntity> findBysubfield(String subfield);

}
