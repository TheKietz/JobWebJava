/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;

import java.sql.Timestamp;
import java.util.List;
// import com.job.model.User; // Đảm bảo import lớp User nếu nó nằm trong package khác

public class Cart {

    private Integer id;
    private Integer userId; // Khóa ngoại tới users.id
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private User user;
    private List<CartItem> cartItems;
    // Có thể thêm đối tượng User nếu bạn muốn tải cùng thông tin người dùng

    // Constructors
    public Cart() {
    }

    public Cart(Integer id, Integer userId, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public String toString() {
        return "Cart{"
                + "id=" + id
                + ", userId=" + userId
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt
                + ", user=" + (user != null ? user.getFullName() : "null")
                + // Tránh lỗi null pointer
                '}';
    }
}
