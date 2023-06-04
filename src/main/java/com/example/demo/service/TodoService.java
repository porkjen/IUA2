package com.example.demo.service;


import com.example.demo.dao.BasicEntity;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    //@Autowired
    //TodoDao todoDao; // 取得Dao物件
    public BasicEntity printO(BasicEntity request){
        System.out.println(request.getStudentID()+" "+request.getPassword());
        return request;
    }
}
