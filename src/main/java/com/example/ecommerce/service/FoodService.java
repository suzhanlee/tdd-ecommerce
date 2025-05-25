package com.example.ecommerce.service;

import com.example.ecommerce.domain.Food;
import com.example.ecommerce.domain.FoodRepository;
import com.example.ecommerce.dto.DecreaseFoodResponse;
import org.springframework.stereotype.Service;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public DecreaseFoodResponse decreaseStock(Long foodId, int quantity) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        food.decreaseStock(quantity);
        Food savedFood = foodRepository.save(food);
        return DecreaseFoodResponse.from(savedFood);
    }
}
