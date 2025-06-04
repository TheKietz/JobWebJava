/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.*;
/**
 *
 * @author 11090
 */
public class User {

    private Integer userID;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 2, max = 50, message = "Họ tên phải từ 2 đến 50 ký tự")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String passwordHash;

    @NotBlank(message = "Vai trò không được để trống")
    @Pattern(regexp = "USER|ADMIN", message = "Vai trò chỉ có thể là USER hoặc ADMIN")
    private String role;

    @PastOrPresent(message = "Ngày tạo không được là tương lai")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    // Getters and Setters
    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public User() {
    }

    public User(Integer userID, String fullName, String email, String passwordHash, String role, LocalDate createdAt) {
        this.userID = userID;
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.createdAt = createdAt;
    }
}
