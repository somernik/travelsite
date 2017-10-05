package com.sarah.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sarah on 10/5/2017.
 * 1 = admin
 * 2 = contributor
 */
@Entity
@Table(name = "privilege")
public class PrivilegeEntity {
    private int id;
    private String value;
    private Set<UserPrivilegeEntity> userPrivileges = new HashSet<UserPrivilegeEntity>();

    public PrivilegeEntity(){}

    public PrivilegeEntity(String value) {
        this.value = value;
    }

    public PrivilegeEntity(int id) {
        this.id = id;
    }

    public PrivilegeEntity(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public PrivilegeEntity(String value, Set<UserPrivilegeEntity> userPrivileges) {
        this.value = value;
        this.userPrivileges = userPrivileges;
    }

    public PrivilegeEntity(int id, String value, Set<UserPrivilegeEntity> userPrivileges) {
        this.id = id;
        this.value = value;
        this.userPrivileges = userPrivileges;
    }

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

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.privilege")
    public Set<UserPrivilegeEntity> getUserPrivileges() {
        return userPrivileges;
    }

    public void setUserPrivileges(Set<UserPrivilegeEntity> userPrivileges) {
        this.userPrivileges = userPrivileges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrivilegeEntity that = (PrivilegeEntity) o;

        if (id != that.id) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PrivilegeEntity{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
