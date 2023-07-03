package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Map;

public class TaskConfiguration {
    public void timeTableTiming(LocalDateTime localDateTime, Map<String, String> timing){
        Calendar calendar = Calendar.getInstance();//Gets a calendar using the default time zone and locale. The Calendar returned is based on the current time in the default time zone with the default FORMAT locale.
        //If the locale contains the time zone with "tz" Unicode extension, that time zone is used instead.
    }
}
