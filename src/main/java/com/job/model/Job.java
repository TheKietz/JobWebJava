/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;
import java.util.Date;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

public class Job {
    private int jobID;

    private int employerID;

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(min = 3, max = 100, message = "Tiêu đề phải từ 3 đến 100 ký tự")
    private String title;

    @NotBlank(message = "Mô tả công việc không được để trống")
    @Size(min = 10, message = "Mô tả phải có ít nhất 10 ký tự")
    private String description;

    @NotBlank(message = "Địa điểm không được để trống")
    private String location;

    @NotBlank(message = "Loại công việc không được để trống")
    @Pattern(regexp = "Full-Time|Part-Time|Internship|Contract", message = "Loại công việc không hợp lệ")
    private String jobType;

    @NotBlank(message = "Mức lương không được để trống")
    private String salaryRange;

    @PastOrPresent(message = "Ngày đăng không được ở tương lai")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date postedAt;

    @Future(message = "Ngày hết hạn phải ở tương lai")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;

    // Getters and Setters

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public int getEmployerID() {
        return employerID;
    }

    public void setEmployerID(int employerID) {
        this.employerID = employerID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }

    public Date getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Date postedAt) {
        this.postedAt = postedAt;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}

