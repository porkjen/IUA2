package com.example.demo;

import com.example.demo.dao.ChangeCourseHaveEntity;
import com.example.demo.dao.FoodEntity;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);//啟動
		applicationContext.start();
	}
	// @Component
	// class ContextStartedListener implements ApplicationListener<ContextStartedEvent> {

	// 	@Autowired
	// 	private TodoService service;
	// 	@Autowired
	// 	FoodRepository foodRepository;
	// 	@Autowired
	// 	ChangeCourseHaveRepository changeCourseHaveRepository;

<<<<<<< HEAD
	// 	@Override
	// 	public void onApplicationEvent(ContextStartedEvent event) {
	// 		System.out.println("run");
	// 		/*TaskConfiguration taskConfiguration = new TaskConfiguration();
	// 		LocalDateTime localDateTime = LocalDateTime.now();
	// 		taskConfiguration.timeTableTiming(0);*/
	// 		getData gd = new getData();
	// 		NextPostId nextPostId = new NextPostId();
	// 		List<FoodEntity> restaurantList = gd.getRData("25.131736,121.782001");//深溪路
	// 		for(FoodEntity r: restaurantList){
	// 			if(foodRepository.findFirstByOrderByIdDesc()==null){r.setPostId("F00001");}
	// 			else{
	// 				System.out.println("else");
	// 				System.out.println(foodRepository.findFirstByOrderByIdDesc().getPostId());
	// 				r.setPostId(nextPostId.getNextFoodString(foodRepository.findFirstByOrderByIdDesc().getPostId()));
	// 			}
	// 			foodRepository.save(r);
	// 		}
	// 		restaurantList = gd.getRData("25.136418,121.787990");//新豐街
	// 		for(FoodEntity r: restaurantList){
	// 			if(foodRepository.findByStore(r.getStore())==null){
	// 				if(foodRepository.findFirstByOrderByIdDesc()==null){r.setPostId("F00001");}
	// 				else{
	// 					r.setPostId(nextPostId.getNextFoodString(foodRepository.findFirstByOrderByIdDesc().getPostId()));
	// 				}
	// 				foodRepository.save(r);
	// 			}
	// 		}
	// 		restaurantList = gd.getRData("25.154205,121.768702");//和平島
	// 		for(FoodEntity r: restaurantList){
	// 			if(foodRepository.findByStore(r.getStore())==null){
	// 				if(foodRepository.findFirstByOrderByIdDesc()==null){r.setPostId("F00001");}
	// 				else{
	// 					r.setPostId(nextPostId.getNextFoodString(foodRepository.findFirstByOrderByIdDesc().getPostId()));
	// 				}
	// 				foodRepository.save(r);
	// 			}
	// 		}
	// 		restaurantList = gd.getRData("25.144637,121.767681");//祥豐街
	// 		for(FoodEntity r: restaurantList){
	// 			if(foodRepository.findByStore(r.getStore())==null){
	// 				if(foodRepository.findFirstByOrderByIdDesc()==null){r.setPostId("F00001");}
	// 				else{
	// 					r.setPostId(nextPostId.getNextFoodString(foodRepository.findFirstByOrderByIdDesc().getPostId()));
	// 				}
	// 				foodRepository.save(r);
	// 			}
	// 		}
	// 		restaurantList = gd.getRData("25.151030,121.772286");//海大
	// 		for(FoodEntity r: restaurantList){
	// 			if(foodRepository.findByStore(r.getStore())==null){
	// 				if(foodRepository.findFirstByOrderByIdDesc()==null){r.setPostId("F00001");}
	// 				else{
	// 					r.setPostId(nextPostId.getNextFoodString(foodRepository.findFirstByOrderByIdDesc().getPostId()));
	// 				}
	// 				foodRepository.save(r);
	// 			}
	// 		}
	// 		System.out.println("finish!");
	// 		/*ChangeCourseHaveEntity changeCourseHaveEntity = new ChangeCourseHaveEntity();
	// 		for(ChangeCourseHaveEntity c : changeCourseHaveEntity.initialization()){
	// 			System.out.println(c.getTime()+" : "+c.getHave());
	// 			changeCourseHaveRepository.save(c);
	// 		}*/
=======
		@Override
		public void onApplicationEvent(ContextStartedEvent event) {
			System.out.println("run");
			/*TaskConfiguration taskConfiguration = new TaskConfiguration();
			LocalDateTime localDateTime = LocalDateTime.now();
			taskConfiguration.timeTableTiming(0);*/
			/*getData gd = new getData();
			NextPostId nextPostId = new NextPostId();
			List<FoodEntity> restaurantList = gd.getRData("25.131736,121.782001");//深溪路
			for(FoodEntity r: restaurantList){
				if(foodRepository.findFirstByOrderByIdDesc()==null){r.setPostId("F00001");}
				else{
					System.out.println("else");
					System.out.println(foodRepository.findFirstByOrderByIdDesc().getPostId());
					r.setPostId(nextPostId.getNextFoodString(foodRepository.findFirstByOrderByIdDesc().getPostId()));
				}
				foodRepository.save(r);
			}
			restaurantList = gd.getRData("25.136418,121.787990");//新豐街
			for(FoodEntity r: restaurantList){
				if(foodRepository.findByStore(r.getStore())==null){
					if(foodRepository.findFirstByOrderByIdDesc()==null){r.setPostId("F00001");}
					else{
						r.setPostId(nextPostId.getNextFoodString(foodRepository.findFirstByOrderByIdDesc().getPostId()));
					}
					foodRepository.save(r);
				}
			}
			restaurantList = gd.getRData("25.154205,121.768702");//和平島
			for(FoodEntity r: restaurantList){
				if(foodRepository.findByStore(r.getStore())==null){
					if(foodRepository.findFirstByOrderByIdDesc()==null){r.setPostId("F00001");}
					else{
						r.setPostId(nextPostId.getNextFoodString(foodRepository.findFirstByOrderByIdDesc().getPostId()));
					}
					foodRepository.save(r);
				}
			}
			restaurantList = gd.getRData("25.144637,121.767681");//祥豐街
			for(FoodEntity r: restaurantList){
				if(foodRepository.findByStore(r.getStore())==null){
					if(foodRepository.findFirstByOrderByIdDesc()==null){r.setPostId("F00001");}
					else{
						r.setPostId(nextPostId.getNextFoodString(foodRepository.findFirstByOrderByIdDesc().getPostId()));
					}
					foodRepository.save(r);
				}
			}
			restaurantList = gd.getRData("25.151030,121.772286");//海大
			for(FoodEntity r: restaurantList){
				if(foodRepository.findByStore(r.getStore())==null){
					if(foodRepository.findFirstByOrderByIdDesc()==null){r.setPostId("F00001");}
					else{
						r.setPostId(nextPostId.getNextFoodString(foodRepository.findFirstByOrderByIdDesc().getPostId()));
					}
					foodRepository.save(r);
				}
			}
			System.out.println("finish!");*/
			/*ChangeCourseHaveEntity changeCourseHaveEntity = new ChangeCourseHaveEntity();
			for(ChangeCourseHaveEntity c : changeCourseHaveEntity.initialization()){
				System.out.println(c.getTime()+" : "+c.getHave());
				changeCourseHaveRepository.save(c);
			}*/

		}
	}
>>>>>>> 479c7bdeeaf202f18f78c5a4c6b470343cf04327

	// 	}
	// }
}
