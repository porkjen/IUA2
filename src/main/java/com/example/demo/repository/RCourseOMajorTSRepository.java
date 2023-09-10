package com.example.demo.repository;

import com.example.demo.dao.RequiredCourseEntityOuterMajorTS;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RCourseOMajorTSRepository extends MongoRepository<RequiredCourseEntityOuterMajorTS, String> {

    //在資料庫item內查找c_category&c_grade(regex is include)
    @Query(value = "{'c_category': ?0,'c_grade': {$regex: ?1}}")
    List<RequiredCourseEntityOuterMajorTS> findByCategoryAndGrade(String c_category, String c_grade);

    //在資料庫item內查找c_number&c_grade(regex is include)
    @Query(value = "{'c_number': ?0,'c_grade': {$regex: ?1}}")
    List<RequiredCourseEntityOuterMajorTS> findByNumberAndGrade(String c_number, String c_grade);
}
