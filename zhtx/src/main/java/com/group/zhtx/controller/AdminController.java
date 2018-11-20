package com.group.zhtx.controller;

import com.group.zhtx.message.websocket.client.AdminC;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @PostMapping("/adminLogin")
    public String adminLogin(@RequestBody AdminC adminC){
        System.out.println("管理员用户名："+adminC.getLoginName());
        System.out.println("管理员密码："+adminC.getLoginPassword());
        return "manager";
    }
}
