/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.dto;

/**
 *
 * @author 11090
 */
public class RecommentCandidateDTO {
    private int id;
    private String fullName;
    private String avatarUrl;
    private String location;
    private String experienceLevel;
    private String skills;
    private String resumeUrl;
    private String category;

    public RecommentCandidateDTO(int id,String avatarUrl,String fullName,String category, String resumeUrl,  String location, String experienceLevel, String skills) {
        this.id=id;
        this.avatarUrl = avatarUrl;
        this.fullName = fullName;
        this.category = category;
        this.resumeUrl = resumeUrl;
        this.location = location;
        this.experienceLevel = experienceLevel;
        this.skills = skills;        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }
    
}
