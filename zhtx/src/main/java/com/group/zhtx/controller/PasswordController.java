package com.group.zhtx.controller;

import com.group.zhtx.message.controller.register.PasswordC;
import com.group.zhtx.message.controller.register.RegisterS;
import com.group.zhtx.model.User;
import com.group.zhtx.service.RepositoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class PasswordController {
    @Resource
    private RepositoryService service;

    @PostMapping("/password/post")
    public RegisterS updatePassword(@RequestBody PasswordC passwordC) {

        RegisterS registerS = new RegisterS();

        /*
            检测该电话是否被注册
         */
        boolean isRegistered = service.isPhoneRegistered(passwordC.getTelephone());
        if (isRegistered) {
            User users = service.getUserByPhone(passwordC.getTelephone());
            if (users != null) {
                service.updateNewPassword(users, passwordC);
                registerS.setStatus("success");
                registerS.setInformation("修改密码成功");
                return registerS;
            } else {
                registerS.setStatus("fail");
                registerS.setInformation("修改密码失败");
                return registerS;
            }
        } else {
            registerS.setStatus("fail");
            registerS.setInformation("该号码没有被注册");
            return registerS;
        }
    }
}
