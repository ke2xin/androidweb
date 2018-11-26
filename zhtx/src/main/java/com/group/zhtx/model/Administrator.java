package com.group.zhtx.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "chat_administrator")
public class Administrator implements Serializable {


    //管理员UUID（账号），这也是登陆系统的唯一标识
    @Id
    @Column(name = "admin_uuid", length = 30)
    private String uuid;

    //管理员密码
    @Column(name = "admin_password", nullable = false, length = 16)
    private String password;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
