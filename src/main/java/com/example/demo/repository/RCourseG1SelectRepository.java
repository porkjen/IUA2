package com.example.demo.repository;

import com.example.demo.dao.RequiredCourseEntityG1select;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RCourseG1SelectRepository extends MongoRepository<RequiredCourseEntityG1select, String> {
    @Query(value = "{'c_category': ?0}")
    List<RequiredCourseEntityG1select> findByc_category(String c_category);
}

