package com.musinsa.item.service;

import com.musinsa.item.domain.Item;
import com.musinsa.item.dto.ItemRequest;
import com.musinsa.item.exception.ErrorEnum;
import com.musinsa.item.exception.ItemCustomException;
import com.musinsa.item.repository.ItemRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository itemRepository;

    List<Item> itemList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        itemList.add(new Item("A", "상의", 11200));
        itemList.add(new Item("A", "아우터", 5500));
        itemList.add(new Item("A", "바지", 4200));
        itemList.add(new Item("A", "스니커즈", 9000));
        itemList.add(new Item("A", "가방", 2000));
        itemList.add(new Item("A", "모자", 1700));
        itemList.add(new Item("A", "양말", 1800));
        itemList.add(new Item("A", "액세서리", 2300));

        itemList.add(new Item("B", "상의", 10500));
        itemList.add(new Item("B", "아우터", 5900));
        itemList.add(new Item("B", "바지", 3800));
        itemList.add(new Item("B", "스니커즈", 9100));
        itemList.add(new Item("B", "가방", 2100));
        itemList.add(new Item("B", "모자", 2000));
        itemList.add(new Item("B", "양말", 2000));
        itemList.add(new Item("B", "액세서리", 2200));

        itemList.add(new Item("C", "상의", 10000));
        itemList.add(new Item("C", "아우터", 6200));
        itemList.add(new Item("C", "바지", 3300));
        itemList.add(new Item("C", "스니커즈", 9200));
        itemList.add(new Item("C", "가방", 2200));
        itemList.add(new Item("C", "모자", 1900));
        itemList.add(new Item("C", "양말", 2200));
        itemList.add(new Item("C", "액세서리", 2100));

        itemList.add(new Item("D", "상의", 10100));
        itemList.add(new Item("D", "아우터", 5100));
        itemList.add(new Item("D", "바지", 3000));
        itemList.add(new Item("D", "스니커즈", 9500));
        itemList.add(new Item("D", "가방", 2500));
        itemList.add(new Item("D", "모자", 1500));
        itemList.add(new Item("D", "양말", 2400));
        itemList.add(new Item("D", "액세서리", 2000));

        itemList.add(new Item("E", "상의", 10700));
        itemList.add(new Item("E", "아우터", 5000));
        itemList.add(new Item("E", "바지", 3800));
        itemList.add(new Item("E", "스니커즈", 9900));
        itemList.add(new Item("E", "가방", 2300));
        itemList.add(new Item("E", "모자", 1800));
        itemList.add(new Item("E", "양말", 2100));
        itemList.add(new Item("E", "액세서리", 2100));

        itemList.add(new Item("F", "상의", 11200));
        itemList.add(new Item("F", "아우터", 7200));
        itemList.add(new Item("F", "바지", 4000));
        itemList.add(new Item("F", "스니커즈", 9300));
        itemList.add(new Item("F", "가방", 2100));
        itemList.add(new Item("F", "모자", 1600));
        itemList.add(new Item("F", "양말", 2300));
        itemList.add(new Item("F", "액세서리", 1900));

        itemList.add(new Item("G", "상의", 10500));
        itemList.add(new Item("G", "아우터", 5800));
        itemList.add(new Item("G", "바지", 3900));
        itemList.add(new Item("G", "스니커즈", 9000));
        itemList.add(new Item("G", "가방", 2200));
        itemList.add(new Item("G", "모자", 1700));
        itemList.add(new Item("G", "양말", 2100));
        itemList.add(new Item("G", "액세서리", 2000));

        itemList.add(new Item("H", "상의", 10800));
        itemList.add(new Item("H", "아우터", 6300));
        itemList.add(new Item("H", "바지", 3100));
        itemList.add(new Item("H", "스니커즈", 9700));
        itemList.add(new Item("H", "가방", 2100));
        itemList.add(new Item("H", "모자", 1600));
        itemList.add(new Item("H", "양말", 2000));
        itemList.add(new Item("H", "액세서리", 2000));

        itemList.add(new Item("I", "상의", 11400));
        itemList.add(new Item("I", "아우터", 6700));
        itemList.add(new Item("I", "바지", 3200));
        itemList.add(new Item("I", "스니커즈", 9500));
        itemList.add(new Item("I", "가방", 2400));
        itemList.add(new Item("I", "모자", 1700));
        itemList.add(new Item("I", "양말", 1700));
        itemList.add(new Item("I", "액세서리", 2400));
    }

    @Test
    void getItemsWithMinPriceByCategory_shouldReturnValidResponse() throws Exception {
        // given
        when(itemRepository.findAll()).thenReturn(itemList);

        // when
        String result = itemService.getItemsWithMinPriceByCategory();

        // then
        assertNotNull(result);
        JSONObject jsonObject = new JSONObject(result);
        assertEquals(34100, jsonObject.getLong("totalPrice"));
        assertEquals(8, jsonObject.getJSONArray("category").length());

        // Verify that findAll was called once
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void getItemsWithMinPriceByCategory_shouldThrowExceptionWhenCategorySizeNotValid() {
        // given
        Item item = new Item("A", "상의", 10000);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        // when & then
        ItemCustomException exception = assertThrows(ItemCustomException.class, () ->
                itemService.getItemsWithMinPriceByCategory()
        );

        assertEquals(ErrorEnum.NOT_VALID_CATEGORY, exception.getErrorEnum());
    }

    @Test
    void getItemsWithMinPriceByBrand_shouldReturnValidResponse() throws Exception {
        // given
        when(itemRepository.findAll()).thenReturn(itemList);

        // when
        String result = itemService.getItemsWithMinPriceByBrand();

        // then
        assertNotNull(result);
        JSONObject jsonObject = (new JSONObject(result)).getJSONObject("minPrice");
        assertEquals(36100, jsonObject.getLong("totalPrice"));
        assertEquals("D", jsonObject.getString("brand"));
        assertEquals(8, jsonObject.getJSONArray("category").length());

        // Verify that findAll was called once
        verify(itemRepository, times(1)).findAll();
    }


    @Test
    void getItemsWithMinPriceByBrand_shouldThrowExceptionWhenCategorySizeNotValid() {
        // given
        Item item = new Item("A", "상의", 10000);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        // when & then
        ItemCustomException exception = assertThrows(ItemCustomException.class, () ->
                itemService.getItemsWithMinPriceByBrand()
        );

        assertEquals(ErrorEnum.NOT_VALID_CATEGORY, exception.getErrorEnum());
    }


    @Test
    void getItemWithMinMaxPricesByCategory_shouldReturnValidResponse() throws Exception {
        // given
        when(itemRepository.findByCategory("상의")).thenReturn(Optional.of(itemList.stream().filter(item -> "상의".equals(item.getCategory())).toList()));

        // when
        String result = itemService.getItemWithMinMaxPricesByCategory("상의");

        // then
        assertNotNull(result);
        JSONObject jsonObject = new JSONObject(result);
        assertEquals(jsonObject.getString("category"), "상의");

        JSONArray minJsonArray = jsonObject.getJSONArray("minPrice");
        assertEquals(1, minJsonArray.length());
        assertEquals("C", minJsonArray.getJSONObject(0).getString("brand"));
        assertEquals(10000, minJsonArray.getJSONObject(0).getLong("price"));

        JSONArray maxJsonArray = jsonObject.getJSONArray("maxPrice");
        assertEquals(1, maxJsonArray.length());
        assertEquals("I", maxJsonArray.getJSONObject(0).getString("brand"));
        assertEquals(11400, maxJsonArray.getJSONObject(0).getLong("price"));


        // Verify that findAll was called once
        verify(itemRepository, times(1)).findByCategory("상의");
    }

    @Test
    void getItemWithMinMaxPricesByCategory_shouldThrowExceptionWhenCategoryNoItem() {
        // given
        when(itemRepository.findByCategory(anyString())).thenReturn(Optional.empty());

        // when & then
        ItemCustomException exception = assertThrows(ItemCustomException.class, () ->
                itemService.getItemWithMinMaxPricesByCategory("상의")
        );

        assertEquals(ErrorEnum.NO_CONTENT_CATEGORY, exception.getErrorEnum());
    }

    @Test
    void createItem_shouldSaveNewItem() throws Exception {
        // given
        when(itemRepository.findByBrandAndCategory(anyString(), anyString()))
                .thenReturn(Optional.empty());

        Item newItem = new Item("A", "상의", 10000);
        when(itemRepository.save(ArgumentMatchers.any(Item.class))).thenReturn(newItem);

        // when
        Item result = itemService.createItem(new ItemRequest("A", "상의", 10000));

        // then
        assertNotNull(result);
        assertEquals("A", result.getBrand());
        assertEquals("상의", result.getCategory());
        assertEquals(10000, result.getPrice());

        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void createItem_shouldThrowExceptionIfItemAlreadyExists() {
        // given
        Item existingItem = new Item("A", "상의", 10000);
        when(itemRepository.findByBrandAndCategory(anyString(), anyString()))
                .thenReturn(Optional.of(existingItem));

        // when & then
        ItemCustomException exception = assertThrows(ItemCustomException.class, () ->
                itemService.createItem(new ItemRequest("A", "상의", 10000))
        );

        assertEquals(ErrorEnum.ALREADY_EXIST_ITEM, exception.getErrorEnum());
    }

    @Test
    void updateItem_shouldUpdateExistingItem() throws Exception {
        // given
        Item originalItem = new Item("A", "상의", 10000);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(originalItem));

        Item updatedItem = new Item("B", "하의", 20000);
        when(itemRepository.save(ArgumentMatchers.any(Item.class))).thenReturn(updatedItem);

        // when
        Item result = itemService.updateItem(1L, new ItemRequest("B", "하의", 20000));

        // then
        assertNotNull(result);
        assertEquals("B", result.getBrand());
        assertEquals("하의", result.getCategory());
        assertEquals(20000, result.getPrice());

        verify(itemRepository, times(1)).findById(anyLong());
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void updateItem_shouldThrowExceptionIfItemAlreadyExists() {
        // given
        Item originalItem = new Item("A", "상의", 10000);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(originalItem));

        Item existingItem = new Item("B", "하의", 10000);
        when(itemRepository.findByBrandAndCategory(anyString(), anyString()))
                .thenReturn(Optional.of(existingItem));

        // when & then
        ItemCustomException exception = assertThrows(ItemCustomException.class, () ->
                itemService.updateItem(1L, new ItemRequest("B", "하의", 10000))
        );

        assertEquals(ErrorEnum.ALREADY_EXIST_ITEM, exception.getErrorEnum());
    }

    @Test
    void updateItem_shouldThrowExceptionWhenItemNotFound() {
        // given
        ItemRequest itemRequest = new ItemRequest("B", "하의", 20000);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        ItemCustomException exception = assertThrows(ItemCustomException.class, () -> {
            itemService.updateItem(1L, itemRequest);
        });

        assertEquals(ErrorEnum.NO_EXIST_ITEM, exception.getErrorEnum());

        verify(itemRepository, times(1)).findById(anyLong());
        verify(itemRepository, times(0)).save(any(Item.class));
    }

    @Test
    void deleteItem_shouldDeleteExistingItem() throws Exception {
        // given
        Item originalItem = new Item("A", "상의", 10000);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(originalItem));

        // when
        Item result = itemService.deleteItem(1L);

        // then
        assertNotNull(result);
        assertEquals(originalItem, result);

        verify(itemRepository, times(1)).findById(anyLong());
        verify(itemRepository, times(1)).delete(any(Item.class));
    }

    @Test
    void deleteItem_shouldThrowExceptionWhenItemNotFound() {
        // given
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        ItemCustomException exception = assertThrows(ItemCustomException.class, () -> {
            itemService.deleteItem(1L);
        });

        assertEquals(ErrorEnum.NO_EXIST_ITEM, exception.getErrorEnum());

        verify(itemRepository, times(1)).findById(anyLong());
        verify(itemRepository, times(0)).delete(any(Item.class));
    }
}