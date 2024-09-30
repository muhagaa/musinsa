package com.musinsa.item.controller;

import com.musinsa.item.domain.Item;
import com.musinsa.item.dto.ItemRequest;
import com.musinsa.item.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    /**
     * 카테고리별 최저가격인 브랜드와 상품 가격을 조회합니다.
     * @return 조회된 상품리스트 및 총액
     */
    @GetMapping("/categories/min-price")
    public ResponseEntity<String> getItemsWithMinPriceByCategory() throws Exception {
        return ResponseEntity.ok(itemService.getItemsWithMinPriceByCategory());
    }

    /**
     * 단일 브랜드 카테고리 최저가를 조회합니다.
     * @return 조회돈 상품리스트 및 브랜드, 총액
     */
    @GetMapping("/brands/min-price")
    public ResponseEntity<String> getItemsWithMinPriceByBrand() throws Exception {
        return ResponseEntity.ok(itemService.getItemsWithMinPriceByBrand());
    }

    /**
     * 카테고리 별 최저/최고 가격 브랜드를 조회합니다.
     * @param category 카테고리
     * @return 조회된 카테고리 및 최저/최고 상품 리스트
     */
    @GetMapping("/categories/{category}/min-max-price")
    public ResponseEntity<String> getItemWithMinMaxPricesByCategory(@PathVariable("category") String category) throws Exception {
        return ResponseEntity.ok(itemService.getItemWithMinMaxPricesByCategory(category));
    }

    /**
     * 상품을 생성합니다.
     * @param itemRequest 생성할 상품 정보
     * @return 생성된 상품
     */
    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody @Valid ItemRequest itemRequest) throws Exception {
        Item createdItemList = itemService.createItem(itemRequest);
        return new ResponseEntity<>(createdItemList, HttpStatus.CREATED);
    }

    /**
     * 상품을 수정합니다.
     * @param itemId 수정할 상품 ID
     * @param itemRequest 수정할 상품 정보
     * @return 수정된 상품
     */
    @PutMapping("/{itemId}")
    public ResponseEntity<Item> updateItem(@PathVariable Long itemId, @RequestBody @Valid ItemRequest itemRequest) throws Exception {
        Item updatedItem = itemService.updateItem(itemId, itemRequest);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    /**
     * 상품을 삭제합니다.
     * @param itemId 삭제할 상품 정보
     * @return 성공/실패 여부
     */

    /**
     * 상품을 삭제합니다.
     * @param itemId 삭
     * @return 삭제된 상품
     */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Item> deleteItem(@PathVariable Long itemId) throws Exception {
        Item deleteItem = itemService.deleteItem(itemId);
        return new ResponseEntity<>(deleteItem, HttpStatus.OK);
    }

}
