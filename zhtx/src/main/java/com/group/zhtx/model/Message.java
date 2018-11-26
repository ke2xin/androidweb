package com.group.zhtx.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "chat_message")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;


    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Group.class)
    @JoinColumn(name = "message_gid", referencedColumnName = "group_uuid", foreignKey = @ForeignKey(name = "FK_MESSAGE_GROUP"))
    private Group group;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "message_uid", referencedColumnName = "user_uuid", foreignKey = @ForeignKey(name = "FK_MESSAGE_USER"))
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "message_time")
    private Date sendTime;

    @Column(name = "message_content", length = 500)
    private String content;

    @Column(name = "message_status")
    private short status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }
}
