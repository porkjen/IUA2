package com.example.demo;

import com.example.demo.dao.SavedEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SavedRepository extends MongoRepository<SavedEntity, String> {
    public SavedEntity findByStudentID(String studentID);
}
