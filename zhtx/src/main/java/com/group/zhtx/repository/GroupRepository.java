package com.group.zhtx.repository;


import com.group.zhtx.model.Group;
import com.group.zhtx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GroupRepository extends JpaRepository<Group, String>, JpaSpecificationExecutor<Group> {

    @Query("select g from Group g where g.uuid in (select gu.group from GroupUser gu where gu.user.uuid = ?1)")
    public List<Group> getGroupByUuid(String user);

    public Group findByUuid(String uuid);

    //根据用户给的群号模糊查找
    public List<Group> findByUuidLike(String groupId);

    //根据用户传入的群号和用户账号查找该群
    @Query("select g from  Group g where g.uuid=?1 and g.creater.uuid=?2")
    public Group getGroupByUuidAndCreater(String groupId, String uuid);

    @Query("select g from Group g where g.uuid like %?1% or g.name like %?1% ")
    public List<Group> getAllGroupIdOrGroupName(String param);

    //根据用户id和群id查找群
    public Group findByUuidAndCreater(String groupId, User user);

    //根据用户传来的关键字查询群
    @Query("select g from Group g where (g.uuid like %?1% or g.name like %?1%) and g.status=?2")
    public List<Group>findGroupKeyWord(String key,short status);
}
