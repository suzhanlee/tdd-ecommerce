package com.example.ecommerce.service;

import com.example.ecommerce.domain.Food;
import com.example.ecommerce.domain.FoodRepository;
import com.example.ecommerce.dto.DecreaseFoodResponse;
import com.example.ecommerce.exception.FoodNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public DecreaseFoodResponse decreaseStock(Long foodId, int quantity) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(FoodNotFoundException::new);
        food.decreaseStock(quantity);
        Food savedFood = foodRepository.save(food);
        return DecreaseFoodResponse.from(savedFood);
    }
}
