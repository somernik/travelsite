package com.sarah.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagEntity tagEntity = (TagEntity) o;

        if (id != tagEntity.id) return false;
        if (name != null ? !name.equals(tagEntity.name) : tagEntity.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + name.hashCode();
        return result;
    }
}
