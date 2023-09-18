package com.example.demo.controller;
import com.example.demo.repository.KeyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/chatGPT")
public class ChatGPTController {

    @Autowired
    KeyRepository keyRepository;
    private final String API_ENDPOINT = "https://api.openai.com/v1/chat/completions";
    //test2
    @PostMapping("/chat")
    public ResponseEntity<String> chatWithGPT(@RequestBody Map<String, String> requestData) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+keyRepository.findByUse("chatGPT").getKey());
        headers.set("Content-Type", "application/json");
        
        JSONObject requestJson = new JSONObject();
        JSONArray messagesArray = new JSONArray();

        // 系統消息，用於設定語言為繁體中文
        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", "使用繁體中文，我在這裡傾聽您的心事，簡短回答");

        // 用戶消息
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", requestData.get("content"));  // 使用requestData中的內容

        messagesArray.put(systemMessage);  // 先加入系統消息
        messagesArray.put(userMessage);    // 再加入用戶消息

        requestJson.put("messages", messagesArray);
        requestJson.put("model", "gpt-3.5-turbo");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(requestJson.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(API_ENDPOINT, HttpMethod.POST, request, String.class);

        return response;

    }

}
