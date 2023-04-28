package com.llacerximo.bbddweb.bbddweb.controller;

import org.springframework.stereotype.Controller;

@Controller
public class MainController {
    
    public String index(){
        return "index";
    }

    public String error(){
        return "error";
    }
}
