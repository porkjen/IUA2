package com.example.demo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class gpt {
    public static void main(String[] args) {
        // OpenAI API endpoint URL
        String apiUrl = "https://api.openai.com/v1/engines/davinci-002/completions";

        // Your OpenAI API key
        String apiKey = "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";

        // JSON payload for the request
        String jsonPayload = "{\"prompt\": \"Once upon a time\", \"max_tokens\": 50}";

        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(apiUrl);

            // Set the API key in the Authorization header
            httpPost.addHeader("Authorization", "Bearer " + apiKey);

            // Set the content type to JSON
            httpPost.addHeader("Content-Type", "application/json");

            // Set the request body
            httpPost.setEntity(new StringEntity(jsonPayload));

            // Send the POST request
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            // Process the response
            if (responseEntity != null) {
                String responseBody = EntityUtils.toString(responseEntity);
                System.out.println(responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

