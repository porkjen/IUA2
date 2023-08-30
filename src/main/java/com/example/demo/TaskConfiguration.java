package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@Slf4j
public class TaskConfiguration implements SchedulingConfigurer {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    @Value("${scheduler.FoodUpdateCron}")
    private String foodUpdateCron;
    public void timeTableTiming(/*LocalDateTime localDateTime*/ int month, int day){
        System.out.println("call" + Calendar.getInstance().getTime());
        Calendar calendar = Calendar.getInstance();//Gets a calendar using the default time zone and locale. The Calendar returned is based on the current time in the default time zone with the default FORMAT locale.
        //set time
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

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // 使用cron expression可以動態設置循環間隔
        taskRegistrar.addTriggerTask(() -> {
            log.info(">>>>>>定時任務開始時間： {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS)));
            try {
                // do something
                log.info("定时任务具体业务逻辑，模拟业务逻辑处理......");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("美食定時更新失敗", e);
                Thread.currentThread().interrupt();
            }
            log.info(">>>>>>定時任務結束時間： {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS)));
        }, triggerContext -> {
            // 使用CronTrigger觸發器，可動態修改cron表達式來操作循環規則
            CronTrigger cronTrigger = new CronTrigger(foodUpdateCron);
            return cronTrigger.nextExecutionTime(triggerContext).toInstant();//下次執行時間
        });

    }
}