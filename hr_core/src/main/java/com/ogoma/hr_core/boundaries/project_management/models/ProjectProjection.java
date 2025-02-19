package com.ogoma.hr_core.boundaries.project_management.models;

import com.ogoma.hr_core.boundaries.project_management.entities.Project;
import com.ogoma.hr_core.boundaries.project_management.enums.Priority;
import com.ogoma.hr_core.boundaries.project_management.enums.RateType;

import java.math.BigDecimal;
import java.util.Date;

public class ProjectProjection {
    private Long id;
    private String description;
    private String clientName;
    private String name;
    private Long leadEmployeeId;
    private Date startDate;
    private Date endDate;
    private Date createdAt;
    private RateType rateType;
    private BigDecimal rate;
    private Priority priority;

    public ProjectProjection(Project project) {
//        this.clientName =
//                project.getClient().getUser().getFullName();
        this.description = project.getDescription();
        this.name = project.getName();
        this.leadEmployeeId = project.getTeamLeaderId();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
        this.createdAt = project.getCreatedAt();
        this.rateType = project.getRateType();
        this.rate = project.getRate();
        this.priority = project.getPriority();
        this.id=project.getId();



    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLeadEmployeeId() {
        return leadEmployeeId;
    }

    public void setLeadEmployeeId(Long leadEmployeeId) {
        this.leadEmployeeId = leadEmployeeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public RateType getRateType() {
        return rateType;
    }

    public void setRateType(RateType rateType) {
        this.rateType = rateType;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
//    public String getClientName();
//
//    public Long getLeadEmployeeId();
//
//    public String getLeadFirstName();
//
//    public String getLeadLastName();
//
//    public String getLeadDesignation();

//    public Integer getCompleteTasks();
//
//    public Integer getOpenTasks();
//
//    public Integer getMemberCount();
//
//    public String getClientFirstName();
//
//    public Long getClientId();
//
//    public String getClientLastName();
//    public default long getTotalHours() {
//        long millisecondsDiff =
//                Math.abs(this.getEndDate().getTime() - this.getStartDate().getTime());
//        long totalHours = TimeUnit.HOURS.convert(millisecondsDiff, TimeUnit.MILLISECONDS);
//        return totalHours;
//    }
//
//    public default String getClientFullName() {
//        String lastName = this.getClientLastName();
//        String firstName = this.getClientFirstName();
//        if (null != firstName) {
//            return firstName.concat(" ").concat(lastName);
//        }
//        return lastName;
//    }
//
//    public default String getLeadFullName() {
//        String lastName = this.getLeadLastName();
//        String firstName = this.getLeadFirstName();
//        if (null != firstName) {
//            return firstName.concat(" ").concat(lastName);
//        }
//        return lastName;
//    }
}
