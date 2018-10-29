package com.group.zhtx.repository;

import com.group.zhtx.model.Notification;
import com.group.zhtx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    //根据发送方和接受方查找通知表
    public Notification findByReceiveUserIdAndSendUserId(User receiver, User sender);

}
