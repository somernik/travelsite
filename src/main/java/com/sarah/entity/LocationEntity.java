package com.sarah.entity;

import javax.persistence.*;

/**
 * Created by sarah on 9/26/2017.
 */
@Entity
@Table(name = "location", schema = "travelsite", catalog = "")
public class LocationEntity {
    private int id;
    private String name;
    private String googleId;

    public LocationEntity() {}

    public LocationEntity(String name, String googleId) {
        this.name = name;
        this.googleId = googleId;
    }

    public LocationEntity(int id, String name, String googleId) {
        this.id = id;
        this.name = name;
        this.googleId = googleId;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "googleId")
    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationEntity that = (LocationEntity) o;

        if (id != that.id) return false;
        if (googleId != that.googleId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

}
