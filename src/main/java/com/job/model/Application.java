/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class Application {
    private Integer applicationID;

    private int candidateID;

    private int jobID;

    @NotBlank(message = "Thư xin việc không được để trống")
    @Size(min = 10, max = 2000, message = "Thư xin việc phải từ 10 đến 2000 ký tự")
    private String coverLetter;

    @NotBlank(message = "URL CV không được để trống")
    @Pattern(regexp = "^(https?://)?(www\\.)?.+\\.(pdf|doc|docx)$", message = "URL CV không hợp lệ")
    private String resumeUrl;

    @PastOrPresent(message = "Ngày nộp không được ở tương lai")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate appliedAt;

    @NotBlank(message = "Trạng thái không được để trống")
    @Pattern(regexp = "Pending|Reviewed|Accepted|Rejected", message = "Trạng thái không hợp lệ")
    private String status;

    public Application() {
    }
    public Application(int candidateID, int jobID, String coverLetter, String resumeUrl, LocalDate appliedAt, String status) {
        this.candidateID = candidateID;
        this.jobID = jobID;
        this.coverLetter = coverLetter;
        this.resumeUrl = resumeUrl;
        this.appliedAt = appliedAt;
        this.status = status;
    }
    // Getters and Setters

    public Integer getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(Integer applicationID) {
        this.applicationID = applicationID;
    }

    public int getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(int candidateID) {
        this.candidateID = candidateID;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public LocalDate getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDate appliedAt) {
        this.appliedAt = appliedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

