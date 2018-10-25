package com.group.zhtx.message.websocket.service.getGroupData;

public class UserGetGroupDataMember {

    private String groupUserName;
    private String groupUserPortrait;
    private String groupUserUuid;

    public String getGroupUserName() {
        return groupUserName;
    }

    public void setGroupUserName(String groupUserName) {
        this.groupUserName = groupUserName;
    }

    public String getGroupUserPortrait() {
        return groupUserPortrait;
    }

    public void setGroupUserPortrait(String groupUserPortrait) {
        this.groupUserPortrait = groupUserPortrait;
    }

    public String getGroupUserUuid() {
        return groupUserUuid;
    }

    public void setGroupUserUuid(String groupUserUuid) {
        this.groupUserUuid = groupUserUuid;
    }
}
