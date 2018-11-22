package com.group.zhtx.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class IndexController {
    @RequestMapping("/t")
    public String toIndex(){
        return "testIndex";
    }
    @RequestMapping("/i")
    public String toRegister(){
        return "testRegister";
    }
    @RequestMapping("/managerLogin")
    public String toManagerLogin(){
        return "managerLogin";
    }
    @RequestMapping("/manager")
    public String toManager(){
        return "manager";
    }
}
