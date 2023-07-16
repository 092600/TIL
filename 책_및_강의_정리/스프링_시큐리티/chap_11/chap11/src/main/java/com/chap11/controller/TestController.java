package com.chap11.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @PostMapping(value = "/test")
    public @ResponseBody String test(){
        return "test";
    }
}
