package com.group.zhtx.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "chat_group")
public class Group implements Serializable {

    @Id
    @Column(name = "group_uuid",length = 30)
    private String uuid;

    @Column(name = "group_name",nullable = false,length = 30)
    private String name;

    @Column(name = "group_hobby",length = 200)
    private String hobby;

    @Column(name = "group_anoun",length = 400)
    private String anoun;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinColumn(name = "group_creatorId",referencedColumnName = "user_uuid",foreignKey = @ForeignKey(name = "FK_GROP_CREATOR"))
    private User creater;

    @Column(name = "group_status")
    private short status = 1;

    @Column(name = "group_portarit",length = 100)
    private String portarit ="-1";

    @Temporal(TemporalType.DATE)
    @Column(name = "group_createTime")
    private Date createTime;

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

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getAnoun() {
        return anoun;
    }

    public void setAnoun(String anoun) {
        this.anoun = anoun;
    }

    public User getCreater() {
        return creater;
    }

    public void setCreater(User creater) {
        this.creater = creater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getPortarit() {
        return portarit;
    }

    public void setPortarit(String portarit) {
        this.portarit = portarit;
    }
}
