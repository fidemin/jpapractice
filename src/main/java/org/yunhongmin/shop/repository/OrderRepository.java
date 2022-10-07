package org.yunhongmin.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yunhongmin.shop.domain.Order;
import org.yunhongmin.shop.repository.custom.CustomOrderRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, CustomOrderRepository {
}
