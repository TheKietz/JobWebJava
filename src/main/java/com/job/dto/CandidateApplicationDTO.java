/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.dto;

/**
 *
 * @author 11090
 */
public class CandidateApplicationDTO {
    private String fullName;
    private String jobTitle;
    private String resumeUrl;
    private String avatarUrl;

    // Constructor
    public CandidateApplicationDTO(String fullName, String jobTitle, String resumeUrl, String avatarUrl) {
        this.fullName = fullName;
        this.jobTitle = jobTitle;
        this.resumeUrl = resumeUrl;
        this.avatarUrl = avatarUrl;
    }

    // Getters and Setters (hoặc dùng Lombok @Data nếu có)

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
