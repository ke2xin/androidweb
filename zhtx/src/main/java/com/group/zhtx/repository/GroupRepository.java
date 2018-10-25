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

public interface GroupRepository extends JpaRepository<Group,String>,JpaSpecificationExecutor<Group>{

    @Query("select g from Group g where g.uuid in (select gu.group from GroupUser gu where gu.user.uuid = ?1)")
    public List<Group> getGroupByUuid(String user);


}
