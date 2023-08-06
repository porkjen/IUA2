package com.example.demo;

import com.example.demo.dao.RequiredCourseEntityG2select;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RCourseG2SelectRepository extends MongoRepository<RequiredCourseEntityG2select, String> {
    @Query(value = "{'c_category': ?0}")
    List<RequiredCourseEntityG2select> findByc_category(String c_category);
}

