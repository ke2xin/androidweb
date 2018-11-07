package com.group.zhtx.repository;


import com.group.zhtx.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query("select m from Message m where m.group.uuid =?1 order by m.sendTime Desc ")
    public List<Message> getLastestMessageByGroupUuid(String groupUuid);


    @Query("select count(*) from Message m where m.sendTime >=?2 and m.group.uuid =?1")
    public int getCountOfUnReadMessageByGroupUuidAndTime(String groupuuid,Date leaveTime);

    @Query("select m from Message m where m.sendTime >=?2 and m.group.uuid =?1")
    public List<Message> getUnReadMessageByGroupUuidAndTime(String groupuuid,Date leaveTime);


    @Query("select m from Message m where m.group.uuid = ?1")
    public List<Message> getMessagesByUserUuid(String groupUuid);

    //根据用户传过来的群号查找群聊天信息
    @Query("select m from Message m where m.group.uuid=?1 and m.user.uuid in (select g.user.uuid from GroupUser g where g.group.uuid=?2)order by message_time")
    public List<Message> getChatMessageByGroupId(String groupId1,String groupId2);
}
