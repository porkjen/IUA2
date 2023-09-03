package com.example.demo.repository;

import com.example.demo.dao.RequiredCourseEntityOuterMajorTC;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RCourseOMajorTCRepository extends MongoRepository<RequiredCourseEntityOuterMajorTC, String> {

    //在資料庫item內查找c_category&c_grade(regex is include)
    @Query(value = "{'c_category': ?0,'c_grade': {$regex: ?1}}")
    List<RequiredCourseEntityOuterMajorTC> findByCategoryAndGrade(String c_category, String c_grade);
}
