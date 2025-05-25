package com.example.ecommerce.domain;

import com.example.ecommerce.exception.NotEnoughStockException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FoodTest {

    @Test
    void 재고가_충분하면_재고를_차감한다() {
        Food food = new Food("치킨", 10);
        food.decreaseStock(3);
        assertThat(food.getStockQuantity()).isEqualTo(7);
    }

    @Test
    void 재고가_부족하면_예외가_발생한다() {
        Food food = new Food("치킨", 2);
        assertThatThrownBy(() -> food.decreaseStock(5))
                .isInstanceOf(NotEnoughStockException.class);
    }
}