/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;

import com.job.enums.CommonEnums.FeedbackStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 *
 * @author 11090
 */
public class Feedback {
    private Integer id;
    private Integer userId; // Liên kết với User.id

    @NotBlank(message = "Subject cannot be blank")   
    private String subject;

    @NotBlank(message = "Message cannot be blank")
    private String message;
    private String feedbackType;
    private String reportCategory;
    private String attachmentUrls;
    private FeedbackStatus status;
    private LocalDateTime submittedAt; // Default handled by DB, but here for completeness

    // Constructors (optional)
    public Feedback() {}

    public Feedback(Integer id, Integer userId, String subject, String message, String feedbackType, String reportCategory, String attachmentUrls, FeedbackStatus status, LocalDateTime submittedAt) {
        this.id = id;
        this.userId = userId;
        this.subject = subject;
        this.message = message;
        this.feedbackType = feedbackType;
        this.reportCategory = reportCategory;
        this.attachmentUrls = attachmentUrls;
        this.status = status;
        this.submittedAt = submittedAt;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getReportCategory() {
        return reportCategory;
    }

    public void setReportCategory(String reportCategory) {
        this.reportCategory = reportCategory;
    }

    public String getAttachmentUrls() {
        return attachmentUrls;
    }

    public void setAttachmentUrls(String attachmentUrls) {
        this.attachmentUrls = attachmentUrls;
    }

    public FeedbackStatus getStatus() {
        return status;
    }

    public void setStatus(FeedbackStatus status) {
        this.status = status;
    }
    
}
