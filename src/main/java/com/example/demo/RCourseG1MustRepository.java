package com.example.demo;

import com.example.demo.dao.RequiredCourseEntityG1must;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RCourseG1MustRepository extends MongoRepository<RequiredCourseEntityG1must, String> {

    //在資料庫item內查找c_category
    @Query(value = "{'c_category': ?0}")
    List<RequiredCourseEntityG1must> findByc_category(String c_category);
}
