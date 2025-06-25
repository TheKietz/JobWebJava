/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;

import com.job.enums.CommonEnums.BannerPosition;
import com.job.enums.CommonEnums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 *
 * @author 11090
 */
public class Banner {

    private Integer id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title must be less than or equal to 100 characters")
    private String title;

    @Size(max = 255, message = "Image file name must be <= 255 characters")
    private String imageUrl;

    @Size(max = 255, message = "Link URL must be <= 255 characters")
    private String linkUrl;

    @NotNull(message = "Position cannot be null")
    private BannerPosition position;

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;

    private Status status; // Default handled by DB, but here for completeness

    // Constructors (optional)
    public Banner() {
    }

    public Banner(Integer id, String title, String imageUrl, String linkUrl, BannerPosition position, LocalDate startDate, LocalDate endDate, Status status) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.linkUrl = linkUrl;
        this.position = position;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public BannerPosition getPosition() {
        return position;
    }

    public void setPosition(BannerPosition position) {
        this.position = position;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
