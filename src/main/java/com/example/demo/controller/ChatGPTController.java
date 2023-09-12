package com.example.demo.controller;
import com.example.demo.repository.KeyRepository;
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
        headers.set("Authorization", "Bearer " + "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");
        headers.set("Content-Type", "application/json");

        String requestBody = "{\"messages\": [{\"role\": \"user\", \"content\": \"" + requestData.get("content") + "\"}], \"model\": \"gpt-3.5-turbo\"}";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(API_ENDPOINT, HttpMethod.POST, request, String.class);

        return response;
    }
}
