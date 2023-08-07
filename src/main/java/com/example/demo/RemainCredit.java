package com.example.demo;

import java.util.ArrayList;

public class RemainCredit{
    int required;       //必修
    int deptOptional;   //系選
    int optional;       //系外選
    int general;        //通識
    int kernal;         //核心
    int pe;             //體育

    ArrayList<String> requiredList;
    ArrayList<String> deptList;
    ArrayList<String> optionalList;
    ArrayList<String> generalList;
    ArrayList<String> kernalList;
    ArrayList<String> peList;

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

    public void setEng(String eng){
        this.eng = eng;
    }

    public void setSwimming(String swimming){
        this.swimming = swimming;
    }

    public void setReqList(ArrayList<String> reqList){
        this.requiredList = reqList;
    }

    public void setDeptList(ArrayList<String> deptList){
        this.deptList = deptList;
    }

    public void setoptList(ArrayList<String> optList){
        this.optionalList = optList;
    }

    public void setgeneralList(ArrayList<String> generalList){
        this.generalList = generalList;
    }

    public void setKernalList(ArrayList<String> kernalList){
        this.kernalList = kernalList;
    }

    public void setPeList(ArrayList<String> peList){
        this.peList = peList;
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

    public String getEng(){
        return eng;
    }

    public String getSwimming(){
        return swimming;
    }

    public ArrayList<String> getReqList(){
        return requiredList;
    }

    public ArrayList<String> getDeptList(){
        return deptList;
    }

    public ArrayList<String> getOptList(){
        return optionalList;
    }

    public ArrayList<String> getGeneralList(){
        return generalList;
    }

    public ArrayList<String> getKernalList(){
        return kernalList;
    }

    public ArrayList<String> getPeList(){
        return peList;
    }
}