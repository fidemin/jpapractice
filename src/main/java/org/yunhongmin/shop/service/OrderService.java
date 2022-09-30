package org.yunhongmin.shop.service;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yunhongmin.shop.domain.*;
import org.yunhongmin.shop.repository.ItemRepository;
import org.yunhongmin.shop.repository.OrderItemRepository;
import org.yunhongmin.shop.repository.OrderRepository;
import org.yunhongmin.shop.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired OrderRepository orderRepository;
    @Autowired UserRepository userRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired
    OrderItemRepository orderItemRepository;

    @Transactional
    public Long order(Long userId, List<Pair<Long, Integer>> itemIdCountPairList) {
        User user = userRepository.findOne(userId);

        Delivery delivery = new Delivery();
        delivery.setAddress(user.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        Order order = new Order();
        order.setDelivery(delivery);
        order.setUser(user);
        order.setOrderDateTime(new Date());
        orderRepository.save(order);

        for (Pair<Long, Integer> itemIdCountPair: itemIdCountPairList) {
            Long itemId = itemIdCountPair.getValue0();
            Integer count = itemIdCountPair.getValue1();

            Item item = itemRepository.findOne(itemId);
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setItem(item);
            orderItem.setCount(count);
            orderItem.setOrderPrice(item.getPrice());
            item.removeStock(count);
            orderItemRepository.save(orderItem);
        }

        return order.getId();
    }
}
