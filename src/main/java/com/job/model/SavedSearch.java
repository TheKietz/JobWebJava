/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author 11090
 */
public class SavedSearch {
    private Integer id;
    private Integer candidateId; // Liên kết với Candidate.id

    @Size(max = 100, message = "Keyword must be less than or equal to 100 characters")
    private String keyword;

    @Size(max = 100, message = "Location must be less than or equal to 100 characters")
    private String location;

    @DecimalMin(value = "0.0", message = "Salary min cannot be negative")
    private BigDecimal salaryMin;

    @DecimalMin(value = "0.0", message = "Salary max cannot be negative")
    private BigDecimal salaryMax;
    private LocalDateTime createdAt; // Default handled by DB, but here for completeness

    // Constructors (optional)
    public SavedSearch() {}

    public SavedSearch(Integer id, Integer candidateId, String keyword, String location, BigDecimal salaryMin, BigDecimal salaryMax, LocalDateTime createdAt) {
        this.id = id;
        this.candidateId = candidateId;
        this.keyword = keyword;
        this.location = location;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getCandidateId() { return candidateId; }
    public void setCandidateId(Integer candidateId) { this.candidateId = candidateId; }
    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public BigDecimal getSalaryMin() { return salaryMin; }
    public void setSalaryMin(BigDecimal salaryMin) { this.salaryMin = salaryMin; }
    public BigDecimal getSalaryMax() { return salaryMax; }
    public void setSalaryMax(BigDecimal salaryMax) { this.salaryMax = salaryMax; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
