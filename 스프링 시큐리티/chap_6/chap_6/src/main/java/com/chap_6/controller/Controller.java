package com.chap_6.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @RequestMapping(value = "chap6", method = RequestMethod.GET)
    public @ResponseBody String chap6(){
        return "chap6";
    }
}
