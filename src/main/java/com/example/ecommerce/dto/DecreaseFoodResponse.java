package com.example.ecommerce.dto;

import com.example.ecommerce.domain.Food;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DecreaseFoodResponse {
    private Long foodId;
    private long quantity;

    public static DecreaseFoodResponse from(Food food) {
        DecreaseFoodResponse response = new DecreaseFoodResponse();
        response.foodId = food.getId();
        response.quantity = food.getStockQuantity();
        return response;
    }
}
