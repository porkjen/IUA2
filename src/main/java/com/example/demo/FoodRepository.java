package com.example.demo;

import com.example.demo.dao.FoodEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends MongoRepository<FoodEntity, String> {
    FoodEntity findByStore(String store);
    FoodEntity findFirstByOrderByIdDesc();
}
