package com.sarah.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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

    @Basic
    @Column(name = "rank")
    private int rank;

    @Basic
    @Column(name = "negativeRank")
    private int negativeRank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Location_id", nullable = false)
    private LocationEntity location;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "taglocations")
    private Set<User> users = new HashSet<User>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Tag_id", nullable = false)
    private TagEntity tag;

    public TaglocationEntity() {}

    public TaglocationEntity(int rank, LocationEntity location) {
        this.rank = rank;
        this.location = location;
    }

    public TaglocationEntity(int rank, int negativeRank, LocationEntity location, TagEntity tag) {
        this.rank = rank;
        this.negativeRank = negativeRank;
        this.location = location;
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getNegativeRank() {
        return negativeRank;
    }

    public void setNegativeRank(int negativeRank) {
        this.negativeRank = negativeRank;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public TagEntity getTag() {
        return tag;
    }

    public void setTag(TagEntity tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaglocationEntity that = (TaglocationEntity) o;

        if (id != that.id) return false;
        if (rank != that.rank) return false;

        return true;
    }

}
