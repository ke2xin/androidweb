package com.group.zhtx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String toIndex(){
        return "chatIndex";
    }
    @GetMapping("/register")
    public String toRegister(){
        return "register";
    }

    @GetMapping("/login")
    public String toLogin(){
        return "login";
    }

    @GetMapping("/updataPassword")
    public String toUpdataPassword(){
        return "updataPassword";
    }
}
