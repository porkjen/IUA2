package com.example.demo;

import com.example.demo.dao.FoodEntity;
import com.example.demo.service.TodoService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class getData {
    @Autowired
    TodoService todoService;//取得Service物件

    List<FoodEntity> getRData(String location){
        List<FoodEntity> restaurantList = new ArrayList<>();
        try {
            String pagetoken;
            getApi res = new getApi();

            //first 20 restaurants
            String res1 = res.get("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+
                    location +
                    "&type=restaurant&language=zh-TW&key=AIzaSyA2DfKC5NYIGGTxNnoCs6Bbr6WVPbVHmes&rankby=distance");
            JSONObject jsonObject = new JSONObject(res1);
            //get page_token
            pagetoken = jsonObject.getString("next_page_token");
            setting(restaurantList, jsonObject);

            //21-40 restaurants
            Thread.sleep(6000);
            String res2 = res.get("https://maps.googleapis.com/maps/api/place/nearbysearch/json?key" +
                    "=AIzaSyA2DfKC5NYIGGTxNnoCs6Bbr6WVPbVHmes&pagetoken=" + pagetoken);
            jsonObject = new JSONObject(res2);
            pagetoken = jsonObject.getString("next_page_token");
            setting(restaurantList, jsonObject);

            //31-60 restaurants
            String res3 = res.get("https://maps.googleapis.com/maps/api/place/nearbysearch/json?key" +
                    "=AIzaSyA2DfKC5NYIGGTxNnoCs6Bbr6WVPbVHmes&pagetoken=" + pagetoken);
            jsonObject = new JSONObject(res3);
            setting(restaurantList, jsonObject);

            int i = 0;
            for(FoodEntity r : restaurantList){
                System.out.println(i);
                System.out.println("name : "+ r.getStore()+"\nrating : "+ r.getRating()+
                        "\naddress : "+ r.getAddress()+"\nopen time : \n"+ r.getWeekday_text());
                i++;
            }

        }
        catch (JSONException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return restaurantList;
    }
    List<FoodEntity> setting(List<FoodEntity> arrayList, JSONObject jsonObject) {
        JSONArray resultsArray = (JSONArray) jsonObject.get("results");
        String getString;
        String placeID;
        getApi getDATA = new getApi();
        for (int i = 0; i < 20; i++) {
            FoodEntity restaurant = new FoodEntity();
            restaurant.setStore(resultsArray.getJSONObject(i).getString("name"));//restaurant
            placeID = resultsArray.getJSONObject(i).getString("place_id");
            //rating
            if (!resultsArray.getJSONObject(i).has("rating")) restaurant.setRating(0);
            else restaurant.setRating(resultsArray.getJSONObject(i).getDouble("rating"));
            //how many person rating
            if (!resultsArray.getJSONObject(i).has("user_ratings_total")) restaurant.setRating_num(5);
            else restaurant.setRating(resultsArray.getJSONObject(i).getInt("rating"));
            //details api
            getString = getDATA.get("https://maps.googleapis.com/maps/api/place/details/json?key" +
                    "=AIzaSyA2DfKC5NYIGGTxNnoCs6Bbr6WVPbVHmes&language=zh-TW&placeid=" + placeID);
            jsonObject = new JSONObject(getString);
            if(jsonObject.getJSONObject("result").has("current_opening_hours")){
                JSONArray timeArr =
                        jsonObject.getJSONObject("result").getJSONObject("current_opening_hours").getJSONArray("weekday_text");
                for (int j = 0; j < 7; j++) {
                    restaurant.setWeekday_text(timeArr.getString(j), j);
                }
            }
            restaurant.setAddress(jsonObject.getJSONObject("result").getString("formatted_address"));
            restaurant.setURL(jsonObject.getJSONObject("result").getString("url"));
            if(jsonObject.getJSONObject("result").has("reviews")) {
                int count = jsonObject.getJSONObject("result").getJSONArray("reviews").length();
                for (int j = 0; j < count; j++) {
                    FoodEntity.p userReview = new FoodEntity.p();
                    userReview.setP_name(jsonObject.getJSONObject("result").getJSONArray("reviews").getJSONObject(j).getString("author_name"));
                    userReview.setP_review(jsonObject.getJSONObject("result").getJSONArray("reviews").getJSONObject(j).getString("text"));
                    userReview.setP_time(jsonObject.getJSONObject("result").getJSONArray("reviews").getJSONObject(j).getString("relative_time_description"));
                    userReview.setP_rate(jsonObject.getJSONObject("result").getJSONArray("reviews").getJSONObject(j).getInt("rating"));
                    userReview.setP_studentID("IUA");
                    restaurant.setReview(userReview);
                }
            }
            restaurant.setStudentID("IUA");
            restaurant.setNickname("IUA");
            restaurant.setPost_time(DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDateTime.now()));
            arrayList.add(restaurant);
            //foodRepository.save(restaurant);
        }
        return arrayList;
    }
    FoodEntity FoodByCid(String cid){
        getApi res = new getApi();
        FoodEntity restaurant = new FoodEntity();
        String result = res.get("https://maps.googleapis.com/maps/api/place/details/json?cid="+cid+"&key=AIzaSyC_zYmWyI7RDMLhO7p5XRt0pqlxprBKAuk&language=zh-TW");
        JSONObject jsonObject = new JSONObject(result);
        restaurant.setStore(jsonObject.getJSONObject("result").getString("name"));
        restaurant.setRating(jsonObject.getJSONObject("result").getDouble("rating"));
        restaurant.setRating_num(jsonObject.getJSONObject("result").getInt("user_ratings_total"));
        if(jsonObject.getJSONObject("result").has("opening_hours")){
            JSONArray timeArr =
                    jsonObject.getJSONObject("result").getJSONObject("opening_hours").getJSONArray("weekday_text");
            for (int j = 0; j < 7; j++) {
                restaurant.setWeekday_text(timeArr.getString(j), j);
            }
        }
        restaurant.setAddress(jsonObject.getJSONObject("result").getString("formatted_address"));
        restaurant.setURL(jsonObject.getJSONObject("result").getString("URL"));
        if(jsonObject.getJSONObject("result").has("reviews")) {
            int count = jsonObject.getJSONObject("result").getJSONArray("reviews").length();
            for (int j = 0; j < count; j++) {
                FoodEntity.p userReview = new FoodEntity.p();
                userReview.setP_name(jsonObject.getJSONObject("result").getJSONArray("reviews").getJSONObject(j).getString("author_name"));
                userReview.setP_review(jsonObject.getJSONObject("result").getJSONArray("reviews").getJSONObject(j).getString("text"));
                userReview.setP_time(jsonObject.getJSONObject("result").getJSONArray("reviews").getJSONObject(j).getString("relative_time_description"));
                userReview.setP_rate(jsonObject.getJSONObject("result").getJSONArray("reviews").getJSONObject(j).getInt("rating"));
                userReview.setP_studentID("IUA");
                restaurant.setReview(userReview);
            }
        }
        return restaurant;
    }
}
