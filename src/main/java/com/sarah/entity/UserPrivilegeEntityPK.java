package com.sarah.entity;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by sarah on 10/5/2017.
 */
@Embeddable
public class UserPrivilegeEntityPK implements Serializable {


    private PrivilegeEntity privilege;
    private User user;

    @ManyToOne
    public PrivilegeEntity getPrivilege() {
        return privilege;
    }

    public void setPrivilege(PrivilegeEntity privilege) {
        this.privilege = privilege;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPrivilegeEntityPK that = (UserPrivilegeEntityPK) o;

        return privilege.equals(that.privilege) && user.equals(that.user);
    }

    @Override
    public int hashCode() {
        int result = privilege.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserPrivilegeEntityPK{" +
                "privilege=" + privilege.toString() +
                ", user=" + user.toString() +
                '}';
    }
}
