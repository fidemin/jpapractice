package org.yunhongmin.shop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.yunhongmin.shop.domain.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByIdIn(List<Long> ids);
}
