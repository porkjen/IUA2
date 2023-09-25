package com.example.demo.repository;

import com.example.demo.dao.ChatroomApiEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChatRoomApiRepository extends MongoRepository<ChatroomApiEntity, String> {

    @Query(value = "{'FirstStudentID': ?0,'SecondStudentID': ?1}")
    ChatroomApiEntity findByStudentID(String FirstStudentID, String SecondStudentID);

    @Query(value = "{'roomApi': ?0}")
    ChatroomApiEntity findByRoomApi(String roomApi);
}
