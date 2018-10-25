package com.group.zhtx.controller;


import com.group.zhtx.message.controller.register.RegisterC;
import com.group.zhtx.message.controller.register.RegisterS;
import com.group.zhtx.service.RepositoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private RepositoryService service;


    @PostMapping(value = "/register/post")
    public RegisterS toRegister(@RequestBody RegisterC registerC){

        RegisterS registerS = new RegisterS();

        //校验用户是否被注册
        boolean isValid = service.validUserRegister(registerC.getUuid());
        if(isValid){
            registerS.setStatus("fail");
            registerS.setInformation("用户名已经存在");
            return registerS;
        }

        //注册用户
        service.saveUser(registerC);
        registerS.setStatus("注册成功");
        registerS.setInformation("");
        return registerS;
    }

}
