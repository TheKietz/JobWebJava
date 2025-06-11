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
public class FavoriteJob {
     private Integer id;
    private Integer candidateId; // Liên kết với Candidate.id
    private Integer jobId; // Liên kết với Job.id
    private LocalDateTime savedAt; // Default handled by DB, but here for completeness

    // Constructors (optional)
    public FavoriteJob() {}

    public FavoriteJob(Integer id, Integer candidateId, Integer jobId, LocalDateTime savedAt) {
        this.id = id;
        this.candidateId = candidateId;
        this.jobId = jobId;
        this.savedAt = savedAt;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getCandidateId() { return candidateId; }
    public void setCandidateId(Integer candidateId) { this.candidateId = candidateId; }
    public Integer getJobId() { return jobId; }
    public void setJobId(Integer jobId) { this.jobId = jobId; }
    public LocalDateTime getSavedAt() { return savedAt; }
    public void setSavedAt(LocalDateTime savedAt) { this.savedAt = savedAt; }
}
