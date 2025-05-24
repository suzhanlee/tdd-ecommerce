package com.example.ecommerce.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(String message) {
        super(message);
    }
}
