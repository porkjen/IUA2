package com.example.demo;

import java.util.ArrayList;

public class RemainCredit{
    int required;       //必修
    int deptOptional;   //系選
    int optional;       //系外選
    int general;        //通識
    int kernal;         //核心
    int pe;             //體育
    int chinese;        //國文
    ArrayList<CourseCredit> requiredList;
    ArrayList<CourseCredit> deptList;
    ArrayList<CourseCredit> optionalList;
    ArrayList<CourseCredit> generalList;
    ArrayList<CourseCredit> kernalList;
    ArrayList<CourseCredit> peList;
    ArrayList<CourseCredit> chList;
    String eng = "未通過";
    String swimming = "未通過";

    public RemainCredit(){}

    public void setRequired(int required){
        this.required = required;
    }

    public void setDeptOptional(int dept){
        this.deptOptional = dept;
    }

    public void setOptional(int optional){
        this.optional = optional;
    }

    public void setGeneral(int general){
        this.general = general;
    }

    public void setKernal(int kernal){
        this.kernal = kernal;
    }

    public void setPE(int pe){
        this.pe = pe;
    }

    public void setChinese(int ch){
        this.chinese = ch;
    }

    public void setEng(String eng){
        this.eng = eng;
    }

    public void setSwimming(String swimming){
        this.swimming = swimming;
    }

    public void setReqList(ArrayList<CourseCredit> reqList){
        this.requiredList = reqList;
    }

    public void setDeptList(ArrayList<CourseCredit> deptList){
        this.deptList = deptList;
    }

    public void setoptList(ArrayList<CourseCredit> optList){
        this.optionalList = optList;
    }

    public void setgeneralList(ArrayList<CourseCredit> generalList){
        this.generalList = generalList;
    }

    public void setKernalList(ArrayList<CourseCredit> kernalList){
        this.kernalList = kernalList;
    }

    public void setPeList(ArrayList<CourseCredit> peList){
        this.peList = peList;
    }

    public void setChList(ArrayList<CourseCredit> chList){
        this.chList = chList;
    }

    public int getRequired(){
        return required;
    }

    public int getDeptOptional(){
        return deptOptional;
    }

    public int getOptional(){
        return optional;
    }

    public int getGeneral(){
        return general;
    }

    public int getKernal(){
        return kernal;
    }

    public int getPE(){
        return pe;
    }

    public int getChinese(){
        return chinese;
    }

    public String getEng(){
        return eng;
    }

    public String getSwimming(){
        return swimming;
    }

    public ArrayList<CourseCredit> getReqList(){
        return requiredList;
    }

    public ArrayList<CourseCredit> getDeptList(){
        return deptList;
    }

    public ArrayList<CourseCredit> getOptList(){
        return optionalList;
    }

    public ArrayList<CourseCredit> getGeneralList(){
        return generalList;
    }

    public ArrayList<CourseCredit> getKernalList(){
        return kernalList;
    }

    public ArrayList<CourseCredit> getPeList(){
        return peList;
    }

    public ArrayList<CourseCredit> getChList(){
        return chList;
    }
}
