package com.group.zhtx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/t")
    public String toIndex(){
        return "index";
    }
    @RequestMapping("/i")
    public String toRegister(){
<<<<<<< HEAD
        return "register";
    }
<<<<<<< HEAD

    @GetMapping("/login")
    public String toLogin(){
        return "login";
    }

    @GetMapping("/updataPassword")
    public String toUpdataPassword(){
        return "updataPassword";
=======
        return "testRegister";
>>>>>>> parent of d069db5... Merge branch 'master' of https://github.com/ke2xin/androidweb
    }
=======
>>>>>>> parent of accf4a9... 首页
}
