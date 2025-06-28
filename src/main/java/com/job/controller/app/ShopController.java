package com.job.controller.app;

import com.job.enums.CommonEnums;
import com.job.enums.CommonEnums.OrderStatus;
import com.job.enums.CommonEnums.PaymentStatus;
import com.job.model.Cart;
import com.job.model.Order;
import com.job.model.User;
import com.job.service.CartService;
import com.job.service.OrderService;
import com.job.service.ServicePackageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays; // Thêm import này cho Arrays.asList()

@Controller
@RequestMapping("/app/shop")
public class ShopController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ServicePackageService servicePackageService;

    // --- Các chức năng giỏ hàng và đặt hàng cho người dùng (đã có) ---
    // (Giữ nguyên các phương thức: addToCart, viewCart, updateCartItemQuantity, removeCartItem, showCheckoutForm, placeOrder, viewUserOrders, viewOrderDetails)
    
    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("packageId") Integer packageId,
                            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng.");
            return "redirect:/app/login";
        }

        try {
            cartService.addItemToCart(loggedInUser.getId(), packageId, quantity);
            redirectAttributes.addFlashAttribute("successMessage", "Đã thêm sản phẩm vào giỏ hàng!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thêm sản phẩm vào giỏ hàng: " + e.getMessage());
            e.printStackTrace();
        }
        
        return "redirect:/app/service_packages";
    }

    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng đăng nhập để xem giỏ hàng.");
            return "redirect:/app/login";
        }

        Cart cart = cartService.getCartDetails(loggedInUser.getId());
        model.addAttribute("cart", cart);
        model.addAttribute("body", "/WEB-INF/views/app/shop/cart.jsp");
        return "app/layout/main";
    }

    @PostMapping("/cart/update-quantity")
    public String updateCartItemQuantity(@RequestParam("cartItemId") Integer cartItemId,
                                         @RequestParam("quantity") int quantity,
                                         RedirectAttributes redirectAttributes) {
        try {
            if (quantity <= 0) {
                 redirectAttributes.addFlashAttribute("errorMessage", "Số lượng phải lớn hơn 0. Nếu muốn xóa, vui lòng dùng nút xóa.");
            } else {
                cartService.updateCartItemQuantity(cartItemId, quantity);
                redirectAttributes.addFlashAttribute("successMessage", "Đã cập nhật số lượng sản phẩm trong giỏ hàng.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật số lượng: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/app/shop/cart";
    }

    @PostMapping("/cart/remove-item")
    public String removeCartItem(@RequestParam("cartItemId") Integer cartItemId,
                                 RedirectAttributes redirectAttributes) {
        try {
            if (cartService.removeCartItem(cartItemId)) {
                redirectAttributes.addFlashAttribute("successMessage", "Đã xóa sản phẩm khỏi giỏ hàng.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sản phẩm trong giỏ hàng để xóa.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/app/shop/cart";
    }

    @GetMapping("/checkout")
    public String showCheckoutForm(Model model, HttpSession session, RedirectAttributes redirectAttributes,
                                   @RequestParam(value = "packageId", required = false) Integer packageId) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng đăng nhập để thanh toán.");
            return "redirect:/app/login";
        }

        Cart cart = cartService.getCartDetails(loggedInUser.getId());

        if (packageId != null) {
            try {
                if (cart == null || cart.getCartItems() == null || cart.getCartItems().stream().noneMatch(item -> item.getPackageId().equals(packageId))) {
                     cartService.addItemToCart(loggedInUser.getId(), packageId, 1);
                     cart = cartService.getCartDetails(loggedInUser.getId());
                }
            } catch (IllegalArgumentException e) {
                redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
                return "redirect:/app/service_packages";
            }
        }

        if (cart == null || cart.getCartItems().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Giỏ hàng của bạn đang trống.");
            return "redirect:/app/shop/cart";
        }

        model.addAttribute("cart", cart);
        model.addAttribute("body", "/WEB-INF/views/app/shop/checkout.jsp");
        return "app/layout/main";
    }

    @PostMapping("/place-order")
    public String placeOrder(@RequestParam("paymentMethod") String paymentMethod,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng đăng nhập để đặt hàng.");
            return "redirect:/app/login";
        }

        try {
            Order newOrder = orderService.placeOrderFromCart(loggedInUser.getId(), paymentMethod);
            redirectAttributes.addFlashAttribute("successMessage", "Đơn hàng của bạn đã được đặt thành công! Mã đơn hàng: " + newOrder.getId());
            return "redirect:/app/shop/orders/" + newOrder.getId();
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi đặt hàng: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/app/shop/cart";
    }

    @GetMapping("/orders")
    public String viewUserOrders(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng đăng nhập để xem đơn hàng.");
            return "redirect:/app/login";
        }
        List<Order> userOrders = orderService.getUserOrders(loggedInUser.getId());
        model.addAttribute("orders", userOrders);
        model.addAttribute("body", "/WEB-INF/views/app/shop/orders.jsp");
        return "app/layout/main";
    }

    @GetMapping("/orders/{orderId}")
    public String viewOrderDetails(@PathVariable("orderId") Integer orderId,
                                   Model model, HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng đăng nhập để xem chi tiết đơn hàng.");
            return "redirect:/app/login";
        }

        Order order = orderService.getOrderDetails(orderId);
        // Employer chỉ xem đơn hàng của chính họ. Admin có thể xem tất cả.
        if (order == null || (loggedInUser.getRole() == CommonEnums.Role.EMPLOYER && !order.getUserId().equals(loggedInUser.getId()))) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy đơn hàng hoặc bạn không có quyền truy cập.");
            // Chuyển hướng Employer về trang đơn hàng của họ, Admin về trang Admin Orders
            if (loggedInUser.getRole() == CommonEnums.Role.EMPLOYER) {
                return "redirect:/app/shop/orders";
            } else { // Admin
                return "redirect:/app/shop/employer/orders"; // Admin view all orders
            }
        }

        model.addAttribute("order", order);
        model.addAttribute("body", "/WEB-INF/views/app/shop/order_details.jsp");
        return "app/layout/main";
    }

    // --- CHỨC NĂNG: DANH SÁCH ĐƠN HÀNG CHO ADMIN/EMPLOYER (THEO DÕI ĐƠN HÀNG) ---
    @GetMapping("/employer/orders") // URL này sẽ được sử dụng bởi cả Admin và Employer
    public String viewEmployerOrders(Model model,
                                     HttpSession session,
                                     @RequestParam(value = "status", required = false) String statusFilter,
                                     RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        // Kiểm tra quyền truy cập: Chỉ cho phép ADMIN hoặc EMPLOYER
        if (loggedInUser == null || (loggedInUser.getRole() != CommonEnums.Role.ADMIN && loggedInUser.getRole() != CommonEnums.Role.EMPLOYER)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn không có quyền truy cập trang này.");
            return "redirect:/app/login";
        }

        List<Order> ordersToDisplay;

        // Phân quyền dựa trên vai trò của người dùng
        if (loggedInUser.getRole() == CommonEnums.Role.ADMIN) {
            ordersToDisplay = orderService.getAllOrders(); // Admin xem tất cả đơn hàng
        } else { // loggedInUser.getRole() == CommonEnums.Role.EMPLOYER
            // Employer chỉ xem đơn hàng của chính họ
            ordersToDisplay = orderService.getUserOrders(loggedInUser.getId());
        }
        
        List<Order> filteredOrders;
        String currentStatusFilter = (statusFilter != null && !statusFilter.isEmpty()) ? statusFilter.toUpperCase() : "ALL";

        if (!currentStatusFilter.equals("ALL")) {
            try {
                OrderStatus statusEnum = OrderStatus.valueOf(currentStatusFilter);
                filteredOrders = ordersToDisplay.stream() // Lọc từ danh sách đã lấy theo vai trò
                                          .filter(order -> order.getStatus() == statusEnum)
                                          .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                filteredOrders = ordersToDisplay; // Mặc định hiển thị danh sách ban đầu nếu filter không hợp lệ
                model.addAttribute("errorMessage", "Trạng thái đơn hàng không hợp lệ: " + statusFilter);
                currentStatusFilter = "ALL"; 
            }
        } else {
            filteredOrders = ordersToDisplay;
        }

        // Đếm số lượng cho từng trạng thái để hiển thị trên tabs (tùy chọn, cần thêm logic đếm)
        // Map<String, Long> statusCounts = ordersToDisplay.stream()
        //     .collect(Collectors.groupingBy(order -> order.getStatus().name(), Collectors.counting()));
        // model.addAttribute("statusCounts", statusCounts);
        
        model.addAttribute("orders", filteredOrders);
        model.addAttribute("statusFilter", currentStatusFilter);
        model.addAttribute("allStatuses", Arrays.asList(OrderStatus.values()));
        model.addAttribute("body", "/WEB-INF/views/app/shop/employer_orders.jsp");
        return "app/layout/main";
    }

    
}
