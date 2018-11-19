package com.group.zhtx.repository;


import com.group.zhtx.model.User;
import com.group.zhtx.model.UserGps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserGpsRepository extends JpaRepository<UserGps,Long> {

    public UserGps findByUser(User user);

    @Query("select  gps from UserGps gps where gps.user.uuid=?1")
    public UserGps getByUserGps(String userId);
}
