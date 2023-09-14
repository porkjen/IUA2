package com.example.demo.repository;

import com.example.demo.dao.ChatroomRecordEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChatroomRecordRepository extends MongoRepository<ChatroomRecordEntity, String> {

    //在資料庫item內查找atWhere
    @Query(value = "{'c_category': ?0}")
    List<ChatroomRecordEntity> findByRoom(String atWhere);
}
