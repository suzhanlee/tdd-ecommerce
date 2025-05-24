package com.example.ecommerce.infra;

import com.example.ecommerce.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFoodRepository extends JpaRepository<Food, Long> {
}
