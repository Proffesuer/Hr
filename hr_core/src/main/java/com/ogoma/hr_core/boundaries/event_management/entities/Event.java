package com.ogoma.hr_core.boundaries.event_management.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("title")
    private String name;

    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Temporal(TemporalType.TIME)
    private Date startTime;
    private Date endDate;
    private String venue;

    public Long getId() {
        return id;
    }

    public Event setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Event setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Event setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Event setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Event setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getVenue() {
        return venue;
    }

    public Event setVenue(String venue) {
        this.venue = venue;
        return this;
    }

    public Date getEndDate() {
        return endDate;

    }

    public Event setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }
}
