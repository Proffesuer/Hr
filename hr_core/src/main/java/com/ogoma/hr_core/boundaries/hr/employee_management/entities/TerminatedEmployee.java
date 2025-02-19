package com.ogoma.hr_core.boundaries.hr.employee_management.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "terminated_employees")
public class TerminatedEmployee {
    // details of employees who have been terminated
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "employee_id",unique = true)
    @NotNull(message = "Select employee to terminate")
    private Long employeeId;
    @OneToOne
    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
    private Employee employee;
    @Temporal(TemporalType.TIMESTAMP)
    private Date finalPayDate;
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Termination date is required")
    private Date terminationDate;
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Notice date is required")
    private Date noticeDate;
    @NotBlank(message = "Reason is required")
    private String reason;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedAt;
    @Column(name = "termination_reason_code")
    public String terminationReasonCode;
    @OneToOne(cascade = CascadeType.PERSIST)
    //Note that when using referencedColumnName to a non primary key column,
    // the associated class has to be Serializable. Also note
    // that the referencedColumnName to a non primary key column has
    // to be mapped to a property having a single column (other cases might not work
    @JoinColumn(name = "termination_reason_code", insertable = false, updatable = false, referencedColumnName = "code")
    private TerminationReason terminationReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getFinalPayDate() {
        return finalPayDate;
    }

    public void setFinalPayDate(Date finalPayDate) {
        this.finalPayDate = finalPayDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public Date getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(Date noticeDate) {
        this.noticeDate = noticeDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public TerminationReason getTerminationReason() {
        return terminationReason;
    }

    public void setTerminationReason(TerminationReason terminationReason) {
        this.terminationReason = terminationReason;
    }

    public String getTerminationReasonCode() {
        return terminationReasonCode;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
    public void setTerminationReasonCode(String terminationReasonCode) {
        this.terminationReasonCode = terminationReasonCode;
    }
}
