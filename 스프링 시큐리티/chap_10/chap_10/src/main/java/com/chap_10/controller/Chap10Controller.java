package com.chap_10.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Controller
public class Chap10Controller {

    private Logger logger = Logger.getLogger(Chap10Controller.class.getName());

    @RequestMapping(value = "/chap10", method = RequestMethod.GET)
    public String chap10(){
        return "createPage";
    }

    @RequestMapping(value = "/chap10/create", method = RequestMethod.POST)
    public @ResponseBody String create(){
        return "Post Okay";
    }

    @RequestMapping(value = "/test", method =RequestMethod.POST)
    public @ResponseBody String test(){
        logger.info("Test method called");
        return "HELLO";
    }
}
