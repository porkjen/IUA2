package com.example.demo.repository;

import com.example.demo.dao.HouseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HouseRepository extends MongoRepository<HouseEntity, String> {
    HouseEntity findFirstByOrderByIdDesc();
    HouseEntity findByPostId(String post_id);
    HouseEntity deleteByPostId(String post_id);
    List<HouseEntity> findByStatus(String status);
}
