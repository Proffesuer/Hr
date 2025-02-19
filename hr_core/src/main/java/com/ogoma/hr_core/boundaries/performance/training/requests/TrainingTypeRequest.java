package com.ogoma.hr_core.boundaries.performance.training.requests;

import com.ogoma.hr_core.boundaries.performance.training.entities.TrainingType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

public class TrainingTypeRequest {
    @NotBlank(message = "Type is required")
    private String type;
    @NotBlank(message = "Description is required")
    private String description;
    @Enumerated(EnumType.STRING)
    public TrainingType.Status status;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TrainingType.Status getStatus() {
        return status;
    }

    public void setStatus(TrainingType.Status status) {
        this.status = status;
    }
}
