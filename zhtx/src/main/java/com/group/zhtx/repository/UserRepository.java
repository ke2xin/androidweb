package com.group.zhtx.repository;

<<<<<<< HEAD

=======
>>>>>>> fe01664500f43459b3476cde00a013a4ee135db1
import com.group.zhtx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {


}
