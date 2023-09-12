package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.dao.NotificationCondition;

public interface NotificationRepository extends MongoRepository<NotificationCondition, String>{
    boolean existsByStudentID(String studentID);

    NotificationCondition findByStudentID(String studentID);
}
