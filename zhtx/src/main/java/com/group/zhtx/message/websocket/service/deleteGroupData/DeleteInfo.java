package com.group.zhtx.message.websocket.service.deleteGroupData;

import com.group.zhtx.message.websocket.service.getGroupData.UserGetGroupDataMember;

import java.util.ArrayList;
import java.util.List;

public class DeleteInfo {
    private List<UserGetGroupDataMember> numbers = new ArrayList<>();

    public List<UserGetGroupDataMember> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<UserGetGroupDataMember> numbers) {
        this.numbers = numbers;
    }

    public void addNumber(UserGetGroupDataMember number) {
        numbers.add(number);
    }
}
