package com.group.zhtx.message.websocket.service.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "users"})
public class ResponseUserS implements Serializable {
    public ResponseUserS() {
    }

    private List<ResponseUser> data = new ArrayList<>();

    public void addResponseUser(ResponseUser responseUser) {
        data.add(responseUser);
    }

    public List<ResponseUser> getData() {//如果没有get和set的话，就会返回一个空对象
        return data;
    }

    public void setData(List<ResponseUser> data) {
        this.data = data;
    }
}
