package com.group.zhtx.repository;

import com.group.zhtx.model.Group;
import com.group.zhtx.model.GroupUser;
import com.group.zhtx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    @Query("select gu.group from GroupUser gu where gu.user.uuid =?1")
    public List<Group> getGroupsByUserUuid(String userUuid);

    /*
       根据群组查找该群的成员
     */
    public List<GroupUser>findByGroup(Group group);

    public GroupUser findByUserAndGroup(User user, Group group);

    //根据群uuid查找所有群成员
    @Query("select g from GroupUser g where g.group.uuid=?1")
    public List<GroupUser>getAllGroupUserForGroupId(String groupId);

    //根据用户传入的uuid和group_id查找该群成员
    @Query("select g from GroupUser g where g.user.uuid=:uuid and g.group.uuid=:groupId")
    public GroupUser getGroupUserByGroupAndUuid(@Param("uuid") String uuid, @Param("groupId") String groupId);

    @Query("select g from GroupUser g where g.user =?1 and g.group=?2")
    public GroupUser getGroupUserByGroupAndUser(User user,Group group);

}
