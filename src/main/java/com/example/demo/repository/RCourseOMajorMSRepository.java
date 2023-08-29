package com.example.demo.repository;

import com.example.demo.dao.RequiredCourseEntityOuterMajorMS;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RCourseOMajorMSRepository extends MongoRepository<RequiredCourseEntityOuterMajorMS, String> {

    //在資料庫item內查找c_category&c_grade
    @Query(value = "{'c_category': ?0,'c_grade': {$regex: ?1}}")
    List<RequiredCourseEntityOuterMajorMS> findByCategoryAndGrade(String c_category, String c_grade);

   /* @Query(value = "{'c_category': ?0, 'c_grade': {$regex: ?1} }")
    List<RequiredCourseEntityOuterMajorMS> findByCategoryAndGrade(String c_category, String c_grade);

    default List<RequiredCourseEntityOuterMajorMS> findByCategoryAndDynamicGrade(String c_category, String originalGrade) {
        System.out.println("123456789");
        String concatenatedGradeA = originalGrade + "年A班";
        String concatenatedGradeB = originalGrade + "年B班";

        List<RequiredCourseEntityOuterMajorMS> resultA = findByCategoryAndGrade(c_category, concatenatedGradeA);
        List<RequiredCourseEntityOuterMajorMS> resultB = findByCategoryAndGrade(c_category, concatenatedGradeB);

        resultA.addAll(resultB);

        return resultA;
    }
*/

}
