package com.sarah.entity;

import javax.persistence.*;

/**
 * Created by sarah on 9/26/2017.
 */
@Entity
@Table(name = "userprivelege", schema = "travelsite", catalog = "")
@IdClass(UserprivelegeEntityPK.class)
public class UserprivelegeEntity {
    private int privelegeId;
    private int userId;

    @Id
    @Column(name = "Privelege_id")
    public int getPrivelegeId() {
        return privelegeId;
    }

    public void setPrivelegeId(int privelegeId) {
        this.privelegeId = privelegeId;
    }

    @Id
    @Column(name = "User_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserprivelegeEntity that = (UserprivelegeEntity) o;

        if (privelegeId != that.privelegeId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = privelegeId;
        result = 31 * result + userId;
        return result;
    }
}
