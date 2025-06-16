/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 *
 * @author 11090
 */
public class RecentSearch {
    private Integer id;
    private Integer candidateId; // Liên kết với Candidate.id

    @Size(max = 100, message = "Keyword must be less than or equal to 100 characters")
    private String keyword;

    @Size(max = 100, message = "Location must be less than or equal to 100 characters")
    private String location;
    private LocalDateTime searchedAt; // Default handled by DB, but here for completeness

    // Constructors (optional)
    public RecentSearch() {}

    public RecentSearch(Integer id, Integer candidateId, String keyword, String location, LocalDateTime searchedAt) {
        this.id = id;
        this.candidateId = candidateId;
        this.keyword = keyword;
        this.location = location;
        this.searchedAt = searchedAt;
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
    public LocalDateTime getSearchedAt() { return searchedAt; }
    public void setSearchedAt(LocalDateTime searchedAt) { this.searchedAt = searchedAt; }
}
