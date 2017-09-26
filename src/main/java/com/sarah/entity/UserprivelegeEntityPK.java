package com.sarah.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by sarah on 9/26/2017.
 */
public class UserprivelegeEntityPK implements Serializable {
    private int privelegeId;
    private int userId;

    @Column(name = "Privelege_id")
    @Id
    public int getPrivelegeId() {
        return privelegeId;
    }

    public void setPrivelegeId(int privelegeId) {
        this.privelegeId = privelegeId;
    }

    @Column(name = "User_id")
    @Id
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

        UserprivelegeEntityPK that = (UserprivelegeEntityPK) o;

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
