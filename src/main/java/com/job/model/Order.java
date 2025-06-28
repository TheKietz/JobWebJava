package com.job.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
// import com.job.model.User; // Đảm bảo import lớp User nếu nó nằm trong package khác

// Giả định bạn có các Enum sau. Nếu không, hãy tạo chúng hoặc sử dụng String.
import com.job.enums.CommonEnums.OrderStatus; // Ví dụ: ENUM('pending', 'completed', 'cancelled', 'failed')
import com.job.enums.CommonEnums.PaymentStatus; // Ví dụ: ENUM('paid', 'unpaid', 'refunded')
import java.util.List;

public class Order {

    private Integer id;
    private Integer userId; // Khóa ngoại tới users.id
    private Timestamp orderDate;
    private BigDecimal totalAmount;
    private OrderStatus status; // Enum cho trạng thái đơn hàng
    private String paymentMethod;
    private PaymentStatus paymentStatus; // Enum cho trạng thái thanh toán

    // Có thể thêm đối tượng User nếu bạn muốn tải cùng thông tin người dùng
    private User user;
    private List<OrderItem> orderItems;

    // Constructors
    public Order() {
    }

    public Order(Integer id, Integer userId, Timestamp orderDate, BigDecimal totalAmount, OrderStatus status, String paymentMethod, PaymentStatus paymentStatus) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    // Getters and Setters
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
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

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{"
                + "id=" + id
                + ", userId=" + userId
                + ", orderDate=" + orderDate
                + ", totalAmount=" + totalAmount
                + ", status=" + status
                + ", paymentMethod='" + paymentMethod + '\''
                + ", paymentStatus=" + paymentStatus
                + ", user=" + (user != null ? user.getFullName() : "null")
                + '}';
    }
}
