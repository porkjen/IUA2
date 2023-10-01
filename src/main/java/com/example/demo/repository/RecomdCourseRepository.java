package com.example.demo.repository;

import com.example.demo.dao.RecommandCourseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RecomdCourseRepository extends MongoRepository<RecommandCourseEntity, String> {
    RecommandCourseEntity findByStudentID(String studentID);
}
