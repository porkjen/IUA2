package com.example.demo.dao;

public class PushNotificationRequest {
    private String title;
    private String message;
    private String topic;
    private String token;
    private String link;
    
  	public PushNotificationRequest() {
		super();
	}
  
  
	public PushNotificationRequest(String title, String message, String topic, String token) {
		super();
		this.title = title;
		this.message = message;
		this.topic = topic;
		this.token = token;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
