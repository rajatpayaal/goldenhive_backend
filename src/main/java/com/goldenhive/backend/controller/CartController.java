package com.goldenhive.backend.controller;

import com.goldenhive.backend.dto.AddToCartRequest;
import com.goldenhive.backend.dto.CartDTO;
import com.goldenhive.backend.dto.UpdateCartRequest;
import com.goldenhive.backend.iservice.ICartService;
import com.goldenhive.backend.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/cart")
@Tag(name = "CART APIs (4)", description = "Cart endpoints")
public class CartController {
    private final ICartService cartService;

    @PostMapping("/add")
    @Operation(summary = "[CART] Add to cart")
    public ApiResponse<CartDTO> add(@Valid @RequestBody AddToCartRequest request) {
        return ApiResponse.<CartDTO>builder().success(true).message("Added to cart").data(cartService.addToCart(request)).build();
    }

    @GetMapping("/get")
    @Operation(summary = "[CART] Get cart")
    public ApiResponse<List<CartDTO>> get(@RequestParam String userId) {
        return ApiResponse.<List<CartDTO>>builder().success(true).message("Cart fetched").data(cartService.getUserCarts(userId)).build();
    }

    @PutMapping("/update")
    @Operation(summary = "[CART] Update cart")
    public ApiResponse<CartDTO> update(@Valid @RequestBody UpdateCartRequest request) {
        return ApiResponse.<CartDTO>builder().success(true).message("Cart updated").data(cartService.updateCart(request.getCartId(), request.getSelectedActivityIds())).build();
    }

    @DeleteMapping("/remove")
    @Operation(summary = "[CART] Remove cart item")
    public ApiResponse<Void> remove(@RequestParam String cartId) {
        cartService.removeFromCart(cartId);
        return ApiResponse.<Void>builder().success(true).message("Cart item removed").data(null).build();
    }
}


