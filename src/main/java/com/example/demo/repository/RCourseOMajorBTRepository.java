package com.example.demo.repository;

import com.example.demo.dao.RequiredCourseEntityOuterMajorBT;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RCourseOMajorBTRepository extends MongoRepository<RequiredCourseEntityOuterMajorBT, String> {

    //在資料庫item內查找c_category&c_grade(regex is include)
    @Query(value = "{'c_category': ?0,'c_grade': {$regex: ?1}}")
    List<RequiredCourseEntityOuterMajorBT> findByCategoryAndGrade(String c_category, String c_grade);

}
