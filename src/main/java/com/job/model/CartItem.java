package com.job.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
// import com.job.model.Cart; // Đảm bảo import các lớp này
// import com.job.model.ServicePackage;

public class CartItem {
    private Integer id;
    private Integer cartId; // Khóa ngoại tới carts.id
    private Integer packageId; // Khóa ngoại tới service_packages.id
    private Integer quantity;
    private BigDecimal priceAtAddition;
    private Timestamp addedAt;

    // Đối tượng đầy đủ cho khóa ngoại
    private Cart cart;
    private ServicePackage servicePackage;

    // Constructors
    public CartItem() {
    }

    public CartItem(Integer id, Integer cartId, Integer packageId, Integer quantity, BigDecimal priceAtAddition, Timestamp addedAt) {
        this.id = id;
        this.cartId = cartId;
        this.packageId = packageId;
        this.quantity = quantity;
        this.priceAtAddition = priceAtAddition;
        this.addedAt = addedAt;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
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

    public BigDecimal getPriceAtAddition() {
        return priceAtAddition;
    }

    public void setPriceAtAddition(BigDecimal priceAtAddition) {
        this.priceAtAddition = priceAtAddition;
    }

    public Timestamp getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Timestamp addedAt) {
        this.addedAt = addedAt;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    @Override
    public String toString() {
        return "CartItem{" +
               "id=" + id +
               ", cartId=" + cartId +
               ", packageId=" + packageId +
               ", quantity=" + quantity +
               ", priceAtAddition=" + priceAtAddition +
               ", addedAt=" + addedAt +
               ", cart=" + (cart != null ? cart.getId() : "null") +
               ", servicePackage=" + (servicePackage != null ? servicePackage.getName() : "null") +
               '}';
    }
}