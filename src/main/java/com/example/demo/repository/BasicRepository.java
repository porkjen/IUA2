package com.example.demo.repository;
import com.example.demo.dao.BasicEntity;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface BasicRepository extends MongoRepository<BasicEntity, String> {
    BasicEntity findByStudentID(String studentID);
    BasicEntity findByNickname(String nickname);

}
