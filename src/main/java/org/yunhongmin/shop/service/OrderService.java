package org.yunhongmin.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yunhongmin.shop.domain.*;
import org.yunhongmin.shop.dto.ItemIdCountDto;
import org.yunhongmin.shop.exception.OrderCancelException;
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
    public Long order(Long userId, List<ItemIdCountDto> itemIdCountDtoList) {
        User user = userRepository.findOne(userId);

        Delivery delivery = new Delivery();
        delivery.setAddress(user.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        Order order = new Order();
        order.setDelivery(delivery);
        order.setUser(user);
        order.setOrderDateTime(new Date());
        order.setStatus(OrderStatus.ORDER);
        orderRepository.save(order);

        int totalPrice  = 0;

        for (ItemIdCountDto itemIdCountDto: itemIdCountDtoList) {
            Long itemId = itemIdCountDto.getItemId();
            Integer count = itemIdCountDto.getCount();

            Item item = itemRepository.findOne(itemId);
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setItem(item);
            orderItem.setCount(count);
            orderItem.setOrderPrice(item.getPrice());
            item.removeStock(count);
            totalPrice += item.getPrice() * count;
            orderItemRepository.save(orderItem);
        }

        order.setTotalPrice(totalPrice);
        return order.getId();
    }

    @Transactional
    public void cancel(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        if (order.getDelivery().getStatus() != DeliveryStatus.READY) {
            throw new OrderCancelException("The delivery is in progress or completed.");
        }
        order.setStatus(OrderStatus.CANCEL);
        List<OrderItem> orderItems = orderItemRepository.findOrderItemsByOrderId(orderId);
        orderItems.forEach(orderItem -> {
            orderItem.getItem().addStock(orderItem.getCount());
        });
    }

    @Transactional
    public List<Order> search(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }

}
