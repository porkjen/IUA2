package com.example.demo;

import com.example.demo.dao.RequiredCourseEntityOuterMajorRW;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RCourseOMajorRWRepository extends MongoRepository<RequiredCourseEntityOuterMajorRW, String> {

    //在資料庫item內查找c_category&c_grade
    @Query(value = "{'c_category': ?0,'c_grade': {$regex: ?1}}")
    List<RequiredCourseEntityOuterMajorRW> findByCategoryAndGrade(String c_category, String c_grade);
}
