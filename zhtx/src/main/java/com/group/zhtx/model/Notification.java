package com.group.zhtx.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "chat_notification")
public class Notification implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public int id;

    @ManyToOne(fetch = FetchType.EAGER,targetEntity = User.class)
    @JoinColumn(name = "notification_suid",referencedColumnName = "user_uuid",foreignKey = @ForeignKey(name = "FK_NOTIFICATION_S_USER"))
    public User sendUserId;

    @ManyToOne(fetch = FetchType.EAGER,targetEntity = User.class)
    @JoinColumn(name = "notification_ruid",referencedColumnName = "user_uuid",foreignKey = @ForeignKey(name = "FK_NOTIFICATION_R_USER"))
    public User receiveUserId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Group.class)
    @JoinColumn(name = "notification_groupId",referencedColumnName = "group_uuid",foreignKey = @ForeignKey(name = "FK_NOTIFICATION_GROUP"))
    public Group groupId;

    @Column(name = "notification_content", length = 200)
    public String content;

    @Column(name = "notification_status")
    public int status;

    @Column(name = "notification_result")
    public int result;

    @Column(name = "notification_createTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(User sendUserId) {
        this.sendUserId = sendUserId;
    }

    public User getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(User receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public Group getGroupId() {
        return groupId;
    }

    public void setGroupId(Group groupId) {
        this.groupId = groupId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
