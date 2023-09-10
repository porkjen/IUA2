package com.example.demo.repository;

import com.example.demo.dao.RequiredCourseEntityOuterMajorDA;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RCourseOMajorDARepository extends MongoRepository<RequiredCourseEntityOuterMajorDA, String> {

    //在資料庫item內查找c_category&c_grade(regex is include)
    @Query(value = "{'c_category': ?0,'c_grade': {$regex: ?1}}")
    List<RequiredCourseEntityOuterMajorDA> findByCategoryAndGrade(String c_category, String c_grade);

    //在資料庫item內查找c_number&c_grade(regex is include)
    @Query(value = "{'c_number': ?0,'c_grade': {$regex: ?1}}")
    List<RequiredCourseEntityOuterMajorDA> findByNumberAndGrade(String c_number, String c_grade);
}
