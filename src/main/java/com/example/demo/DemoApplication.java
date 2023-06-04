package com.example.demo;

import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);//啟動
		applicationContext.start();
	}
	@Component
	class ContextStartedListener implements ApplicationListener<ContextStartedEvent> {

		@Autowired
		private TodoService service;

		@Override
		public void onApplicationEvent(ContextStartedEvent event) {
			System.out.println("run");
		}
	}

}
