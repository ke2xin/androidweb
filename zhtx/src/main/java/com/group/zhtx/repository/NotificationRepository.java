package com.group.zhtx.repository;

import com.group.zhtx.model.Group;
import com.group.zhtx.model.Notification;
import com.group.zhtx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    //根据发送方和接受方查找通知表
    public Notification findByReceiveUserIdAndSendUserId(User receiver, User sender);

    //根据发送方、接收方和群号查找通知
    public Notification findByReceiveUserIdAndSendUserIdAndGroupId(User receiver, User sender, Group group);

    //根据接收用户查找未接收通知
    @Query("select n from Notification n where n.receiveUserId.uuid = ?1")
    public List<Notification> findUnReceiveNotificationByReceiveUser(String userUuid);


}
