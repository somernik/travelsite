package com.sarah.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sarah on 9/26/2017.
 */
@Entity
@Table(name = "tag")
public class TagEntity extends BaseEntity<Long>{

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy="increment")
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tag")
    private Set<TaglocationEntity> tagLocations = new HashSet<TaglocationEntity>();

    public TagEntity() {
    }

    public TagEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TaglocationEntity> getTagLocations() {
        return tagLocations;
    }

    public void setTagLocations(Set<TaglocationEntity> tagLocations) {
        this.tagLocations = tagLocations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TagEntity tagEntity = (TagEntity) o;

        if (id != null ? !id.equals(tagEntity.id) : tagEntity.id != null) return false;
        return name != null ? name.equals(tagEntity.name) : tagEntity.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
