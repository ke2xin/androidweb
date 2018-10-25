package com.group.zhtx.repository;


import com.group.zhtx.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query("select m from Message m order by m.sendTime Desc ")
    public List<Message> getLastestMessageByGroupUuid( );


    @Query("select count(*) from Message m where m.sendTime >=?2 and m.group.uuid =?1")
    public int getCountOfUnReadMessageByGroupUuidAndTime(String groupuuid,Date leaveTime);

}
