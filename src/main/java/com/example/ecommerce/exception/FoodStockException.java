package com.example.ecommerce.exception;

public class FoodStockException extends RuntimeException {
    private static final String FOOD_STOCK_EXCEPTION = "음식이 재고가 음수입니다.";

    public FoodStockException() {
        super(FOOD_STOCK_EXCEPTION);
    }
}
