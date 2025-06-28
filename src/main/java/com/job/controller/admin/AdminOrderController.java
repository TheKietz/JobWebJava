/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.controller.admin;

import com.job.enums.CommonEnums;
import com.job.enums.CommonEnums.OrderStatus;
import com.job.model.User;
import com.job.model.Order;
import com.job.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable; // Import PathVariable
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Controller dành cho quản lý đơn hàng của Admin.
 */
@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    /**
     * Hiển thị danh sách tất cả các đơn hàng cho Admin, có thể lọc theo trạng thái.
     * Kế thừa layout admin/layout/main.jsp
     */
    @GetMapping
    public String viewAdminOrders(Model model,
                                  HttpSession session,
                                  @RequestParam(value = "status", required = false) String statusFilter,
                                  RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != CommonEnums.Role.ADMIN) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn không có quyền truy cập trang quản lý đơn hàng của Admin.");
            return "redirect:/app/login";
        }

        List<Order> allOrders = orderService.getAllOrders();
        
        List<Order> filteredOrders;
        String currentStatusFilter = (statusFilter != null && !statusFilter.isEmpty()) ? statusFilter.toUpperCase() : "ALL";

        if (!currentStatusFilter.equals("ALL")) {
            try {
                OrderStatus statusEnum = OrderStatus.valueOf(currentStatusFilter);
                filteredOrders = allOrders.stream()
                                          .filter(order -> order.getStatus() == statusEnum)
                                          .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                filteredOrders = allOrders;
                model.addAttribute("errorMessage", "Trạng thái đơn hàng không hợp lệ: " + statusFilter);
                currentStatusFilter = "ALL"; 
            }
        } else {
            filteredOrders = allOrders;
        }
        
        model.addAttribute("orders", filteredOrders);
        model.addAttribute("statusFilter", currentStatusFilter);
        model.addAttribute("allStatuses", Arrays.asList(OrderStatus.values()));
        model.addAttribute("body", "/WEB-INF/views/admin/order/admin_orders.jsp");
        return "admin/layout/main";
    }

    /**
     * Hiển thị chi tiết một đơn hàng cụ thể cho Admin.
     * Kế thừa layout admin/layout/main.jsp
     */
    @GetMapping("/{orderId}/details") // URL mới để xem chi tiết đơn hàng của Admin
    public String viewAdminOrderDetails(@PathVariable("orderId") Integer orderId,
                                        Model model,
                                        HttpSession session,
                                        RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != CommonEnums.Role.ADMIN) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn không có quyền truy cập chi tiết đơn hàng này.");
            return "redirect:/app/login";
        }

        Order order = orderService.getOrderDetails(orderId); // Lấy chi tiết đơn hàng
        if (order == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy đơn hàng với ID: " + orderId);
            return "redirect:/admin/orders"; // Quay lại trang danh sách đơn hàng Admin
        }

        model.addAttribute("order", order);
        model.addAttribute("allStatuses", Arrays.asList(OrderStatus.values())); // Để dùng trong dropdown cập nhật trạng thái
        model.addAttribute("body", "/WEB-INF/views/admin/order/admin_order_details.jsp"); // View chi tiết riêng của Admin
        return "admin/layout/main"; // Kế thừa layout của Admin
    }

    // --- CHỨC NĂNG: CẬP NHẬT TRẠNG THÁI ĐƠN HÀNG (CHỈ DÀNH CHO ADMIN) ---
    @PostMapping("/update-status")
    public String updateOrderStatus(@RequestParam("orderId") Integer orderId,
                                    @RequestParam("newStatus") String newStatusString,
                                    @RequestParam(value = "currentStatusFilter", required = false, defaultValue = "ALL") String currentStatusFilter,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != CommonEnums.Role.ADMIN) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn không có quyền thực hiện thao tác này.");
            return "redirect:/app/login";
        }

        try {
            OrderStatus newStatus = OrderStatus.valueOf(newStatusString.toUpperCase());
            orderService.updateOrderStatus(orderId, newStatus);
            redirectAttributes.addFlashAttribute("successMessage", "Đã cập nhật trạng thái đơn hàng #" + orderId + " thành " + newStatus.name());
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Trạng thái đơn hàng không hợp lệ: " + newStatusString);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật trạng thái đơn hàng #" + orderId + ": " + e.getMessage());
            e.printStackTrace();
        }
        // Redirect về trang danh sách đơn hàng Admin Order
        redirectAttributes.addAttribute("status", currentStatusFilter);
        return "redirect:/admin/orders";
    }
}
