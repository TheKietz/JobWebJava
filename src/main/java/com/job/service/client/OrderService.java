package com.job.service; // Đảm bảo đúng package service của bạn

import com.job.model.Cart;
import com.job.model.CartItem;
import com.job.model.Order;
import com.job.model.OrderItem;
import com.job.model.ServicePackage;
import com.job.model.User;
import com.job.repository.CartItemRepository;
import com.job.repository.CartRepository;
import com.job.repository.OrderItemRepository;
import com.job.repository.OrderRepository;
import com.job.repository.ServicePackageRepository; // Bạn cần tạo ServicePackageRepository này
import com.job.repository.UserRepository; // Bạn cần tạo UserRepository này
import com.job.enums.CommonEnums.OrderStatus;
import com.job.enums.CommonEnums.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
// import java.util.Optional; // Không cần import này nữa

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartService cartService; // Để tương tác với giỏ hàng

    @Autowired
    private ServicePackageRepository servicePackageRepository;
    
    @Autowired
    private UserRepository userRepository;

    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        // Tải thông tin người dùng cho mỗi đơn hàng
        for (Order order : orders) { // Dùng vòng lặp for thay vì forEach với Optional
            User user = userRepository.findById(order.getUserId()); // findById trả về User hoặc null
            if (user != null) { // Kiểm tra null trực tiếp
                order.setUser(user);
            }
        }
        return orders;
    }
    // Đặt hàng từ giỏ hàng của người dùng
    @Transactional
    public Order placeOrderFromCart(Integer userId, String paymentMethod) {
        Cart cart = cartService.getCartDetails(userId);
        if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) { // Kiểm tra null cho giỏ hàng và danh sách item
            throw new IllegalArgumentException("Cart is empty or not found for user: " + userId);
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem item : cart.getCartItems()) {
            ServicePackage servicePackage = servicePackageRepository.findById(item.getPackageId()); // Không trả về Optional
            if (servicePackage == null) { // Kiểm tra null trực tiếp
                throw new RuntimeException("Service Package not found for item in cart: " + item.getPackageId());
            }
            
            BigDecimal itemSubtotal = servicePackage.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(itemSubtotal);
            item.setPriceAtAddition(servicePackage.getPrice()); // Cập nhật giá cuối cùng trước khi đặt hàng
        }

        // Tạo Order mới
        Order newOrder = new Order();
        newOrder.setUserId(userId);
        newOrder.setOrderDate(new Timestamp(System.currentTimeMillis()));
        newOrder.setTotalAmount(totalAmount);
        newOrder.setStatus(OrderStatus.PENDING); // Trạng thái ban đầu là Pending
        newOrder.setPaymentMethod(paymentMethod);
        newOrder.setPaymentStatus(PaymentStatus.UNPAID); // Trạng thái thanh toán ban đầu là Unpaid

        Integer orderId = orderRepository.add(newOrder);
        if (orderId == null) {
            throw new RuntimeException("Failed to create order for user: " + userId);
        }
        newOrder.setId(orderId);

        // Chuyển các CartItem thành OrderItem
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setPackageId(cartItem.getPackageId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPricePerItem(cartItem.getPriceAtAddition()); // Giá tại thời điểm thêm vào giỏ hàng
            orderItem.setSubtotal(cartItem.getPriceAtAddition().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            
            Integer orderItemId = orderItemRepository.add(orderItem);
            if (orderItemId == null) {
                throw new RuntimeException("Failed to add order item for package ID: " + cartItem.getPackageId());
            }
        }

        // Xóa giỏ hàng sau khi đặt hàng thành công
        cartService.clearCart(userId);
        
        return newOrder;
    }

    // Lấy tất cả đơn hàng của người dùng
    public List<Order> getUserOrders(Integer userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        // Tải thông tin người dùng cho mỗi đơn hàng nếu cần
        for (Order order : orders) {
            User user = userRepository.findById(order.getUserId()); // Không trả về Optional
            if (user != null) { // Kiểm tra null trực tiếp
                order.setUser(user);
            }
        }
        return orders;
    }

    // Lấy chi tiết một đơn hàng cụ thể (bao gồm các mục đơn hàng và thông tin gói đầy đủ)
    public Order getOrderDetails(Integer orderId) { // Đã thay đổi kiểu trả về từ Optional<Order> sang Order
        Order order = orderRepository.findById(orderId); // Không trả về Optional
        if (order != null) { // Kiểm tra null trực tiếp
            User user = userRepository.findById(order.getUserId()); // Không trả về Optional
            if (user != null) { // Kiểm tra null trực tiếp
                order.setUser(user);
            }
            List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
            // Tải thông tin gói dịch vụ đầy đủ cho mỗi OrderItem
            for (OrderItem item : items) {
                ServicePackage pkg = servicePackageRepository.findById(item.getPackageId()); // Không trả về Optional
                if (pkg != null) { // Kiểm tra null trực tiếp
                    item.setServicePackage(pkg);
                }
            }
            order.setOrderItems(items);
        }
        return order; // Trả về order (có thể là null)
    }

    // Cập nhật trạng thái đơn hàng
    @Transactional
    public void updateOrderStatus(Integer orderId, OrderStatus status) {
        orderRepository.updateStatus(orderId, status);
    }
    
    // Cập nhật trạng thái thanh toán
    @Transactional
    public void updatePaymentStatus(Integer orderId, PaymentStatus status) {
        orderRepository.updatePaymentStatus(orderId, status);
    }
}