package com.example.ecommerce.exception;

public class FoodNotFoundException extends RuntimeException{
    private static final String FOOD_NOT_FOUND_EXCEPTION_MESSAGE = "상품을 찾을 수 없습니다.";

    public FoodNotFoundException() {
        super(FOOD_NOT_FOUND_EXCEPTION_MESSAGE);
    }
}
