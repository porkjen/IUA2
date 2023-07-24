package com.example.demo;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class getApi {

    String get(String u){
        StringBuilder inline = new StringBuilder();
        try{
            URL url = new URL(u);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            //Getting the response code
            int response = conn.getResponseCode();
            if(response!=200){
                throw new RuntimeException("HttpResponseCode: " + response);
            }
            else {
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }

                //Close the scanner
                scanner.close();
            }

        }
        catch (Exception e){

        }
        return inline.toString();
    }
}
