package com.chap_7.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Chap7Controller {
    @RequestMapping(value = "/chap7", method = RequestMethod.GET)
    public @ResponseBody String chap7(){
        return "chap7";
    }
}
