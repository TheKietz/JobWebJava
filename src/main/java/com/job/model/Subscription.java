/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author 11090
 */
public class Subscription {
    private Integer id;
    private Integer employerId; // Liên kết với Employer.id
    private Integer packageId; // Liên kết với ServicePackage.id

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;

    @NotBlank(message = "Payment method cannot be blank")
    @Size(max = 50, message = "Payment method must be less than or equal to 50 characters")
    private String paymentMethod;

    @DecimalMin(value = "0.0", inclusive = false, message = "Amount paid must be positive")
    private BigDecimal amountPaid;
    private LocalDateTime createdAt; // Default handled by DB, but here for completeness

    // Constructors (optional)
    public Subscription() {}

    public Subscription(Integer id, Integer employerId, Integer packageId, LocalDate startDate, LocalDate endDate, String paymentMethod, BigDecimal amountPaid, LocalDateTime createdAt) {
        this.id = id;
        this.employerId = employerId;
        this.packageId = packageId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentMethod = paymentMethod;
        this.amountPaid = amountPaid;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public int getEmployerId() { return employerId; }
    public void setEmployerId(Integer employerId) { this.employerId = employerId; }
    public int getPackageId() { return packageId; }
    public void setPackageId(Integer packageId) { this.packageId = packageId; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public BigDecimal getAmountPaid() { return amountPaid; }
    public void setAmountPaid(BigDecimal amountPaid) { this.amountPaid = amountPaid; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
