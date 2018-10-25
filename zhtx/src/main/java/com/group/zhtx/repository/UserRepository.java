package com.group.zhtx.repository;

import com.group.zhtx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {


    @Query("select u.name from User u where u.uuid=?1")
    public String getUserName(String userUuid);


    //根据组Uuid查找GroupUser表中有相同群Uuid的用户的Uuid再从User表中查找指定用户
    @Query("select u from User u where u.uuid in (select gu.user.uuid from GroupUser gu where gu.group.uuid=?1)")
    public List<User> getUserByGroupUuid(String groupUuid);
}
