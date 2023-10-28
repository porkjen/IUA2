package com.example.demo.repository;

import com.example.demo.dao.HouseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.ArrayList;

public interface HouseRepository extends MongoRepository<HouseEntity, String> {
    HouseEntity findFirstByOrderByIdDesc();
    HouseEntity findByPostId(String post_id);
    ArrayList<HouseEntity> findByStyleAndArea(String style, String Area);
    HouseEntity deleteByPostId(String post_id);
    List<HouseEntity> findByStatus(String status);
}
