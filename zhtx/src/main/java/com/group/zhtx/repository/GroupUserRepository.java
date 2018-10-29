package com.group.zhtx.repository;

import com.group.zhtx.model.Group;
import com.group.zhtx.model.GroupUser;
import com.group.zhtx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface GroupUserRepository extends JpaRepository<GroupUser,Long> {


    @Query("select gu.receiveTime from GroupUser gu where gu.user.uuid=?1 and gu.group.uuid=?2")
    public Date getGroupLastestTime(String userUuid, String groupUuid);


    @Query("select gu.role from GroupUser gu where gu.user.uuid=?1 and gu.group.uuid=?2")
    public int getGroupUserRole(String userUuid,String groupUuid);


    /*
        根据用户的uuid查找用户所拥有的群组
     */
    @Query("select gu.group from GroupUser gu where gu.group.uuid =?1")
    public List<Group> getGroupsByUserUuid(String userUuid);

    public List<GroupUser>findByGroup(Group group);

    public GroupUser findByUserAndGroup(User user, Group group);


}
