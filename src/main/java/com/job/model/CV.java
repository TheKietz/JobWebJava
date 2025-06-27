package com.job.model;

import java.time.LocalDateTime;

public class CV {
    private Integer id;
    private Integer candidateId;
    private String title;
    private String summary;
    private String fileUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CV() {}

    public CV(Integer id, Integer candidateId, String title, String summary, String fileUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.candidateId = candidateId;
        this.title = title;
        this.summary = summary;
        this.fileUrl = fileUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getCandidateId() { return candidateId; }
    public void setCandidateId(Integer candidateId) { this.candidateId = candidateId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

