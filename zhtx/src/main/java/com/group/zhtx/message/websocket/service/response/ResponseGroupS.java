package com.group.zhtx.message.websocket.service.response;

import java.util.ArrayList;
import java.util.List;

public class ResponseGroupS {
    private List<ResponseGroup> data=new ArrayList<>();
    public void addResponseGroup(ResponseGroup responseGroup){
        data.add(responseGroup);
    }

    public List<ResponseGroup> getData() {
        return data;
    }

    public void setData(List<ResponseGroup> data) {
        this.data = data;
    }
}
