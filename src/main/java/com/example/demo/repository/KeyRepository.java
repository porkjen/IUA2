package com.example.demo.repository;

import com.example.demo.dao.KeyEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KeyRepository extends MongoRepository<KeyEntity, String> {
    KeyEntity findByUse(String use);
}
