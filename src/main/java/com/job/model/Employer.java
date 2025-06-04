/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *
 * @author 11090
 */
public class Employer {
    private int employerID;

    private int userID;

    @NotBlank(message = "Tên công ty không được để trống")
    @Size(min = 2, max = 100, message = "Tên công ty phải từ 2 đến 100 ký tự")
    private String companyName;

    @NotBlank(message = "Website không được để trống")
    @Pattern(regexp = "^(https?://)?(www\\.)?[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}.*$", message = "Website không hợp lệ")
    private String website;

    @Size(max = 1000, message = "Mô tả không được quá 1000 ký tự")
    private String description;

    @Pattern(regexp = "^(https?://)?(www\\.)?[a-zA-Z0-9./_-]+\\.(jpg|jpeg|png|gif)$", message = "URL ảnh logo không hợp lệ")
    private String logoUrl;


    // Getters and Setters

    public int getEmployerID() {
        return employerID;
    }

    public void setEmployerID(int employerID) {
        this.employerID = employerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}

