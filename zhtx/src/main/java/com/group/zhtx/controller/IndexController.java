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
        return "testRegister";
    }
}
