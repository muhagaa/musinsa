package com.musinsa.item.dto;

import com.musinsa.item.annotation.ValidCategory;
import com.musinsa.item.domain.Item;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest {
    @NotBlank(message="brand는 필수값 입니다.")
    private String brand;

    @NotBlank(message="category는 필수값 입니다.")
    @ValidCategory
    private String category;

    @NotNull
    @Min(value = 1, message = "price는 0보다 커야 합니다.")
    private long price;

    public Item toEntity() {
        return Item.builder()
            .brand(brand)
            .category(category)
            .price(price)
            .build();
    }
}
