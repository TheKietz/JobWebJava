
package com.job.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "candidates")
public class Candidate {

    private Integer id;

    @NotNull(message = "User ID cannot be null")
    private Integer userId; 

    @NotBlank(message = "Resume URL cannot be blank")
    @Pattern(regexp = "^(http|https)://.*$", message = "Invalid resume URL format")
    @Size(max = 255, message = "Resume URL must be less than or equal to 255 characters")
    private String resumeUrl;

    @NotBlank(message = "Skills cannot be blank")
    private String skills;

    @NotBlank(message = "Location cannot be blank")
    @Size(max = 100, message = "Location must be less than or equal to 100 characters")
    private String location;

    @Size(max = 50, message = "Experience level must be less than or equal to 50 characters")
    private String experienceLevel;

    // Constructors
    public Candidate() {}

    public Candidate(Integer id, Integer userId, String resumeUrl, String skills, String location, String experienceLevel) {
        this.id = id;
        this.userId = userId;
        this.resumeUrl = resumeUrl;
        this.skills = skills;
        this.location = location;
        this.experienceLevel = experienceLevel;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getResumeUrl() { return resumeUrl; }
    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }
}

