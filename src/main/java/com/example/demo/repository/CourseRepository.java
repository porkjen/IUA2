package com.example.demo.repository;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.dao.CourseEntity;

public interface CourseRepository extends MongoRepository<CourseEntity, String>{
    @Query("{'c_name': ?0}")
    List<CourseEntity> findByc_name(String name);

    @Query("{'c_number': ?0}")
    List<CourseEntity> findByc_number(String number);

    @Query("{'$and': [{'c_grade': ?0}, {'c_name': ?0}]}")
    List<CourseEntity> findByc_gradeAndc_name(String grade, String name);
}
