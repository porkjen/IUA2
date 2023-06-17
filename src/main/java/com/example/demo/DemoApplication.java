package com.example.demo;

import com.example.demo.dao.FoodEntity;
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
		@Autowired
		FoodRepository foodRepository;

		@Override
		public void onApplicationEvent(ContextStartedEvent event) {
			System.out.println("run");
			/*getData gd = new getData();
			NextPostId nextPostId = new NextPostId();
			List<FoodEntity> restaurantList = gd.getRData("25.151030,121.772286");//中心點
			for(FoodEntity r: restaurantList){
				if(foodRepository.findFirstByOrderByIdDesc()==null){r.setPostId("F00001");}
				else{
					System.out.println("else");
					System.out.println(foodRepository.findFirstByOrderByIdDesc().getPostId());
					r.setPostId(nextPostId.getNextFoodString(foodRepository.findFirstByOrderByIdDesc().getPostId()));
				}
				foodRepository.save(r);
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
			restaurantList = gd.getRData("25.131736,121.782001");//深溪路
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
		}
	}

}
