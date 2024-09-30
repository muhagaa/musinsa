package com.musinsa.item.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.item.domain.Item;
import com.musinsa.item.dto.ItemRequest;
import com.musinsa.item.exception.ErrorEnum;
import com.musinsa.item.exception.ItemCustomException;
import com.musinsa.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final int CATEGORY_SIZE = 8;

    /**
     * 카테고리별 최저가격인 브랜드와 상품 가격을 조회합니다.
     * @return 조회된 상품리스트 및 총액
     */
    public String getItemsWithMinPriceByCategory() throws Exception {
        List<Item> itemList = itemRepository.findAll()
                                            .stream()
                                            .collect(Collectors.groupingBy(Item::getCategory, Collectors.minBy(Comparator.comparing(Item::getPrice))))
                                            .values().stream()
                                            .flatMap(Optional::stream)
                                            .toList();

        if (itemList.size() != CATEGORY_SIZE) {
            throw new ItemCustomException(ErrorEnum.NOT_VALID_CATEGORY, "모든 브랜드의 카테고리 정보가 올바르지 않습니다.");
        }

        return objectMapper.writeValueAsString(
            Map.of(
                "totalPrice", itemList.stream().mapToLong(Item::getPrice).sum(),
                "category", itemList)
        );
    }

    /**
     * 단일 브랜드 카테고리 최저가를 조회합니다.
     * @return 조회돈 상품리스트 및 브랜드, 총액
     */
    public String getItemsWithMinPriceByBrand() throws Exception {
        Map<String, List<Item>> brandMap = itemRepository.findAll()
                                                    .stream()
                                                    .collect(Collectors.groupingBy(Item::getBrand));

        String minPriceBrand = brandMap.entrySet().stream()
                                       .filter(e -> e.getValue().size() == CATEGORY_SIZE)
                                       .min((e1,e2) -> {
                                           long total1 = e1.getValue().stream().mapToLong(Item::getPrice).sum();
                                           long total2 = e2.getValue().stream().mapToLong(Item::getPrice).sum();
                                           return Long.compare(total1, total2);
                                       })
                                       .map(Map.Entry::getKey)
                                       .orElseThrow(() -> new ItemCustomException(ErrorEnum.NOT_VALID_CATEGORY, "모든 브랜드의 카테고리 정보가 올바르지 않습니다."));

        List<Item> minPriceBrandItemList = brandMap.get(minPriceBrand);

        return objectMapper.writeValueAsString(
            Map.of(
                "minPrice", Map.of(
                    "brand", minPriceBrand,
                    "totalPrice", minPriceBrandItemList.stream().mapToLong(Item::getPrice).sum(),
                    "category", minPriceBrandItemList))
        );
    }

    /**
     * 카테고리 별 최저/최고 가격 브랜드를 조회합니다.
     * @param category 카테고리
     * @return 조회된 카테고리 및 최저/최고 상품 리스트
     */
    public String getItemWithMinMaxPricesByCategory(String category) throws Exception {
        Optional<List<Item>> optionalItemList = itemRepository.findByCategory(category);

        if (optionalItemList.isEmpty() || optionalItemList.get().isEmpty()) {
            throw new ItemCustomException(ErrorEnum.NO_CONTENT_CATEGORY, category+" 카테고리에 상품이 없습니다.");
        }


        return objectMapper.writeValueAsString(
            Map.of(
                "category", category,
                "minPrice", optionalItemList.get().stream().min(Comparator.comparing(Item::getPrice)).map(Collections::singletonList).orElseGet(Collections::emptyList),
                "maxPrice", optionalItemList.get().stream().max(Comparator.comparing(Item::getPrice)).map(Collections::singletonList).orElseGet(Collections::emptyList))
        );
    }

    /**
     * 상품을 생성합니다.
     * @param itemRequest 생성할 상품 정보
     * @return 생성된 상품
     */
    public Item createItem(ItemRequest itemRequest) throws Exception {
        Optional<Item> optionalItem = itemRepository.findByBrandAndCategory(itemRequest.getBrand(), itemRequest.getCategory());
        if (optionalItem.isPresent()) {
            throw new ItemCustomException(ErrorEnum.ALREADY_EXIST_ITEM, "해당 브랜드/카테고리에 상품이 이미 존재합니다.");
        }
        return itemRepository.save(itemRequest.toEntity());
    }

    /**
     * 상품을 수정합니다.
     * @param itemId 수정할 상품 ID
     * @param itemRequest 수정할 상품 정보
     * @return 수정된 상품
     */
    public Item updateItem(Long itemId, ItemRequest itemRequest) throws Exception {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isEmpty()) {
            throw new ItemCustomException(ErrorEnum.NO_EXIST_ITEM, "해당 상품이 존재하지 않습니다.");
        }

        Optional<Item> existingItem = itemRepository.findByBrandAndCategory(itemRequest.getBrand(), itemRequest.getCategory());
        if (existingItem.isPresent() && itemId != existingItem.get().getId()) {
            throw new ItemCustomException(ErrorEnum.ALREADY_EXIST_ITEM, "해당 브랜드/카테고리에 상품이 이미 존재합니다.");
        }

        Item originalItem = optionalItem.get();
        originalItem.updateItem(itemRequest);
        return itemRepository.save(originalItem);
    }

    /**
     * 상품을 삭제합니다.
     * @param itemId 삭제할 상품 ID
     */
    public Item deleteItem(Long itemId) throws Exception {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isEmpty()) {
            throw new ItemCustomException(ErrorEnum.NO_EXIST_ITEM, "해당 상품이 존재하지 않습니다.");
        }

        Item originalItem = optionalItem.get();
        itemRepository.delete(originalItem);
        return originalItem;
    }
}
