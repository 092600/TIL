package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Chap4Controller {
    @RequestMapping(value = "/chap4", method = RequestMethod.GET)
    public @ResponseBody String chap4(){
        return "chap4";
    }
}
