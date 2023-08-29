package com.example.demo.repository;

import com.example.demo.dao.RequiredCourseEntityOuterMajorCE;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RCourseOMajorCERepository extends MongoRepository<RequiredCourseEntityOuterMajorCE, String> {

    //在資料庫item內查找c_category&c_grade
    @Query(value = "{'c_category': ?0,'c_grade': {$regex: ?1}}")
    List<RequiredCourseEntityOuterMajorCE> findByCategoryAndGrade(String c_category, String c_grade);

}
