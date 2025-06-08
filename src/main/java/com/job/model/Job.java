/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;

import java.time.LocalDate;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.format.DateTimeFormatter;

public class Job {

    private Integer jobID;

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
    private LocalDate postedAt;

    @Future(message = "Ngày hết hạn phải ở tương lai")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;

    public Job() {
    }

    public Job(Integer jobID, Integer employerID, String title, String description, String location,
            String jobType, String salaryRange, LocalDate postedAt, LocalDate expiryDate) {
        this.jobID = jobID;
        this.employerID = employerID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.jobType = jobType;
        this.salaryRange = salaryRange;
        this.postedAt = postedAt;
        this.expiryDate = expiryDate;
    }
    // Getters and Setters

    public Integer getJobID() {
        return jobID;
    }

    public void setJobID(Integer jobID) {
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

    public LocalDate getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDate postedAt) {
        this.postedAt = postedAt;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getPostedDateStr() {
        if (this.postedAt == null) {
            return "";
        }
        return this.postedAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String getPostedAtFormatted() {
        if (postedAt == null) {
            return "";
        }
        return postedAt.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }

    public String getExpiryDateFormatted() {
        if (expiryDate == null) {
            return "";
        }
        return expiryDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }
}
