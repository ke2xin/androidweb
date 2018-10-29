package com.group.zhtx.repository;


import com.group.zhtx.model.User;
import com.group.zhtx.model.UserGps;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGpsRepository extends JpaRepository<UserGps,Long> {
    public UserGps findByUser(User user);
}
