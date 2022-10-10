package com.chap11_authserver.controller;


import com.chap11_authserver.domain.otp.Otp;
import com.chap11_authserver.domain.user.User;
import com.chap11_authserver.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    @RequestMapping(value = "/user/auth", method = RequestMethod.POST)
    public void auth(@RequestBody User user){
        userService.auth(user);
    }

    @RequestMapping(value = "/otp/check", method = RequestMethod.POST)
    public void addUser(@RequestBody Otp otp, HttpServletResponse response){
        if (userService.check(otp)){
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

    }
}
