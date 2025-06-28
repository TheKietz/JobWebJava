package com.job.model;

import java.math.BigDecimal;
// import com.job.model.Order; // Đảm bảo import các lớp này
// import com.job.model.ServicePackage;

public class OrderItem {
    private Integer id;
    private Integer orderId; // Khóa ngoại tới orders.id
    private Integer packageId; // Khóa ngoại tới service_packages.id
    private Integer quantity;
    private BigDecimal pricePerItem;
    private BigDecimal subtotal;

    // Đối tượng đầy đủ cho khóa ngoại
    private Order order;
    private ServicePackage servicePackage;

    // Constructors
    public OrderItem() {
    }

    public OrderItem(Integer id, Integer orderId, Integer packageId, Integer quantity, BigDecimal pricePerItem, BigDecimal subtotal) {
        this.id = id;
        this.orderId = orderId;
        this.packageId = packageId;
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
        this.subtotal = subtotal;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(BigDecimal pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
               "id=" + id +
               ", orderId=" + orderId +
               ", packageId=" + packageId +
               ", quantity=" + quantity +
               ", pricePerItem=" + pricePerItem +
               ", subtotal=" + subtotal +
               ", order=" + (order != null ? order.getId() : "null") +
               ", servicePackage=" + (servicePackage != null ? servicePackage.getName() : "null") +
               '}';
    }
}