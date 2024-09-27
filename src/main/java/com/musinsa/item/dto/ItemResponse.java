package com.musinsa.item.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.musinsa.item.domain.Item;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemResponse {
    private List<Item> items;
    private long totalPrice;
    private String brand;
    private String category;
}
