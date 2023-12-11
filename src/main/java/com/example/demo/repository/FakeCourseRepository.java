package com.example.demo.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.dao.FakeCourseData;

public interface FakeCourseRepository extends MongoRepository<FakeCourseData, String>{
    boolean existsByCourseNumber(String number);

    FakeCourseData findByCourseNumber(String number);
}

