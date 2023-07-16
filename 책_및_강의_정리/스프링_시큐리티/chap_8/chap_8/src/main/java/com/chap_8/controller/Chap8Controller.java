package com.chap_8.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Chap8Controller {
    @RequestMapping(value = "/chap8", method = RequestMethod.GET)
    public @ResponseBody String chap8(){
        return "chap8";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public @ResponseBody String test(){
        return "test";
    }
}
