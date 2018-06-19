package com.docetor.controller;

import com.twoyum.domain.FileInfoInDto;
import com.twoyum.domain.LogInfoInDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

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
