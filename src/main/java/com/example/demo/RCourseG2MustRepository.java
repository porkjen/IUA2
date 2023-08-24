package com.example.demo;

import com.example.demo.dao.RequiredCourseEntityG2must;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RCourseG2MustRepository extends MongoRepository<RequiredCourseEntityG2must, String> {
    @Query(value = "{'c_category': ?0}")
    List<RequiredCourseEntityG2must> findByc_category(String c_category);
}
