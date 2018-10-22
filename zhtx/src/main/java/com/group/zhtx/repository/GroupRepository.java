package com.group.zhtx.repository;

import com.example.jpa.demo.model.Group;
import com.example.jpa.demo.model.GroupUser;
import com.example.jpa.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GroupRepository extends JpaRepository<Group,String>,JpaSpecificationExecutor<Group>{


}
