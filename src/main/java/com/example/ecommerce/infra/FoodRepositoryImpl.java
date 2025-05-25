package com.example.ecommerce.infra;

import com.example.ecommerce.domain.Food;
import com.example.ecommerce.domain.FoodRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class FoodRepositoryImpl implements FoodRepository {

    private final JpaFoodRepository jpaFoodRepository;

    public FoodRepositoryImpl(JpaFoodRepository jpaFoodRepository) {
        this.jpaFoodRepository = jpaFoodRepository;
    }

    @Override
    public Optional<Food> findById(Long foodId) {
        return jpaFoodRepository.findById(foodId);
    }

    @Override
    public Food save(Food food) {
        return jpaFoodRepository.save(food);
    }
}
