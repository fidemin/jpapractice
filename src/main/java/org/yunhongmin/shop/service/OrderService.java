package org.yunhongmin.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yunhongmin.shop.domain.*;
import org.yunhongmin.shop.dto.ItemIdCountDto;
import org.yunhongmin.shop.exception.NotEnoughStockException;
import org.yunhongmin.shop.exception.OrderCancelException;
import org.yunhongmin.shop.repository.ItemRepository;
import org.yunhongmin.shop.repository.OrderItemRepository;
import org.yunhongmin.shop.repository.OrderRepository;
import org.yunhongmin.shop.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderService {
    @Autowired OrderRepository orderRepository;
    @Autowired UserRepository userRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired OrderItemRepository orderItemRepository;

    @Transactional
    public Order order(Long userId, List<ItemIdCountDto> itemIdCountDtoList) {
        HashMap<Long, ItemIdCountDto> itemIdCountHashMap = new HashMap<>();
        for (ItemIdCountDto itemIdCountDto: itemIdCountDtoList) {
            itemIdCountHashMap.put(itemIdCountDto.getItemId(), itemIdCountDto);
        }

        if (itemIdCountDtoList.size() != itemIdCountHashMap.size()) {
            throw new IllegalArgumentException("some item ids are duplicate");
        }

        User user = userRepository.findOne(userId);

        Delivery delivery = new Delivery();
        delivery.setAddress(user.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        Order order = new Order();
        order.setDelivery(delivery);
        order.setUser(user);
        order.setOrderDateTime(new Date());
        order.setStatus(OrderStatus.ORDER);

        List<Item> items = itemRepository.findByIdIn(new ArrayList<>(itemIdCountHashMap.keySet()));

        if (items.size() != itemIdCountDtoList.size()) {
            throw new IllegalArgumentException("some item does not exist");
        }

        int totalPrice  = 0;
        for (Item item: items) {
            int count = itemIdCountHashMap.get(item.getId()).getCount();
            totalPrice += item.getPrice() * count;
        }

        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        for (Item item: items) {
            int count = itemIdCountHashMap.get(item.getId()).getCount();
            OrderItem orderItem = createOrderItem(item, order, count);
            orderItems.add(orderItem);
        }
        orderItemRepository.save(orderItems);

        return order;
    }

    private OrderItem createOrderItem(Item item, Order order, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice());

        try {
            item.removeStock(count);
        } catch (NotEnoughStockException e) {
            throw new IllegalArgumentException("Not enough stock :" + item.getId());
        }
        return orderItem;
    }

    @Transactional
    public void cancel(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        if (order.getDelivery().getStatus() != DeliveryStatus.READY) {
            throw new OrderCancelException("The delivery is in progress or completed.");
        }
        order.setStatus(OrderStatus.CANCEL);
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        orderItems.forEach(orderItem -> {
            orderItem.getItem().addStock(orderItem.getCount());
        });
    }

    @Transactional
    public List<Order> search(OrderSearch orderSearch) {
        return orderRepository.search(orderSearch);
    }

}
