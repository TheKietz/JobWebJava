/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;

import com.job.enums.CommonEnums.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Job {

    private Integer id;

    @NotNull(message = "Employer ID cannot be null")
    private Integer employerId; // Liên kết với Employer.id

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title must be less than or equal to 255 characters")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotBlank(message = "Location cannot be blank")
    @Size(max = 100, message = "Location must be less than or equal to 100 characters")
    private String location;

    @DecimalMin(value = "0.0", inclusive = false, message = "Salary min must be positive")
    private BigDecimal salaryMin;

    @DecimalMin(value = "0.0", inclusive = false, message = "Salary max must be positive")
    private BigDecimal salaryMax;

    @Size(max = 50, message = "Job type must be less than or equal to 50 characters")
    private String jobType;

    private JobStatus status; // Default handled by DB

    @NotBlank(message = "Category cannot be blank")
    @Size(max = 100, message = "Category must be less than or equal to 100 characters")
    private String category;

    private LocalDateTime createdAt; // Default handled by DB
    private LocalDateTime expiredAt;
    private int totalApplications;
    private int candidateNumber;

    public int getCandidateNumber() {
        return candidateNumber;
    }

    public void setCandidateNumber(int candidateNumber) {
        this.candidateNumber = candidateNumber;
    }

    public int getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(int totalApplications) {
        this.totalApplications = totalApplications;
    }

    // Constructors
    public Job() {
    }

    public Job(Integer id, Integer employerId, String title, String description, String location,
            BigDecimal salaryMin, BigDecimal salaryMax, String jobType, JobStatus status, String category,
            LocalDateTime createdAt, LocalDateTime expiredAt) {
        this.id = id;
        this.employerId = employerId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.jobType = jobType;
        this.status = status;
        this.category = category;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Integer employerId) {
        this.employerId = employerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(BigDecimal salaryMin) {
        this.salaryMin = salaryMin;
    }

    public BigDecimal getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(BigDecimal salaryMax) {
        this.salaryMax = salaryMax;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getPostedDateStr() {
        if (this.createdAt == null) {
            return "";
        }
        return this.createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String getPostedAtFormatted() {
        if (createdAt == null) {
            return "";
        }
        return createdAt.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }

    public String getExpiryDateFormatted() {
        if (expiredAt == null) {
            return "";
        }
        return expiredAt.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }
    
    public Date getCreatedAtAsDate() {
        return this.createdAt != null ? Date.from(this.createdAt.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }
    
    public Date getExpiredAtAsDate() {
        return this.expiredAt != null ? Date.from(this.expiredAt.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }
}
