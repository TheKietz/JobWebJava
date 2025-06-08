/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Candidate {

    private Integer candidateID;

    private Integer userID;

    @NotBlank(message = "URL CV không được để trống")
    @Pattern(regexp = "^(https?://)?(www\\.)?.+\\.(pdf|doc|docx)$", message = "URL CV không hợp lệ")
    private String resumeUrl;

    @Size(max = 1000, message = "Tiểu sử không được quá 1000 ký tự")
    private String bio;

    @Size(max = 500, message = "Kỹ năng không được quá 500 ký tự")
    private String skills;
    
    public Candidate(Integer candidateID, String resumeUrl, String bio, String skills) {
        this.candidateID = candidateID;
        this.resumeUrl = resumeUrl;
        this.bio = bio;
        this.skills = skills;
    }

    public Candidate() {
    }

    public Candidate(Integer candidateID, Integer userID, String resumeUrl, String bio, String skills){
        this.userID = userID;
        this.candidateID = candidateID;
        this.resumeUrl = resumeUrl;
        this.bio = bio;
        this.skills = skills;
    }
    // Getters and Setters

    public Integer getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(Integer candidateID) {
        this.candidateID = candidateID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
    
}
