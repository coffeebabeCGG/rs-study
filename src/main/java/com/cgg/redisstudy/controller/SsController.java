package com.cgg.redisstudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SsController {

    @GetMapping(value = "/index")
    public String getIndex() {
        return "index";
    }



}
