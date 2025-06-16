/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/**
 *
 * @author 11090
 */
public class ServicePackage {
    private Integer id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name must be less than or equal to 50 characters")
    private String name;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private BigDecimal price;

    @Min(value = 1, message = "Duration days must be at least 1")
    private Integer durationDays;

    @Min(value = 0, message = "Job post limit cannot be negative")
    private Integer jobPostLimit;
    private Boolean resumeAccess;
    private String description;

    // Constructors (optional)
    public ServicePackage() {}

    public ServicePackage(Integer id, String name, BigDecimal price, Integer durationDays, Integer jobPostLimit, Boolean resumeAccess, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.durationDays = durationDays;
        this.jobPostLimit = jobPostLimit;
        this.resumeAccess = resumeAccess;
        this.description = description;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getDurationDays() { return durationDays; }
    public void setDurationDays(Integer durationDays) { this.durationDays = durationDays; }
    public Integer getJobPostLimit() { return jobPostLimit; }
    public void setJobPostLimit(Integer jobPostLimit) { this.jobPostLimit = jobPostLimit; }
    public Boolean getResumeAccess() { return resumeAccess; }
    public void setResumeAccess(Boolean resumeAccess) { this.resumeAccess = resumeAccess; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
