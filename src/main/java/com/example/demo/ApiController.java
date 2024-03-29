package com.example.demo;

import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class ApiController {

    @PostMapping("/api")
    public ResponseEntity<String> handlePostRequest(@RequestBody MyRequestData requestData) {

        Caller caller = new Caller();
        Calculations calculation = new Calculations(requestData.age, requestData.height, requestData.weight, requestData.desiredWeight, requestData.sex, requestData.activity, requestData.body);
        String data = caller.useData(calculation);

        return ResponseEntity.ok(data);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/chatbot")
    public ResponseEntity<String> handleChatbotRequest(@RequestBody String userInput) throws IOException, ParseException {
        String input = userInput.replace("+", " ");
        String response = ChatBot.getChatbotResponse(input);
        System.out.println("Input: " + input);
        System.out.println("Output: " + response);
        return ResponseEntity.ok(response);
    }
    
}