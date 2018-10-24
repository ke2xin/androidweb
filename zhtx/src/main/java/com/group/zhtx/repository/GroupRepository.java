package com.group.zhtx.repository;


import com.group.zhtx.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GroupRepository extends JpaRepository<Group,String>,JpaSpecificationExecutor<Group>{


}
