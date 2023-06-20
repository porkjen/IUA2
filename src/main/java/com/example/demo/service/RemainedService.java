package com.example.demo.service;


import org.apache.poi.hpsf.Array;
import org.checkerframework.checker.units.qual.C;
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

        int required = 0;       //必修
        int deptOptional = 0;   //系內選修
        int optional = 0;       //其他選修
        int general = 0;        //通識
        int kernal = 0;         //核心選修
        int pe = 0;             //體育

        for(FinishedCourse f : fc){
            int credit = Integer.parseInt(f.getCredit());
            if(f.getCategory().equals("必修"))  required += credit;
            else if(f.getCategory().equals("選修")) optional += credit;
        }


        String[] kernalCourse = {"數位系統設計", "微處理器原理與組合語言", "計算機系統設計", "計算機結構", "程式語言", "資料庫系統", "軟體工程", "嵌入式系統設計", "系統程式", "編譯器", "人工智慧"};

        for(FinishedCourse f : fc){
            int credit = Integer.parseInt(f.getCredit());
            if(f.getCategory().equals("必修")){
                required += credit;
                if(f.getDepartment().equals("體育室"))
                    pe += 1;
            }
            else if(f.getCategory().equals("選修")){
                if(f.getDepartment().equals("資訊工程學系")){
                    if(f.getName().contains("資工系專題") || f.getName().equals("資訊專題討論"))
                        required += credit;
                    else
                        deptOptional += credit;
                    for(String k : kernalCourse){
                        if(f.getName().equals(k)){
                            kernal += credit;
                            break;
                        }
                    }
                }
                else optional += credit;
            } 
            else if(f.getCategory().equals("通識")) general += credit;
            else if(f.getCategory().equals("抵")){
                if(f.getName().equals("游泳畢業門檻"))  remainCredit.setSwimming(true);
                else if(f.getCategory().equals("英文畢業門檻")) remainCredit.setEng(true);
            }
        }

        //計算
        remainCredit.setRequired(67-required);
        remainCredit.setDeptOptional(43-deptOptional);
        remainCredit.setKernal(12-kernal);
        remainCredit.setOptional(11-optional);
        remainCredit.setGeneral(16-general);
        remainCredit.setPE(4-pe);


        return remainCredit;
    }
}
