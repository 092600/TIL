package com.chap_5.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @RequestMapping(value = "chap5", method = RequestMethod.GET)
    public @ResponseBody String chap5(){
        SecurityContext context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        return "chap5 " + username;
    }
}
