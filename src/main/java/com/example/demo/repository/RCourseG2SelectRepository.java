package com.example.demo.repository;

import com.example.demo.dao.RequiredCourseEntityG2select;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RCourseG2SelectRepository extends MongoRepository<RequiredCourseEntityG2select, String> {
    @Query(value = "{'c_category': ?0}")
    List<RequiredCourseEntityG2select> findByc_category(String c_category);

    @Query(value = "{'c_name': ?0}")
    RequiredCourseEntityG2select findByc_name(String c_name);
}

