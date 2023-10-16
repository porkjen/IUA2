package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.dao.WebPushEntity;

public interface WebPushRepository extends MongoRepository<WebPushEntity, String>{
    boolean existsByStudentID(String studentID);

    WebPushEntity findByStudentID(String studentID);

    WebPushEntity findByToken(String token);
}
