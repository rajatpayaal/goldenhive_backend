package com.goldenhive.backend.service.impl;

import com.goldenhive.backend.dto.CartDTO;
import com.goldenhive.backend.dto.AddToCartRequest;
import com.goldenhive.backend.dto.SelectedActivityDTO;
import com.goldenhive.backend.entity.Cart;
import com.goldenhive.backend.entity.SelectedActivity;
import com.goldenhive.backend.exception.UnauthorizedException;
import com.goldenhive.backend.iservice.ICartService;
import com.goldenhive.backend.iservice.IPackageActivityService;
import com.goldenhive.backend.repository.CartRepository;
import com.goldenhive.backend.repository.PackageRepository;
import com.goldenhive.backend.util.PriceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements ICartService {
    
    private final CartRepository cartRepository;
    private final PackageRepository packageRepository;
    private final IPackageActivityService packageActivityService;
    
    @Override
    public CartDTO addToCart(AddToCartRequest request) {
        log.info("Adding to cart - User: {}, Package: {}", request.getUserId(), request.getPackageId());
        
        packageRepository.findById(request.getPackageId())
                .orElseThrow(() -> new RuntimeException("Package not found with ID: " + request.getPackageId()));
        
        Cart cart = cartRepository.findByUserIdAndPackageId(request.getUserId(), request.getPackageId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(request.getUserId());
                    newCart.setPackageId(request.getPackageId());
                    newCart.setCreatedAt(LocalDateTime.now());
                    return newCart;
                });
        
        List<SelectedActivity> selectedActivities = mapSelectedActivities(
                request.getPackageId(),
                request.getSelectedActivityIds()
        );
        cart.setSelectedActivities(selectedActivities);
        
        double totalPrice = calculateTotalPrice(request.getPackageId(), request.getSelectedActivityIds());
        cart.setTotalPrice(totalPrice);
        
        Cart savedCart = cartRepository.save(cart);
        log.info("Cart saved with ID: {}", savedCart.getCartId());
        
        return mapToDTO(savedCart);
    }
    
    @Override
    public Optional<CartDTO> getCartById(String cartId) {
        log.info("Fetching cart with ID: {}", cartId);
        return cartRepository.findById(cartId).map(this::mapToDTO);
    }
    
    @Override
    public Optional<CartDTO> getUserCartForPackage(String userId, String packageId) {
        log.info("Fetching cart for User: {}, Package: {}", userId, packageId);
        return cartRepository.findByUserIdAndPackageId(userId, packageId).map(this::mapToDTO);
    }
    
    @Override
    public List<CartDTO> getUserCarts(String userId) {
        log.info("Fetching all carts for User: {}", userId);
        return cartRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public CartDTO updateCart(String cartId, String userId, List<String> selectedActivityIds) {
        log.info("Updating cart with ID: {}", cartId);
        
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));
        ensureCartOwner(cart, userId);
        
        List<SelectedActivity> selectedActivities = mapSelectedActivities(
                cart.getPackageId(),
                selectedActivityIds
        );
        cart.setSelectedActivities(selectedActivities);
        
        double totalPrice = calculateTotalPrice(cart.getPackageId(), selectedActivityIds);
        cart.setTotalPrice(totalPrice);
        
        Cart updatedCart = cartRepository.save(cart);
        log.info("Cart updated with ID: {}", cartId);
        
        return mapToDTO(updatedCart);
    }
    
    @Override
    public double calculateTotalPrice(String packageId, List<String> selectedActivityIds) {
        log.info("Calculating total price - Package: {}, Activities: {}", packageId, selectedActivityIds.size());
        
        double basePrice = packageRepository.findById(packageId)
                .map(pkg -> pkg.getBasePrice())
                .orElseThrow(() -> new RuntimeException("Package not found"));
        
        double activitiesPrice = 0.0;
        
        if (selectedActivityIds != null && !selectedActivityIds.isEmpty()) {
            for (String activityId : selectedActivityIds) {
                Optional<Double> discountedPrice = packageActivityService.getDiscountedPrice(packageId, activityId);
                activitiesPrice += discountedPrice.orElse(0.0);
            }
        }
        
        double totalPrice = PriceUtil.calculateTotal(basePrice, activitiesPrice);
        log.info("Total price calculated: {}", totalPrice);
        
        return totalPrice;
    }
    
    @Override
    public void removeFromCart(String cartId, String userId) {
        log.info("Removing cart with ID: {}", cartId);
        
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));
        ensureCartOwner(cart, userId);
        
        cartRepository.deleteById(cartId);
        log.info("Cart removed");
    }
    
    @Override
    public void clearUserCarts(String userId) {
        log.info("Clearing all carts for User: {}", userId);
        cartRepository.deleteByUserId(userId);
        log.info("All carts cleared for user");
    }
    
    private void ensureCartOwner(Cart cart, String userId) {
        if (!cart.getUserId().equals(userId)) {
            throw new UnauthorizedException("Cart does not belong to this user");
        }
    }

    private List<SelectedActivity> mapSelectedActivities(String packageId, List<String> activityIds) {
        if (activityIds == null || activityIds.isEmpty()) {
            return new ArrayList<>();
        }
        
        return activityIds.stream()
                .map(activityId -> {
                    Optional<Double> discountedPrice = packageActivityService.getDiscountedPrice(packageId, activityId);
                    
                    SelectedActivity sa = new SelectedActivity();
                    sa.setActivityId(activityId);
                    sa.setDiscountedPrice(discountedPrice.orElse(0.0));
                    return sa;
                })
                .collect(Collectors.toList());
    }
    
    private CartDTO mapToDTO(Cart cart) {
        List<SelectedActivityDTO> selectedActivityDTOs = cart.getSelectedActivities() != null ?
                cart.getSelectedActivities().stream()
                        .map(sa -> new SelectedActivityDTO(
                                sa.getActivityId(),
                                sa.getActivityName(),
                                sa.getPrice(),
                                sa.getDiscountedPrice()
                        ))
                        .collect(Collectors.toList())
                : new ArrayList<>();
        
        return new CartDTO(
                cart.getCartId(),
                cart.getUserId(),
                cart.getPackageId(),
                selectedActivityDTOs,
                cart.getTotalPrice(),
                cart.getCreatedAt()
        );
    }
}
