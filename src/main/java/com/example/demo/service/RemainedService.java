package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import com.example.demo.FinishedCourse;
import com.example.demo.FinishedCourseList;
import com.example.demo.FinishedRepository;
import com.example.demo.RemainCredit;

@Service
public class RemainedService {
    @Autowired
    FinishedRepository fRepository;

    public RemainCredit computeCredit(String studentID){
        FinishedCourseList fcl = fRepository.findByStudentID(studentID);
        ArrayList<FinishedCourse> fc = fcl.getFinishedCourses();
        RemainCredit remainCredit = new RemainCredit();

        int required = 0;
        int deptOptional = 0;
        int optional = 0;
        int general = 0;
        int kernal = 0; 
        int pe = 0;

        for(FinishedCourse f : fc){
            int credit = Integer.parseInt(f.getCredit());
            if(f.getCategory().equals("必修"))  required += credit;
            else if(f.getCategory().equals("選修")) optional += credit;
        }

        //計算

        return remainCredit;
    }
}
