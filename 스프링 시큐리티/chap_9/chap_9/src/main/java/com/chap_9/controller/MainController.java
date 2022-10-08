package com.chap_9.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @RequestMapping(value = "/chap9", method = RequestMethod.GET)
    public @ResponseBody String chap9(){
        return "chap9";
    }
}
