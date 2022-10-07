package org.yunhongmin.shop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.yunhongmin.shop.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
