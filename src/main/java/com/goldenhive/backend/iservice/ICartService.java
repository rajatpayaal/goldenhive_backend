package com.goldenhive.backend.iservice;

import com.goldenhive.backend.dto.CartDTO;
import com.goldenhive.backend.dto.AddToCartRequest;
import java.util.List;
import java.util.Optional;

public interface ICartService {
    
    /**
     * Add package with activities to cart
     */
    CartDTO addToCart(AddToCartRequest request);
    
    /**
     * Get cart by ID
     */
    Optional<CartDTO> getCartById(String cartId);
    
    /**
     * Get user's cart for a package
     */
    Optional<CartDTO> getUserCartForPackage(String userId, String packageId);
    
    /**
     * Get all carts for a user
     */
    List<CartDTO> getUserCarts(String userId);
    
    /**
     * Update cart with new activities
     */
    CartDTO updateCart(String cartId, List<String> selectedActivityIds);
    
    /**
     * Calculate and update total price
     * Applies: basePrice + sum(discountedActivityPrices)
     */
    double calculateTotalPrice(String packageId, List<String> selectedActivityIds);
    
    /**
     * Remove item from cart
     */
    void removeFromCart(String cartId);
    
    /**
     * Clear user's cart
     */
    void clearUserCarts(String userId);
}
