package com.example.demo;

import com.example.demo.dao.FoodEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends MongoRepository<FoodEntity, String> {
    FoodEntity findByStore(String store);
    FoodEntity findFirstByOrderByIdDesc();
    FoodEntity findByPostId(String postId);
    List<FoodEntity> findAllByOrderByIdDesc();
    FoodEntity deleteByPostId(String postId);
    List<FoodEntity> findAllByOrderByRatingDesc();
    List<FoodEntity> findAllByOrderByRatingAsc();
}
