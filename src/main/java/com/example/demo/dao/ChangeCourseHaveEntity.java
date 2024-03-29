package com.example.demo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("change_Course_HaveCollection")
public class ChangeCourseHaveEntity {
    private String id;
    private String time;
    private int have;
    private int pair;
    private int mine = 0; //0, 1

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getHave() {
        return have;
    }

    public void setHave(int have) {
        this.have = have;
    }

    public int getPair() {
        return pair;
    }

    public void setPair(int pair) {
        this.pair = pair;
    }
    public void setPair() {
        this.pair++;
    }


    public int getMine() {
        return mine;
    }

    public void setMine() {
        this.mine++;
    }


    public List<ChangeCourseHaveEntity> initialization(){
        List<ChangeCourseHaveEntity> init = new ArrayList<>();
        for(int i=1;i<=7;i++){
            for(int j=1;j<=14;j++){
                int classtime = i*100+j;
                ChangeCourseHaveEntity c = new ChangeCourseHaveEntity();
                c.setTime(String.valueOf(classtime));
                c.setHave(0);
                c.setPair(0);
                init.add(c);
            }
        }
        return init;
    }

}
