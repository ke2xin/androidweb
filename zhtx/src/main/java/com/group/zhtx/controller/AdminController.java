package com.group.zhtx.controller;

import com.group.zhtx.message.websocket.client.AdminC;
import com.group.zhtx.model.Administrator;
import com.group.zhtx.service.AdministratorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class AdminController {
    @Resource
    private AdministratorService service;

    @PostMapping("/adminLogin")
    public Map adminLogin(@RequestBody AdminC adminC, HttpSession session) {
        String loginName = adminC.getLoginName();
        String loginPassword = adminC.getLoginPassword();
        System.out.println("管理员用户名：" + loginName);
        System.out.println("管理员密码：" + loginPassword);
        Administrator administrator = service.findAdministrator(loginName);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        if (administrator != null) {
            if (loginPassword.equals(administrator.getPassword())) {
                map.put("code", 1);
                map.put("theUser", loginName);
                map.put("result", "");
                map.put("status", "success");
            } else {
                map.put("code", 2);
                map.put("theUser", null);
                map.put("result", "密码错误");
                map.put("status", "success");
            }
        } else {
            map.put("code", 0);
            map.put("theUser", null);
            map.put("result", "用户名错误,不存在该用户");
            map.put("status", "fail");
        }
        return map;
    }
}
