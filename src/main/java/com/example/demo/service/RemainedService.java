package com.example.demo.service;


import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hpsf.Array;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Objects;

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

        int required = 67;       //必修
        int deptOptional = 43;   //系內選修
        int optional = 11;       //其他選修
        int general = 16;        //通識
        int kernal = 12;         //核心選修
        int pe = 4;             //體育
        
        ArrayList<String> requiredList = new ArrayList<String>();   //必修細項
        ArrayList<String> deptList = new ArrayList<String>();       //內選細項
        ArrayList<String> optionalList = new ArrayList<String>();   //其他細項
        ArrayList<String> generalList = new ArrayList<String>();    //通識細項
        ArrayList<String> kernalList = new ArrayList<String>();     //核心細項
        ArrayList<String> peList = new ArrayList<String>();         //體育細項

        int eecse = 0;          //電資學院
        int optOffset = 0;      //共同教育抵銷

        for(FinishedCourse f : fc){
            int credit = Integer.parseInt(f.getCredit());
            if(f.getCategory().equals("必修"))  required += credit;
            else if(f.getCategory().equals("選修")) optional += credit;
        }


        String[] kernalCourse = {"數位系統設計", "微處理器原理與組合語言", "計算機系統設計", "計算機結構", "程式語言", "資料庫系統", "軟體工程", "嵌入式系統設計", "系統程式", "編譯器", "人工智慧"};

        for(FinishedCourse f : fc){
            if(NumberUtils.isCreatable(f.getCredit())){
                int credit = Integer.parseInt(f.getCredit());
                String courseName = f.getName();
                String courseCredit = f.getCredit();
                String item = courseName + ": " + courseCredit + "學分";
                String dept = f.getDepartment();
                System.out.println("course: " + courseName + "\ncredit: " + courseCredit);
                if(f.getCategory().equals("必修")){
                    if(dept.equals("資訊工程學系")){
                        required -= credit;
                        requiredList.add(item);
                    }
                    if(dept.equals("體育室")){
                        peList.add(item);
                        pe -= 1;
                    }
                    if(required < 0)
                        required = 0;
                }
                else if(f.getCategory().equals("選修")){
                    if(!dept.isEmpty()){
                        if(dept.equals("資訊工程學系") || dept.equals("電機工程學系") || dept.equals("電機資訊學院")){
                            if(f.getName().contains("資工系專題") || f.getName().equals("資訊專題討論")){
                                required -= credit;
                                requiredList.add(item);
                                if(required < 0)
                                    required = 0;
                            }
                            else{
                                if(dept.equals("電機工程學系") || dept.equals("電機資訊學院")){
                                    eecse += credit;
                                    if(eecse > 6)
                                        eecse = 6;
                                    deptOptional -= eecse;
                                }
                                else
                                    deptOptional -= credit;
                                if(deptOptional < 0)
                                    deptOptional = 0;
                                deptList.add(item);
                            }
                            for(String k : kernalCourse){
                                if(f.getName().equals(k)){
                                    kernal -= credit;
                                    kernalList.add(item);
                                    if(kernal < 0)
                                        kernal = 0;
                                    break;
                                }
                            }
                        }
                        else{
                            optional -= credit;
                            optionalList.add(item);
                            if(optional < 0)
                                optional = 0;
                        }
                    }

                }
                else if(f.getCategory().equals("通識")){
                    general -= credit;
                    generalList.add(item);
                    if(general < 0){
                        general = 0;
                        optOffset += credit;
                        if(optOffset < 8){
                            optional -= optOffset;
                            if(optional < 0)
                                optional = 0;
                        }
                    }
                }
                else if(f.getCategory().equals("抵")){
                    if(f.getName().equals("游泳畢業門檻"))  remainCredit.setSwimming("通過");
                    if(f.getName().equals("英文畢業門檻")) remainCredit.setEng("通過");
                }
            }

        }

        remainCredit.setRequired(required);
        remainCredit.setDeptOptional(deptOptional);
        remainCredit.setKernal(kernal);
        remainCredit.setOptional(optional);
        remainCredit.setGeneral(general);
        remainCredit.setPE(pe);



        return remainCredit;
    }
}
