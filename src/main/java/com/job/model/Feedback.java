/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;

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
    @Size(max = 100, message = "Subject must be less than or equal to 100 characters")
    private String subject;

    @NotBlank(message = "Message cannot be blank")
    private String message;
    private LocalDateTime submittedAt; // Default handled by DB, but here for completeness

    // Constructors (optional)
    public Feedback() {}

    public Feedback(Integer id, Integer userId, String subject, String message, LocalDateTime submittedAt) {
        this.id = id;
        this.userId = userId;
        this.subject = subject;
        this.message = message;
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
}
