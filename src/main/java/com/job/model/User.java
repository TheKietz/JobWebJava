package com.job.model;

import com.job.enums.CommonEnums.Role;
import com.job.enums.CommonEnums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "users")
public class User {
    private Integer id;

    @NotBlank(message = "Full name cannot be blank")
    @Size(max = 100, message = "Full name must be less than or equal to 100 characters")
    private String fullName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must be less than or equal to 100 characters")
    private String email;

    @Size(max = 255, message = "Password must be less than or equal to 255 characters")
    private String password;

    @Size(max = 20, message = "Phone must be less than or equal to 20 characters")
    private String phone;

    @NotNull(message = "Role cannot be null")
    private Role role;

    private Status status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public User() {}

    public User(Integer id, String fullName, String email, String password, String phone, Role role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.status = Status.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "User{id=" + id + ", fullName='" + fullName + "', email='" + email + "', role=" + role + "}";
    }
}