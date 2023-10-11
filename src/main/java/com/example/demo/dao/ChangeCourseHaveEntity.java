package com.example.demo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("change_Course_HaveCollection")
public class ChangeCourseHaveEntity {
    private String id;
    private String time;
    private int have;
    private boolean pair;

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

    public boolean isPair() {
        return pair;
    }

    public void setPair(boolean pair) {
        this.pair = pair;
    }

    public List<ChangeCourseHaveEntity> initialization(){
        List<ChangeCourseHaveEntity> init = new ArrayList<>();
        for(int i=1;i<=7;i++){
            for(int j=1;j<=14;j++){
                int classtime = i*100+j;
                ChangeCourseHaveEntity c = new ChangeCourseHaveEntity();
                c.setTime(String.valueOf(classtime));
                c.setHave(0);
                init.add(c);
            }
        }
        return init;
    }

}
