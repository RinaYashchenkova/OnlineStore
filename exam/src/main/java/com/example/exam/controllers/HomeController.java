package com.example.exam.controllers;


import com.example.exam.services.PeopleService;
import com.example.exam.services.ProductService;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/test")
    public String testPage(){
        return "test";
    }
}
