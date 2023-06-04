package com.example.demo;

import com.example.demo.dao.HouseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HouseRepository extends MongoRepository<HouseEntity, String> {
    HouseEntity findFirstByOrderByIdDesc();
    HouseEntity findByPostId(String post_id);
}
