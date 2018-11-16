package com.group.zhtx.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "chat_user_gps")
public class UserGps implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "locate_uid", referencedColumnName = "user_uuid", foreignKey = @ForeignKey(name = "FK_LOCATION_USER"))
    private User user;

    @Column(name = "locate_longitude",length = 100)
    private String longgitude;

    @Column(name = "locate_latitude",length = 100)
    private String latitude;

    @Column(name = "locate_corner",length = 100)
    private String directionAndAngle;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "locate_time")
    private Date locationDate;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLonggitude() {
        return longgitude;
    }

    public void setLonggitude(String longgitude) {
        this.longgitude = longgitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDirectionAndAngle() {
        return directionAndAngle;
    }

    public void setDirectionAndAngle(String directionAndAngle) {
        this.directionAndAngle = directionAndAngle;
    }

    public Date getLocationDate() {
        return locationDate;
    }

    public void setLocationDate(Date locationDate) {
        this.locationDate = locationDate;
    }
}
