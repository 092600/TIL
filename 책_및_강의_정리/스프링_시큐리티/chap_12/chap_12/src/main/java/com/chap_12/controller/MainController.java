package com.chap_12.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Logger;

@Controller
public class MainController {
    private Logger logger = Logger.getLogger(MainController.class.getName());

    @GetMapping("/")
    public @ResponseBody  String main(OAuth2AuthenticationToken token){
        logger.info(String.valueOf(token.getPrincipal()));
        return "Hello There";
    }
}
