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

    @PostMapping("/chat")
    public ResponseEntity<String> chatWithGPT(@RequestBody Map<String, String> requestData) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "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");
        headers.set("Content-Type", "application/json");
        
        JSONObject requestJson = new JSONObject();
        JSONArray messagesArray = new JSONArray();

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", requestData.get("content"));

        messagesArray.put(userMessage);

        requestJson.put("messages", messagesArray);
        requestJson.put("model", "gpt-3.5-turbo");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(requestJson.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(API_ENDPOINT, HttpMethod.POST, request, String.class);

        return response;
    }

}
