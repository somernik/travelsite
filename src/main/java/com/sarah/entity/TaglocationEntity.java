package com.sarah.entity;

import com.sarah.utility.LocalDateAttributeConverter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sarah on 9/26/2017.
 */
@Entity
@Table(name = "taglocation")
public class TaglocationEntity extends BaseEntity<Long> {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy="increment")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Location_id", nullable = false)
    private LocationEntity location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_id", nullable = false)
    private User user;

    @Basic
    @Column(name = "Date")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate date;

    @Basic
    @Column(name = "Positive")
    private boolean positive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Tag_id", nullable = false)
    private TagEntity tag;

    public TaglocationEntity() {}

    public TaglocationEntity(User user, LocationEntity location, TagEntity tag, boolean positive, LocalDate date) {
        this.user = user;
        this.location = location;
        this.tag = tag;
        this.positive = positive;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    public TagEntity getTag() {
        return tag;
    }

    public void setTag(TagEntity tag) {
        this.tag = tag;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean getPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaglocationEntity that = (TaglocationEntity) o;

        if (id != that.id) return false;

        return true;
    }

}
