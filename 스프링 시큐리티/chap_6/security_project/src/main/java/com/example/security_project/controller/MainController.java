package com.example.security_project.controller;

import com.example.security_project.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(Authentication a, Model model){
        System.out.println(productService.findAll());
        model.addAttribute("username", a.getName());
        model.addAttribute("products", productService.findAll());
        return "main";
    }
}
