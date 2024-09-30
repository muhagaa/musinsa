package com.musinsa.item.repository;

import com.musinsa.item.domain.Item;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<List<Item>> findByCategory(String category);

    Optional<Item> findByBrandAndCategory(String brand, String category);

}
