package com.group.zhtx.repository;

import com.group.zhtx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> , PagingAndSortingRepository<User,String> {


    @Query("select u.name from User u where u.uuid=?1")
    public String getUserName(String userUuid);


    //根据组Uuid查找GroupUser表中有相同群Uuid的用户的Uuid再从User表中查找指定用户
    @Query("select u from User u where u.uuid in (select gu.user.uuid from GroupUser gu where gu.group.uuid=?1)")
    public List<User> getUserByGroupUuid(String groupUuid);

    //根据用户uuid和电话查找指定的用户
    public List<User> findByUuidAndPhone(String uuid, String phone);

    //根据指定的电话查找是否已注册了该用户
    User findByPhone(String phone);

    //根据用户账号查找用户
    public User findByUuid(String user);

    @Query("select  u from User u ")
    public List<User> findThreedUser();

    /*根据关键词查找数据库*/
    @Query("select u from User u where (u.uuid like %?1% or u.name like %?1%) and u.status=?2")
    public List<User>findByUserIdOrUserName(String key,short status);
}
