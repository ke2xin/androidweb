package com.group.zhtx.message.websocket.service.loginMessage;

import java.util.ArrayList;
import java.util.List;

public class UserLoginData {

    private UserLoginDataSingal singal;

    private List<UserLoginDataGroup> groups = new ArrayList<>();

    public UserLoginDataSingal getSingal() {
        return singal;
    }

    public void setSingal(UserLoginDataSingal singal) {
        this.singal = singal;
    }

    public List<UserLoginDataGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<UserLoginDataGroup> groups) {
        this.groups = groups;
    }

    public void addDataGroup(UserLoginDataGroup group) {
        groups.add(group);
    }
}
