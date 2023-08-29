package com.example.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.sql.DriverManager.println;

@Controller
@CrossOrigin(value = "http://localhost:3000/ChatRoomlist")
public class ListController {

    static String data;
    @PostMapping("/localstorage")
    public ResponseEntity<String> handleLocalStorageData(@RequestBody String localStorageData) {
        // 在這裡處理和存儲 LocalStorage 資料
        System.out.println(localStorageData);
        data = localStorageData;
        System.out.println("data: " + data);

        // 返回適當的回應給前端
        return ResponseEntity.ok("LocalStorage data received");
    }
<<<<<<< HEAD
//    @RequestMapping(value = "/{dynamicData}", method = RequestMethod.GET)
//    public String getChat(@PathVariable("dynamicData") String dynamicData, Model model) {
//        /*if (dynamicData.equals(data)) {
//            System.out.println("success");
//            return "chat";
//        } else {
//            System.out.println("Dy="+ dynamicData);
//            System.out.println("Da="+ data);
//            return "error";
//        }*/
//        return "chat";
//    }
=======
    /*@RequestMapping(value = "/{dynamicData}", method = RequestMethod.GET)
    public String getChat(@PathVariable("dynamicData") String dynamicData, Model model) {
        /*if (dynamicData.equals(data)) {
            System.out.println("success");
            return "chat";
        } else {
            System.out.println("Dy="+ dynamicData);
            System.out.println("Da="+ data);
            return "error";
        }
        return "chat";
    }*/
>>>>>>> b967ed226b8251a62e81a8073c5af9482e832549

}
