package com.chap1.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class Chap1Controller {
    @RequestMapping(value = "/chap1", method = RequestMethod.GET)
    public @ResponseBody String chap1(){
        return "chap1";
    }
}
