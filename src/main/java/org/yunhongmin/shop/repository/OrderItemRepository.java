package org.yunhongmin.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yunhongmin.shop.domain.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);
}
