package com.musinsa.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.item.domain.Item;
import com.musinsa.item.dto.ItemRequest;
import com.musinsa.item.dto.ItemResponse;
import com.musinsa.item.service.ItemService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/categories/min-price")
    public ResponseEntity<String> getItemsWithMinPriceByCategory() throws Exception {
        return ResponseEntity.ok(itemService.getItemsWithMinPriceByCategory());
    }

    @GetMapping("/brands/min-price")
    public ResponseEntity<String> getItemsWithMinPriceByBrand() throws Exception {
        return ResponseEntity.ok(itemService.getItemsWithMinPriceByBrand());
    }

    @GetMapping("/categories/{category}/min-max-prices")
    public ResponseEntity<String> getItemWithMinMaxPricesByCategory(@PathVariable("category") String category) throws Exception {
        return ResponseEntity.ok(itemService.getItemWithMinMaxPricesByCategory(category));
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody @Valid ItemRequest itemRequest) throws Exception {
        Item createdItemList = itemService.createItem(itemRequest);
        return new ResponseEntity<>(createdItemList, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Item> updateItem(@RequestBody @Valid ItemRequest itemRequest) throws Exception {
        Item updatedItem = itemService.updateItem(itemRequest);
        return new ResponseEntity<>(updatedItem, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Long> deleteItem(@RequestBody @Valid ItemRequest itemRequest) {
        itemService.deleteItem(itemRequest);
        return null;
    }

}
