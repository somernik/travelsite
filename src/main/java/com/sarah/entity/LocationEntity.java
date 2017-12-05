package com.sarah.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Accesses locations values.
 * Created by sarah on 9/26/2017.
 */
@Entity
@Table(name = "location")
public class LocationEntity extends BaseEntity<Long> {
    private Long id;
    private String name;
    private String googleId;
    private String photoReference;
    private Set<ReviewEntity> reviews = new HashSet<ReviewEntity>();
    private Set<User> users = new HashSet<User>();
    private Set<TaglocationEntity> tagLocations = new HashSet<TaglocationEntity>();

    public LocationEntity() {}

    public LocationEntity(String name, String googleId) {
        this.name = name;
        this.googleId = googleId;
    }

    public LocationEntity(Long id, String name, String googleId) {
        this.id = id;
        this.name = name;
        this.googleId = googleId;
    }

    public LocationEntity(String name, String googleId, Set<ReviewEntity> reviews, Set<User> users) {
        this.name = name;
        this.googleId = googleId;
        this.reviews = reviews;
        this.users = users;
    }

    public LocationEntity(Long id, String name, String googleId, Set<ReviewEntity> reviews, Set<User> users) {
        this.id = id;
        this.name = name;
        this.googleId = googleId;
        this.reviews = reviews;
        this.users = users;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy="increment")
    @Column(name = "id", unique = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    public Set<ReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(Set<ReviewEntity> reviews) {
        this.reviews = reviews;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "locations")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    public Set<TaglocationEntity> getTagLocations() {
        return tagLocations;
    }

    public void setTagLocations(Set<TaglocationEntity> tagLocations) {
        this.tagLocations = tagLocations;
    }

    @Basic
    @Column(name = "photoReference")
    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationEntity that = (LocationEntity) o;

        if (id != that.id) return false;
        if (!Objects.equals(googleId, that.googleId)) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "LocationEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", googleId='" + googleId + '\'' +/*
                ", reviews=" + reviews +
                ", users=" + users +*/
                '}';
    }
}
