package com.example.demo.repository;

import com.example.demo.dao.RequiredCourseEntityG3must;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RCourseG3MustRepository extends MongoRepository<RequiredCourseEntityG3must, String> {
    @Query(value = "{'c_category': ?0}")
    List<RequiredCourseEntityG3must> findByc_category(String c_category);
}
