package com.group.zhtx.repository;

import com.group.zhtx.model.Group;
import com.group.zhtx.model.GroupUser;
import com.group.zhtx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface GroupUserRepository extends JpaRepository<GroupUser,Long> {


    @Query("select gu.receiveTime from GroupUser gu where gu.user.uuid=?1 and gu.group.uuid=?2")
    public Date getGroupLastestTime(String userUuid, String groupUuid);


    @Query("select gu.role from GroupUser gu where gu.user.uuid=?1 and gu.group.uuid=?2")
    public int getGroupUserRole(String userUuid,String groupUuid);
}
