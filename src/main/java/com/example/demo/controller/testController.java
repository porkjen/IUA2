package com.example.demo.controller;

import com.example.demo.IntentManagement;
import com.example.demo.QA;
import com.example.demo.gifCrawler;
import com.example.demo.quote;
import com.example.demo.service.MailService;
import com.google.cloud.dialogflow.v2.Intent;
import com.google.cloud.dialogflow.v2.Intent.TrainingPhrase;
import com.google.cloud.dialogflow.v2.IntentsClient;
import com.google.cloud.dialogflow.v2.UpdateIntentRequest;
import com.google.common.collect.Lists;
import com.google.protobuf.FieldMask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.json.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.springframework.web.client.RestTemplate;
import static com.google.cloud.dialogflow.v2.UpdateIntentRequest.*;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)    //回傳的資料格式為 JSON
public class testController {

    List<String> gifCollection = new ArrayList<String>();
    List<String> quoteCollection = new ArrayList<String>();

    List<QA> IUAQA = new ArrayList<QA>();

    @Autowired
    private MailService mailService;

    private String gifLink;
    private String quote;

    public String getGIF(String intent){
        try{
            gifCrawler crawler = new gifCrawler();
            gifCollection = crawler.gifCrawler(intent);
            int i = 0;
            i = (int)(Math.random()*10)+1;
            gifLink = gifCollection.get(i);
        }catch(Exception e){
            System.out.println("wrong");
        }

        return gifLink;
    }

    public String getQuote(){
        try{
            quote crawler = new quote();
            quoteCollection = crawler.quoteCrawler();
            int i = 0;
            i = (int)(Math.random()*10)+1;
            quote = quoteCollection.get(i);
            System.out.println(quote);
        }catch(Exception e){
            System.out.println("wrong");
        }

        return quote;
    }


   //send mail
    public void sendMail(){

        mailService.sendMail("singyi0216@gmail.com","test test!","I love you soooo much!");
    }



    //MTH(心情樹洞)
    @ResponseBody
    @PostMapping("/test")
    public String merchantForInfo(@RequestBody String jsonObject) {

        JSONObject jsonObjectResponse = new JSONObject(jsonObject);
        if(jsonObjectResponse.getJSONObject("queryResult").getJSONObject("intent").get("displayName").equals("negative")|jsonObjectResponse.getJSONObject("queryResult").getJSONObject("intent").get("displayName").equals("loveYou")
            |jsonObjectResponse.getJSONObject("queryResult").getJSONObject("intent").get("displayName").equals("emo")){

            if(jsonObjectResponse.getJSONObject("queryResult").getJSONObject("intent").get("displayName").equals("negative")){
                mailService.sendMail("singyi0216@gmail.com","test test!","I love you soooo much!");
            }

            JSONArray fulfillmentMessagesJsonArray = new JSONArray(jsonObjectResponse.getJSONObject("queryResult").getJSONArray("fulfillmentMessages"));
            JSONObject message = fulfillmentMessagesJsonArray.getJSONObject(0);
            message.getJSONObject("text").getJSONArray("text").put("我愛你");
            JSONObject image = fulfillmentMessagesJsonArray.getJSONObject(1);
            image.getJSONObject("payload").getJSONArray("richContent").getJSONArray(0).getJSONObject(0).put("rawUrl", Arrays.asList(getGIF("negative")));

        }
/*
        else if(jsonObjectResponse.getJSONObject("queryResult").getJSONObject("intent").get("displayName").equals("Default Fallback Intent")){
            JSONArray fulfillmentMessagesJsonArray = new JSONArray(jsonObjectResponse.getJSONObject("queryResult").getJSONArray("fulfillmentMessages"));
            JSONObject message = fulfillmentMessagesJsonArray.getJSONObject(0);
            message.getJSONObject("text").getJSONArray("text").put(getQuote());
            JSONObject image = fulfillmentMessagesJsonArray.getJSONObject(1);
            image.getJSONObject("payload").getJSONArray("richContent").getJSONArray(0).getJSONObject(0).put("rawUrl", Arrays.asList(getGIF("negative")));

        }
*/
        else{
            jsonObjectResponse.getJSONObject("queryResult").put("fulfillmentText","taetae love you");
            //System.out.println(jsonObjectResponse.getJSONObject("queryResult"));
            JSONArray fulfillmentMessagesJsonArray = new JSONArray(jsonObjectResponse.getJSONObject("queryResult").getJSONArray("fulfillmentMessages"));
            JSONObject message = fulfillmentMessagesJsonArray.getJSONObject(0);
            message.getJSONObject("text").getJSONArray("text").put("希望有安慰到你!");
            JSONObject image = fulfillmentMessagesJsonArray.getJSONObject(1);
            image.getJSONObject("payload").getJSONArray("richContent").getJSONArray(0).getJSONObject(0).put("rawUrl", Arrays.asList(getGIF("sad")));

        }


        String jsonStr = jsonObjectResponse.getJSONObject("queryResult").toString();
        return jsonStr;
    }


