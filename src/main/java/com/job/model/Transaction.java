/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;
import com.job.enums.CommonEnums.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author 11090
 */
public class Transaction {
    private Integer id;
    private Integer userId;
    private String userName;
    
    private Integer packageId;
    private String packageName;
    
    
    private BigDecimal amount;
    private String paymentMethod;
    
    private TransactionStatus status; // enum mới
    private LocalDateTime createdAt;
    
    // Constructors
    public Transaction() {}

    public Transaction(Integer id, Integer userId, String userName, Integer packageId, String packageName,
                       BigDecimal amount, String paymentMethod, LocalDateTime createdAt, TransactionStatus status) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.packageId = packageId;
        this.packageName = packageName;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
        this.status = status;
    }

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Integer getPackageId() { return packageId; }
    public void setPackageId(Integer packageId) { this.packageId = packageId; }

    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public TransactionStatus getStatus() { return status; }
    public void setStatus(TransactionStatus status) { this.status = status; }
    
    public String getCreatedAtStr() {
    if (createdAt == null) return "";
    return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
}
}
