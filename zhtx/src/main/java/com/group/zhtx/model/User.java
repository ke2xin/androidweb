package com.group.zhtx.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "chat_user")
public class User implements Serializable{

    private static final long serialVersionUID = -3417930882448168081L;

    //用户UUID（用户账号）
    @Id
    @Column(name = "user_uuid",length = 30)
    private String uuid;

    @Column(name = "user_name",length = 50)
    private String name;

    //用户密码
    @Column(name = "user_password",nullable = false,length = 16)
    private String password;

    //用户的手机号码
    @Column(name = "user_phone",nullable =false,length = 11)
    private String phone;


    //用户邮箱
    @Column(name = "user_email",length = 30)
    private String email;


    //用户头像的存储的地址
    @Column(name = "user_portrait",length = 100)
    private  String portrait;

    //用户的状态
    // 0表示被禁，1表示没有被禁用
    @Column(name = "user_status")
    private short status;

    //用户签名
    @Column(name = "user_sign",length = 50)
    private String sign;

    //用户创建的时间
    @Column(name = "user_createTime")
    @Temporal(TemporalType.DATE)
    private Date createTime;

    //用户最新修改资料的时间
    @Column(name = "user_lastestModify")
    @Temporal(TemporalType.DATE)
    private Date modifyTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }


}
