package com.example.demo;

import com.example.demo.dao.RequiredCourseEntityG4select;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RCourseG4SelectRepository extends MongoRepository<RequiredCourseEntityG4select, String> {
    @Query(value = "{'c_category': ?0}")
    List<RequiredCourseEntityG4select> findByc_category(String c_category);
}

