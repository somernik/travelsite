package com.sarah.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by sarah on 9/26/2017.
 */
@Entity
@Table(name = "review", schema = "travelsite", catalog = "")
public class ReviewEntity {
    private int id;
    private String body;
    private Timestamp date;


    //private int userId;
    //private int locationId;
    private User user;
    private LocationEntity location;

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
    @Column(name = "body")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Location_id")
    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReviewEntity that = (ReviewEntity) o;

        if (id != that.id) return false;
        if (body != null ? !body.equals(that.body) : that.body != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}