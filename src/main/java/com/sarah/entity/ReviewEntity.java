package com.sarah.entity;

import com.sarah.utility.LocalDateAttributeConverter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by sarah on 9/26/2017.
 */
@Entity
@Table(name = "review")
public class ReviewEntity extends BaseEntity<Long>{
    private Long id;
    private String body;
    private LocalDate date;
    private Date created;
    private Integer stars;

    private User user;
    private LocationEntity location;

    public ReviewEntity(){
    }

    public ReviewEntity(String body, LocalDate date, User user, LocationEntity location, int stars) {
        this.body = body;
        this.date = date;
        this.user = user;
        this.location = location;
        this.stars = stars;
    }

    public ReviewEntity(Long reviewId, String body, LocalDate date, User user, LocationEntity location, int stars) {
        this.id = reviewId;
        this.body = body;
        this.date = date;
        this.user = user;
        this.location = location;
        this.stars = stars;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy="increment")
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    @Convert(converter = LocalDateAttributeConverter.class)
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Column(name = "stars")
    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
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
    public String toString() {
        return "ReviewEntity{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", date=" + date +
                ", user=" + user +
                ", location=" + location +
                ", stars=" + stars +
                '}';
    }
}
