package com.example.demo.dao;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;

@Document(collection = "WebPushCollection")
public class WebPushEntity {
    private String id;
    private String studentID;
    private String token;
    private Boolean update = true;
    private ArrayList<PushNotificationRequest> noticications;

    public WebPushEntity(){}

    public void setStudentID(String id){
        this.studentID = id;
    }
    public String getStudentID(){
        return studentID;
    }
    public void setToken(String token){
        this.token = token;
    }
    public String getToken(){
        return token;
    }
    public void setUpdate(Boolean update){
        this.update = update;
    }
    public Boolean getUpdate(){
        return update;
    }
    public void setNotifications(ArrayList<PushNotificationRequest> noticications){
        this.noticications = noticications;
    }
    public ArrayList<PushNotificationRequest> getNotifications(){
        return noticications;
    }
}
