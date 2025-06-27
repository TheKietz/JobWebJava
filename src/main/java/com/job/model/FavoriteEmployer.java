package com.job.model;

import java.time.LocalDateTime;

public class FavoriteEmployer {

    private Integer id;
    private Integer candidateId;     // Liên kết đến Candidate.id
    private Integer employerId;      // Liên kết đến Employer.id
    private LocalDateTime savedAt;   // Thời gian lưu

    public FavoriteEmployer() {
    }

    public FavoriteEmployer(Integer id, Integer candidateId, Integer employerId, LocalDateTime savedAt) {
        this.id = id;
        this.candidateId = candidateId;
        this.employerId = employerId;
        this.savedAt = savedAt;
    }

    // Getters & Setters
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

    public Integer getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Integer employerId) {
        this.employerId = employerId;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        this.savedAt = savedAt;
    }
}
