package org.yunhongmin.shop.repository.custom;

import org.yunhongmin.shop.domain.Order;
import org.yunhongmin.shop.domain.OrderSearch;

import java.util.List;

public interface CustomOrderRepository {
    List<Order> search(OrderSearch search);
}
