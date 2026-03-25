package com.goldenhive.backend.util;

public class PriceUtil {
    
    /**
     * Calculate total price: basePrice + activitiesPrice
     */
    public static double calculateTotal(double basePrice, double activitiesPrice) {
        return Math.round((basePrice + activitiesPrice) * 100.0) / 100.0;
    }
    
    /**
     * Calculate discount percentage
     */
    public static double calculateDiscount(double originalPrice, double discountedPrice) {
        if (originalPrice <= 0) {
            return 0;
        }
        return Math.round(((originalPrice - discountedPrice) / originalPrice * 100) * 100.0) / 100.0;
    }
    
    /**
     * Apply discount percentage to price
     */
    public static double applyDiscount(double price, double discountPercent) {
        double discount = (price * discountPercent) / 100;
        return Math.round((price - discount) * 100.0) / 100.0;
    }
}
