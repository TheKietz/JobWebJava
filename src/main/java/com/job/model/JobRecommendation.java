/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;

import java.time.LocalDateTime;

/**
 *
 * @author 11090
 */
public class JobRecommendation {

    private Integer id;
    private Integer candidateId; // Liên kết với Candidate.id
    private Integer jobId; // Liên kết với Job.id

    private String reason; // Có thể để trống nếu không có lý do cụ thể

    private LocalDateTime recommendedAt; // Default handled by DB, but here for completeness

    // Constructors (optional)
    public JobRecommendation() {
    }

    public JobRecommendation(Integer id, Integer candidateId, Integer jobId, String reason, LocalDateTime recommendedAt) {
        this.id = id;
        this.candidateId = candidateId;
        this.jobId = jobId;
        this.reason = reason;
        this.recommendedAt = recommendedAt;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getRecommendedAt() {
        return recommendedAt;
    }

    public void setRecommendedAt(LocalDateTime recommendedAt) {
        this.recommendedAt = recommendedAt;
    }
}
