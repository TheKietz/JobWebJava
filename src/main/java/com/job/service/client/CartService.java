package com.job.service; // Đảm bảo đúng package service của bạn

import com.job.model.Cart;
import com.job.model.CartItem;
import com.job.model.ServicePackage;
import com.job.model.User;
import com.job.repository.CartItemRepository;
import com.job.repository.CartRepository;
import com.job.repository.ServicePackageRepository; // Bạn cần tạo ServicePackageRepository này
import com.job.repository.UserRepository; // Bạn cần tạo UserRepository này
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
// import java.util.Optional; // Không cần import này nữa

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository; // Cần để lấy thông tin User
    
    @Autowired
    private ServicePackageRepository servicePackageRepository; // Cần để lấy thông tin ServicePackage

    // Lấy hoặc tạo giỏ hàng cho người dùng
    @Transactional
    public Cart getOrCreateCartForUser(Integer userId) {
        // Tìm giỏ hàng hiện có cho người dùng
        Cart existingCart = cartRepository.findByUserId(userId); // Không trả về Optional nữa
        if (existingCart != null) { // Kiểm tra null trực tiếp
            return existingCart;
        } else {
            // Nếu không có, tạo giỏ hàng mới
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            newCart.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            newCart.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            Integer newCartId = cartRepository.add(newCart);
            if (newCartId == null) {
                throw new RuntimeException("Failed to create new cart for user: " + userId);
            }
            newCart.setId(newCartId);
            return newCart;
        }
    }

    // Thêm gói vào giỏ hàng hoặc cập nhật số lượng nếu đã tồn tại
    @Transactional
    public CartItem addItemToCart(Integer userId, Integer packageId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }

        Cart userCart = getOrCreateCartForUser(userId);
        if (userCart == null) { // Kiểm tra null cho giỏ hàng
            throw new RuntimeException("Could not retrieve or create cart for user: " + userId);
        }
        
        // Lấy thông tin gói dịch vụ để có giá
        ServicePackage servicePackage = servicePackageRepository.findById(packageId); // Không trả về Optional nữa
        if (servicePackage == null) { // Kiểm tra null trực tiếp
            throw new IllegalArgumentException("Service Package not found with ID: " + packageId);
        }

        CartItem existingItem = cartItemRepository.findByCartIdAndPackageId(userCart.getId(), packageId); // Không trả về Optional nữa

        if (existingItem != null) { // Kiểm tra null trực tiếp
            existingItem.setQuantity(existingItem.getQuantity() + quantity); // Cập nhật số lượng
            existingItem.setPriceAtAddition(servicePackage.getPrice()); // Cập nhật lại giá tại thời điểm thêm
            cartItemRepository.update(existingItem);
            return existingItem;
        } else {
            CartItem newItem = new CartItem();
            newItem.setCartId(userCart.getId());
            newItem.setPackageId(packageId);
            newItem.setQuantity(quantity);
            newItem.setPriceAtAddition(servicePackage.getPrice()); // Lưu giá tại thời điểm thêm vào giỏ hàng
            newItem.setAddedAt(new Timestamp(System.currentTimeMillis()));
            Integer newItemId = cartItemRepository.add(newItem);
            if (newItemId == null) {
                throw new RuntimeException("Failed to add item to cart.");
            }
            newItem.setId(newItemId);
            return newItem;
        }
    }

    // Cập nhật số lượng của một mặt hàng trong giỏ hàng
    @Transactional
    public void updateCartItemQuantity(Integer cartItemId, int quantity) {
        if (quantity <= 0) {
            cartItemRepository.deleteById(cartItemId);
            return;
        }
        cartItemRepository.updateQuantity(cartItemId, quantity);
    }

    // Xóa một mặt hàng khỏi giỏ hàng
    @Transactional
    public boolean removeCartItem(Integer cartItemId) {
        return cartItemRepository.deleteById(cartItemId);
    }

    // Lấy chi tiết giỏ hàng của người dùng (bao gồm thông tin gói đầy đủ)
    public Cart getCartDetails(Integer userId) {
        Cart cart = getOrCreateCartForUser(userId);
        if (cart == null) { // Kiểm tra null cho giỏ hàng
            return null; // Hoặc ném ngoại lệ tùy logic nghiệp vụ của bạn
        }
        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        // Tải thông tin gói dịch vụ đầy đủ cho mỗi CartItem
        for (CartItem item : items) {
            ServicePackage pkg = servicePackageRepository.findById(item.getPackageId()); // Không trả về Optional
            if (pkg != null) { // Kiểm tra null trực tiếp
                item.setServicePackage(pkg);
            }
        }

        cart.setCartItems(items);
        User user = userRepository.findById(userId); // Không trả về Optional
        if (user != null) { // Kiểm tra null trực tiếp
            cart.setUser(user);
        }
        
        return cart;
    }

    // Xóa tất cả các mặt hàng khỏi giỏ hàng của người dùng
    @Transactional
    public boolean clearCart(Integer userId) {
        Cart cart = getOrCreateCartForUser(userId);
        if (cart == null) { // Kiểm tra null cho giỏ hàng
            return false;
        }
        return cartItemRepository.deleteByCartId(cart.getId());
    }
}