package com.example.demo.dao;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "G_CourseCollection")
public class GeneralCourse {
    private String name;
    private String number;
    private float rate;
    private String classroom;
    private String time;
    private String evaluation;
    private String subfield;
}
