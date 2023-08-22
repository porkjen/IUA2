package com.example.demo.repository;

import com.example.demo.dao.RequiredCourseEntityG4must;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RCourseG4MustRepository extends MongoRepository<RequiredCourseEntityG4must, String> {
    @Query(value = "{'c_category': ?0}")
    List<RequiredCourseEntityG4must> findByc_category(String c_category);
}
