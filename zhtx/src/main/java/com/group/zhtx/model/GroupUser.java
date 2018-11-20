package com.group.zhtx.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "chat_group_user")
public class GroupUser implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Group.class)
    @JoinColumn(name = "groupUser_gid",referencedColumnName = "group_uuid",foreignKey = @ForeignKey(name = "FK_GROUPUSER_GROUP"))
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinColumn(name = "groupUser_uid",referencedColumnName = "user_uuid",foreignKey = @ForeignKey(name = "FK_GROUPUSER__USER"))
    private User user;

    @Column(name = "groupUser_status")
    private short status = 0;

    @Column(name = "groupUser_role")
    private short role = 1;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "groupUser_joinTime")
    private Date joinTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "groupUser_recTime")
    private Date receiveTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public short getRole() {
        return role;
    }

    public void setRole(short role) {
        this.role = role;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }
}
