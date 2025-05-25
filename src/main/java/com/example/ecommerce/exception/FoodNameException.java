package com.example.ecommerce.exception;

public class FoodNameException extends RuntimeException {
    private static final String FOOD_NAME_EXCEPTION_MESSAGE = "음식의 이름은 비어있을 수 없습니다.";

    public FoodNameException() {
        super(FOOD_NAME_EXCEPTION_MESSAGE);
    }
}