    //連接googleSheet
   public void getQA(){

       String question,question1,question2,question3,answer,intentName;
       String url = "https://sheetdb.io/api/v1/20tre7elywmbd";
       RestTemplate restTemplate = new RestTemplate();
       String result = restTemplate.getForObject(url, String.class);

       JSONArray j;
       j = new JSONArray(result);
       for(int i=0;i<j.length();i++) {
           QA q = new QA();
           intentName = j.getJSONObject(i).getString("IntentName");
           question = j.getJSONObject(i).getString("Question");
           question1 = j.getJSONObject(i).getString("Question1");
           question2 = j.getJSONObject(i).getString("Question2");
           question3 = j.getJSONObject(i).getString("Question3");
           answer = j.getJSONObject(i).getString("Answer");
           q.setIntentName(intentName);
           q.setQ(question);
           q.setQ1(question1);
           q.setQ2(question2);
           q.setQ3(question3);
           q.setA(answer);
           IUAQA.add(q);
       }

   }

   public void getTrainingPhrase(String getIntentName) throws IOException {
       String projectId = "iua-xhes";

       // Create an intents client
       try (IntentsClient intentsClient = IntentsClient.create()) {
           // The name of the intent you want to retrieve training phrases for
           String intentName = getIntentName;

           // Construct the intent request
           Intent intent = intentsClient.getIntent(intentName);
           System.out.println("in");
           // Get the training phrases
           for (TrainingPhrase trainingPhrase : intent.getTrainingPhrasesList()) {
               System.out.println("trainingPhrase"+trainingPhrase.getPartsList());
           }
       }
   }

    //test讀取檔案
    @ResponseBody
    @PostMapping("/tt")
    public String returnResponse(@RequestBody String jsonObject) throws MalformedURLException {
        URL url = new URL("https://sheetdb.io/api/v1/20tre7elywmbd");

        JSONObject jsonObjectResponse = new JSONObject(jsonObject);
        String text,res="";

        text = jsonObjectResponse.getJSONObject("queryResult").getString("queryText");
        System.out.println(text);
        for(int i = 0;i<IUAQA.size();i++){
            //System.out.println(IUAQA.get(i).getQ());
            if(IUAQA.get(i).getQ().equals(text)){
                res = IUAQA.get(i).getA();
                break;
            }
        }
        getQA();
        //System.out.println(res);

        JSONArray fulfillmentMessagesJsonArray = new JSONArray(jsonObjectResponse.getJSONObject("queryResult").getJSONArray("fulfillmentMessages"));
        JSONObject message = fulfillmentMessagesJsonArray.getJSONObject(0);
        message.getJSONObject("text").getJSONArray("text").put(res);

        String jsonStr = jsonObjectResponse.getJSONObject("queryResult").toString();
        return jsonStr;
    }

    public void AddTrainingPhrase(){
        String projectId = "your-project-id";
        String locationId = "your-location-id";
        String intentId = "your-intent-id";
        String intentName = "projects/" + projectId + "/locations/" + locationId + "/agent/intents/" + intentId;

        // 创建一个IntentsClient对象
        try (IntentsClient intentsClient = IntentsClient.create()) {
            // 获取要更新的意图对象
            Intent intent = intentsClient.getIntent(intentName);

            // 创建一个新的训练短语对象
            TrainingPhrase newTrainingPhrase = TrainingPhrase.newBuilder()
                    .setType(TrainingPhrase.Type.EXAMPLE)
                    .addParts(TrainingPhrase.Part.newBuilder().setText("Hello").build())
                    .addParts(TrainingPhrase.Part.newBuilder().setText("world").setEntityType("@sys.any").build())
                    .build();

            // 添加新的训练短语到意图对象的训练短语列表中
            Intent updatedIntent = intent.toBuilder().addTrainingPhrases(newTrainingPhrase).build();

            // 创建UpdateIntentRequest对象
            UpdateIntentRequest request = newBuilder()
                    .setIntent(updatedIntent)
                    .setLanguageCode("en-US")
                    .setUpdateMask(FieldMask.newBuilder().addPaths("training_phrases").build())
                    .build();

            // 更新意图
            Intent finalIntent = intentsClient.updateIntent(request);
            System.out.println("Intent updated: " + finalIntent.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //自動新增intent
    @PostMapping("/create")
    public void test() throws IOException {

        List<Intent> intents = Lists.newArrayList();
        List ans = new ArrayList<>();
        List list = new ArrayList<>();
        boolean flag = true;
        getQA();
        IntentManagement intentManagement = new IntentManagement();
        intents = intentManagement.listIntents("iua-xhes");
        for(int i=0;i<IUAQA.size();i++){
            List testList = new ArrayList<>();
            for(int j=0;j<intents.size();j++){
                if(IUAQA.get(i).getIntentName().equals(intents.get(j).getDisplayName())){
                    //getTrainingPhrase("test");
                    flag = false;
                    break;
                }
            }
            if(flag){
                //Training phrases
                list.add(IUAQA.get(i).getQ());
                list.add(IUAQA.get(i).getQ1());
                list.add(IUAQA.get(i).getQ2());
                list.add(IUAQA.get(i).getQ3());
                //answer
                ans.add(IUAQA.get(i).getA());
                intentManagement.createIntent(IUAQA.get(i).getIntentName(), "iua-xhes",list,ans);
            }
            list.clear();;
            ans.clear();
            flag = true;
        }
    }

}
