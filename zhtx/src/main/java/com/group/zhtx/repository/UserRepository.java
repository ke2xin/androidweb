package com.group.zhtx.repository;

import com.group.zhtx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {


}
