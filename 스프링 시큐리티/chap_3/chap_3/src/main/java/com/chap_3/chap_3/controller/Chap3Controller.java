package com.chap_3.chap_3.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Chap3Controller {
    @RequestMapping(value = "/chap3", method = RequestMethod.GET)
    public @ResponseBody String chap3(){
        return "chap3";
    }
}
