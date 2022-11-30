package com.example.demo.swagger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController {

    @GetMapping("/api/swagger/docs")
    public String redirectSwagger(){
        return "redirect:/swagger-ui/index.html";
    }
}
