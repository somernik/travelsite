package com.sarah.entity;

import javax.persistence.*;

/**
 * Created by sarah on 10/5/2017.
 */
@Entity
@Table(name = "userprivilege")
@AssociationOverrides({
        @AssociationOverride(name = "pk.user",
                joinColumns = @JoinColumn(name = "User_id")),
        @AssociationOverride(name = "pk.privilege",
                joinColumns = @JoinColumn(name = "Privilege_id")) })
public class UserPrivilegeEntity {

    private UserPrivilegeEntityPK pk = new UserPrivilegeEntityPK();
    private String userName;

    public UserPrivilegeEntity() {}

    public UserPrivilegeEntity(User user, PrivilegeEntity privilege) {
        this.userName = user.getUserName();
        this.setUser(user);
        this.setPrivilege(privilege);
    }

    public UserPrivilegeEntity(String userName, User user, PrivilegeEntity privilege) {
        this.userName = userName;
        this.setUser(user);
        this.setPrivilege(privilege);
    }

    @EmbeddedId
    public UserPrivilegeEntityPK getPk() {
        return pk;
    }

    public void setPk(UserPrivilegeEntityPK pk) {
        this.pk = pk;
    }

    @Transient
    public User getUser() {
        return getPk().getUser();
    }

    public void setUser(User user) {
        getPk().setUser(user);
    }

    @Transient
    public PrivilegeEntity getPrivilege() {
        return getPk().getPrivilege();
    }

    public void setPrivilege(PrivilegeEntity privilege) {
        getPk().setPrivilege(privilege);
    }

    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserPrivilegeEntity{" +
                "pk=" + pk.toString() +
                ", userName='" + userName + '\'' +
                '}';
    }
}
