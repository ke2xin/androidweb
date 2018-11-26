package com.group.zhtx.repository;

import com.group.zhtx.model.Group;
import com.group.zhtx.model.Notification;
import com.group.zhtx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    //根据发送方和接受方查找通知表
    public Notification findByReceiveUserIdAndSendUserId(User receiver, User sender);

    //根据接收用户查找未接收通知
    @Query("select n from Notification n where n.receiveUserId.uuid = ?1 and n.status=0")
    public List<Notification> findUnReceiveNotificationByReceiveUser(String userUuid);

    //根据发送方、接收方和群号查找通知
    public List<Notification> findByReceiveUserIdAndSendUserIdAndGroupId(User receiver, User sender, Group group);

    //根据发送方、接收方和群号查找通知并排序
    @Query("select  n from Notification n where n.receiveUserId.uuid=?1 and n.sendUserId.uuid=?2 and n.groupId.uuid=?3 order by n.createTime desc ")
    public List<Notification> findNotificationLists(String receiverId, String sendId, String groupId);
}
