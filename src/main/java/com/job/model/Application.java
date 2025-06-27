/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import com.job.enums.CommonEnums.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Application {
   private Integer id;
    private Integer candidateId; // Liên kết với Candidate.id
    private Integer jobId; // Liên kết với Job.id
    private Job job;

    @NotBlank(message = "Resume URL cannot be blank")
    @Pattern(regexp = "^(http|https)://.*$", message = "Invalid resume URL format")
    @Size(max = 255, message = "Resume URL must be less than or equal to 255 characters")
    private String resumeUrl;

    private ApplicationStatus status; // Default handled by DB, but here for completeness

    @DecimalMin(value = "0.0", message = "Score cannot be negative")
    @DecimalMax(value = "100.0", message = "Score cannot exceed 100")
    private BigDecimal score;
    
    private LocalDateTime appliedAt; // Default handled by DB, but here for completeness

    // Constructors (optional)
    public Application() {}

    public Application(Integer id, Integer candidateId, Integer jobId, String resumeUrl, ApplicationStatus status, BigDecimal score, LocalDateTime appliedAt) {
        this.id = id;
        this.candidateId = candidateId;
        this.jobId = jobId;
        this.resumeUrl = resumeUrl;
        this.status = status;
        this.score = score;
        this.appliedAt = appliedAt;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getCandidateId() { return candidateId; }
    public void setCandidateId(Integer candidateId) { this.candidateId = candidateId; }
    public Integer getJobId() { return jobId; }
    public void setJobId(Integer jobId) { this.jobId = jobId; }
    public String getResumeUrl() { return resumeUrl; }
    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }
    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
    public BigDecimal getScore() { return score; }
    public void setScore(BigDecimal score) { this.score = score; }
    public LocalDateTime getAppliedAt() { return appliedAt; }
    public void setAppliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; }
    public Job getJob() {return job;}
    public void setJob(Job job) {this.job = job;}
}

