package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.util.*;

public class TaskConfiguration {
    public void timeTableTiming(/*LocalDateTime localDateTime*/ int min){
        System.out.println("call" + Calendar.getInstance().getTime());
        Calendar calendar = Calendar.getInstance();//Gets a calendar using the default time zone and locale. The Calendar returned is based on the current time in the default time zone with the default FORMAT locale.
        calendar.set(Calendar.MINUTE, 4);
        Date time = calendar.getTime(); //Tue Jul 04 15:50:44 CST 2023
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("現在時間是 : " + Calendar.getInstance().getTime());
            }
        }, time);
    }
}
