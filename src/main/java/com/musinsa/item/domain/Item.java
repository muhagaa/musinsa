package com.musinsa.item.domain;

import com.musinsa.item.dto.ItemRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Item {

    public Item(String brand, String category, long price) {
        this.brand = brand;
        this.category = category;
        this.price = price;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private long id;
    @Setter
    private String brand;
    private String category;
    private long price;

    public void updateItem(ItemRequest itemRequest) {
        this.brand = itemRequest.getBrand();
        this.category = itemRequest.getCategory();
        this.price = itemRequest.getPrice();
    }
}
