/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;

import com.job.enums.CommonEnums.MessageStatus;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 *
 * @author 11090
 */
public class Message {
    private Integer id;
    private Integer senderId; // Liên kết với User.id
    private Integer receiverId; // Liên kết với User.id

    @NotBlank(message = "Content cannot be blank")
    private String content;
    private LocalDateTime sentAt; // Default handled by DB, but here for completeness
    private MessageStatus status; // Default handled by DB, but here for completeness

    // Constructors (optional)
    public Message() {}

    public Message(Integer id, Integer senderId, Integer receiverId, String content, LocalDateTime sentAt, MessageStatus status) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.sentAt = sentAt;
        this.status = status;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getSenderId() { return senderId; }
    public void setSenderId(Integer senderId) { this.senderId = senderId; }
    public int getReceiverId() { return receiverId; }
    public void setReceiverId(Integer receiverId) { this.receiverId = receiverId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
    public MessageStatus getStatus() { return status; }
    public void setStatus(MessageStatus status) { this.status = status; }
}
