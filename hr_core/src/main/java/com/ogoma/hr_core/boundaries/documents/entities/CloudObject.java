package com.ogoma.hr_core.boundaries.documents.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "documents")
public class CloudObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long size;
    private Date createdOn;
    private  Date updatedOn;

    public Long getId() {
        return id;
    }

    public CloudObject setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CloudObject setName(String name) {
        this.name = name;
        return this;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public CloudObject setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public CloudObject setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public Long getSize() {
        return size;
    }


    public CloudObject setSize(Long size) {
        this.size = size;
        return this;
    }
}
