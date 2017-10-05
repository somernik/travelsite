package com.sarah.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sarah on 9/26/2017.
 */
@Entity
@Table(name = "userprivelege")
public class UserprivelegeEntity {
    private int id;
    private int privilegeId;
    private int userId;
    private String userName;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy="increment")
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "Privelege_id")
    public int getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(int privelegeId) {
        this.privilegeId = privilegeId;
    }


    @Column(name = "User_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserprivelegeEntity that = (UserprivelegeEntity) o;

        if (privilegeId != that.privilegeId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = privilegeId;
        result = 31 * result + userId;
        return result;
    }
}
