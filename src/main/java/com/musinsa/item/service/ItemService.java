package com.musinsa.item.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.item.domain.Item;
import com.musinsa.item.dto.ItemRequest;
import com.musinsa.item.repository.ItemRepository;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final int CATEGORY_SIZE = 8;

    public String getItemsWithMinPriceByCategory() throws JsonProcessingException {
        List<Item> itemList = itemRepository.findAll()
                                            .stream()
                                            .collect(Collectors.groupingBy(Item::getCategory, Collectors.minBy(Comparator.comparing(Item::getPrice))))
                                            .values().stream()
                                            .flatMap(Optional::stream)
                                            .toList();

        return objectMapper.writeValueAsString(
            Map.of(
                "totalPrice", itemList.stream().mapToLong(Item::getPrice).sum(),
                "category", itemList)
        );
    }

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
                                       .orElseThrow(() -> new Exception("취소 대상 상품이 없습니다."));

        List<Item> minPriceBrandItemList = brandMap.get(minPriceBrand);

        return objectMapper.writeValueAsString(
            Map.of(
                "minPrice", Map.of(
                    "brand", minPriceBrand,
                    "totalPrice", minPriceBrandItemList.stream().mapToLong(Item::getPrice).sum(),
                    "category", minPriceBrandItemList))
        );
    }

    public String getItemWithMinMaxPricesByCategory(String category) throws  Exception {
        List<Item> itemList = itemRepository.findByCategory(category).orElseThrow(() -> new Exception(""));

        return objectMapper.writeValueAsString(
            Map.of(
                "category", category,
                "minPrice", itemList.stream().min(Comparator.comparing(Item::getPrice)).map(Collections::singletonList).orElseGet(Collections::emptyList),
                "maxPrice", itemList.stream().max(Comparator.comparing(Item::getPrice)).map(Collections::singletonList).orElseGet(Collections::emptyList))
        );
    }

    public Item createItem(ItemRequest itemRequest) throws Exception {
        Optional<Item> optionalItem = itemRepository.findByBrandAndCategory(itemRequest.getBrand(), itemRequest.getCategory());
        if (optionalItem.isEmpty()) {
            return itemRepository.save(itemRequest.toEntity());
        } else {
            throw new Exception("");
        }
    }

    public Item updateItem(ItemRequest itemRequest) throws Exception {
        Optional<Item> optionalItem = itemRepository.findByBrandAndCategory(itemRequest.getBrand(), itemRequest.getCategory());
        if (optionalItem.isPresent()) {
            Item originalItem = optionalItem.get();
            originalItem.updateItem(itemRequest);
            return itemRepository.save(originalItem);
        } else {
            throw new Exception("");
        }
    }

    public boolean deleteItem(ItemRequest itemRequest) {
        Optional<Item> optionalItem = itemRepository.findByBrandAndCategory(itemRequest.getBrand(), itemRequest.getCategory());
        if (optionalItem.isPresent()) {
            itemRepository.delete(optionalItem.get());
            return true;
        } else {
            return false;
        }
    }
}
