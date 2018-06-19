package com.docetor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/check")
    public String CheckApi(){
        return "service ok";
    }

}
