package com.example.demo;

public class RemainCredit{
    int required;       //必修
    int deptOptional;   //系選
    int optional;       //系外選
    int general;        //通識
    int kernal;         //核心
    int pe;             //體育
    boolean eng;
    boolean swimming;

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

    public void setEng(boolean eng){
        this.eng = eng;
    }

    public void setSwimming(boolean swimming){
        this.swimming = swimming;
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

    public boolean getEng(){
        return eng;
    }

    public boolean getSwimming(){
        return swimming;
    }
}