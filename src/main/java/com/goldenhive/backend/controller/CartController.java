package com.goldenhive.backend.controller;

import com.goldenhive.backend.dto.AddToCartRequest;
import com.goldenhive.backend.dto.CartDTO;
import com.goldenhive.backend.dto.UpdateCartRequest;
import com.goldenhive.backend.iservice.IAuthService;
import com.goldenhive.backend.iservice.ICartService;
import com.goldenhive.backend.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/cart")
@Tag(name = "CART APIs (4)", description = "Cart endpoints")
public class CartController {
    private final ICartService cartService;
    private final IAuthService authService;

    @PostMapping("/add")
    @Operation(summary = "[CART] Add to cart")
    public ApiResponse<CartDTO> add(@Valid @RequestBody AddToCartRequest request, Authentication authentication) {
        request.setUserId(resolveUserId(authentication));
        return ApiResponse.<CartDTO>builder().success(true).message("Added to cart").data(cartService.addToCart(request)).build();
    }

    @GetMapping("/get")
    @Operation(summary = "[CART] Get cart")
    public ApiResponse<List<CartDTO>> get(Authentication authentication) {
        return ApiResponse.<List<CartDTO>>builder().success(true).message("Cart fetched").data(cartService.getUserCarts(resolveUserId(authentication))).build();
    }

    @PutMapping("/update")
    @Operation(summary = "[CART] Update cart")
    public ApiResponse<CartDTO> update(@Valid @RequestBody UpdateCartRequest request, Authentication authentication) {
        return ApiResponse.<CartDTO>builder().success(true).message("Cart updated").data(cartService.updateCart(request.getCartId(), resolveUserId(authentication), request.getSelectedActivityIds())).build();
    }

    @DeleteMapping("/remove")
    @Operation(summary = "[CART] Remove cart item")
    public ApiResponse<Void> remove(@RequestParam String cartId, Authentication authentication) {
        cartService.removeFromCart(cartId, resolveUserId(authentication));
        return ApiResponse.<Void>builder().success(true).message("Cart item removed").data(null).build();
    }

    private String resolveUserId(Authentication authentication) {
        return authService.loadUserByEmail(authentication.getName()).getUserId();
    }
}
