package com.example.ecommerce.domain;

import com.example.ecommerce.exception.FoodNameException;
import com.example.ecommerce.exception.FoodStockException;
import com.example.ecommerce.exception.NotEnoughStockException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int stockQuantity;

    public Food(String name, int stockQuantity) {
        validateFood(name, stockQuantity);
        this.name = name;
        this.stockQuantity = stockQuantity;
    }

    private static void validateFood(String name, int stockQuantity) {
        validateFoodName(name);
        validateStockQuantity(stockQuantity);
    }

    private static void validateFoodName(String name) {
        if (name == null || name.isBlank()) {
            throw new FoodNameException();
        }
    }

    private static void validateStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new FoodStockException();
        }
    }

    // test 용
    public Food(Long id, int quantity) {
        this.id = id;
        this.name = "test-food-name";
        this.stockQuantity = quantity;
    }

    public void decreaseStock(int quantity) {
        if (this.stockQuantity < quantity) {
            throw new NotEnoughStockException("재고 부족");
        }
        this.stockQuantity -= quantity;
    }
}
