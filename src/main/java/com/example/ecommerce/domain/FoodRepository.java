package com.example.ecommerce.domain;

import java.util.Optional;

public interface FoodRepository {
    Optional<Food> findById(Long foodId);

    Food save(Food food);
}
