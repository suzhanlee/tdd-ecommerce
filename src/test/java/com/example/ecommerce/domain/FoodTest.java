package com.example.ecommerce.domain;

import com.example.ecommerce.exception.FoodNameException;
import com.example.ecommerce.exception.FoodStockException;
import com.example.ecommerce.exception.NotEnoughStockException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

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

    @ParameterizedTest
    @DisplayName("음식의 이름이 비어있으면 예외가 발생한다.")
    @NullAndEmptySource
    void validate_food_name(String name) {
        // when // then
        assertThatThrownBy(() -> new Food(name, 1))
                .isInstanceOf(FoodNameException.class)
                .hasMessage("음식의 이름은 비어있을 수 없습니다.");
    }

    @Test
    @DisplayName("음식의 재고가 음수면 예외가 발생한다.")
    void validate_food_stock() {
        // when // then
        assertThatThrownBy(() -> new Food("라면", -1))
                .isInstanceOf(FoodStockException.class)
                .hasMessage("음식이 재고가 음수입니다.");
    }
}