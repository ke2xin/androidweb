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
    @RequestMapping("/manager")
    public String toManager(){//管理员页面
        return "manager";
    }
    @RequestMapping("/managerLogin")
    public String toManagerLogin(){//管理员登录页面
        return "managerLogin";
    }
}
