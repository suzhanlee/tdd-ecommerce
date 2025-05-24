package com.example.ecommerce.service;

import com.example.ecommerce.domain.Food;
import com.example.ecommerce.domain.FoodRepository;
import com.example.ecommerce.dto.DecreaseFoodResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoodServiceTest {

    @InjectMocks
    private FoodService foodService;      // final 제거

    @Mock
    private FoodRepository foodRepository;

    @Captor
    private ArgumentCaptor<Food> foodCaptor;

    @Test
    @DisplayName("상품의 재고를 차감할 수 있다.")
    void decrease_stock() {
        // given
        Long foodId = 1L;
        int quantity = 1;

        Food givenFood = new Food(foodId, 10);
        when(foodRepository.findById(foodId)).thenReturn(Optional.of(givenFood));
        when(foodRepository.save(any(Food.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        DecreaseFoodResponse result = foodService.decreaseStock(foodId, quantity);

        // then
        verify(foodRepository).save(foodCaptor.capture());
        assertThat(foodCaptor.getValue().getStockQuantity()).isEqualTo(9);

        assertThat(result.getFoodId()).isEqualTo(foodId);
        assertThat(result.getQuantity()).isEqualTo(9);
    }
}
