/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;

import com.job.enums.CommonEnums.NotificationStatus;
import com.job.enums.CommonEnums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 *
 * @author 11090
 */
public class Notification {
    private Integer id;
    private Integer userId; // Liên kết với User.id

    @NotBlank(message = "Message cannot be blank")
    private String message;

    @NotNull(message = "Type cannot be null")
    private NotificationType type;

    private NotificationStatus status; // Default handled by DB, but here for completeness
    private LocalDateTime createdAt; // Default handled by DB, but here for completeness

    // Constructors (optional)
    public Notification() {}

    public Notification(Integer id, Integer userId, String message, NotificationType type, NotificationStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }
    public NotificationStatus getStatus() { return status; }
    public void setStatus(NotificationStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
