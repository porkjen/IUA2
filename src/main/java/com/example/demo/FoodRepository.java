package com.example.demo;

import com.example.demo.dao.FoodEntity;
import com.example.demo.dao.HouseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends MongoRepository<FoodEntity, String> {
    FoodEntity findByStore(String store);
    FoodEntity findFirstByOrderByIdDesc();
    FoodEntity findByPostId(String postid);
    List<FoodEntity> findAllByOrderByIdDesc();
    FoodEntity deleteByPostId(String post_id);
}
