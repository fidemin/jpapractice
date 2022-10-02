package org.yunhongmin.shop.service;

import org.javatuples.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yunhongmin.shop.domain.*;
import org.yunhongmin.shop.exception.OrderCancelException;
import org.yunhongmin.shop.repository.ItemRepository;
import org.yunhongmin.shop.repository.OrderItemRepository;
import org.yunhongmin.shop.repository.OrderRepository;
import org.yunhongmin.shop.repository.UserRepository;

import javax.transaction.Transactional;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appConfig.xml")
public class OrderServiceTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderService orderService;

    @Test
    @Transactional
    public void order() {
        // Given
        User user = new User();
        user.setName("yunhong");
        Address address = new Address("seoul", "mapo", "12345");
        user.setAddress(address);
        userRepository.save(user);

        ArrayList<Pair<Long, Integer>> itemCountPairs = new ArrayList<>();

        Item item1 = new Item();
        item1.setStockQuantity(10);
        item1.setPrice(1000);
        item1.setName("item1");
        itemRepository.save(item1);
        itemCountPairs.add(new Pair<>(item1.getId(), 3));

        Item item2 = new Item();
        item2.setStockQuantity(20);
        item2.setPrice(2000);
        item2.setName("item2");
        itemRepository.save(item2);
        itemCountPairs.add(new Pair<>(item2.getId(), 7));

        // When
        Long orderId = orderService.order(user.getId(), itemCountPairs);

        // Then
        Order order = orderRepository.findOne(orderId);
        Delivery delivery = order.getDelivery();

        assertEquals(OrderStatus.ORDER, order.getStatus());
        assertEquals(address, delivery.getAddress());
        assertEquals(DeliveryStatus.READY, delivery.getStatus());

        List<OrderItem> orderItems = orderItemRepository.findOrderItemsByOrderId(order.getId());
        assertEquals(2, orderItems.size());
        OrderItem orderItem1 = orderItems.get(0);
        assertEquals(1000, orderItem1.getOrderPrice());
        assertEquals(3, orderItem1.getCount());
        assertEquals(7, orderItem1.getItem().getStockQuantity());

        OrderItem orderItem2 = orderItems.get(1);
        assertEquals(2000, orderItem2.getOrderPrice());
        assertEquals(7, orderItem2.getCount());
        assertEquals(13, orderItem2.getItem().getStockQuantity());
    }

    @Test
    @Transactional
    public void cancel() {
        // Given
        Long orderId = createOrder();

        // When
        orderService.cancel(orderId);

        // Then
        Order order = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, order.getStatus());

        List<OrderItem> orderItems = orderItemRepository.findOrderItemsByOrderId(order.getId());
        OrderItem orderItem1 = orderItems.get(0);
        assertEquals(10, orderItem1.getItem().getStockQuantity());

        OrderItem orderItem2 = orderItems.get(1);
        assertEquals(20, orderItem2.getItem().getStockQuantity());
    }

    @Test(expected = OrderCancelException.class)
    @Transactional
    public void cancelFail() {
        // Given
        Long orderId = createOrder();
        Order order = orderRepository.findOne(orderId);
        order.getDelivery().setStatus(DeliveryStatus.IN_PROGRESS);

        // When
        orderService.cancel(orderId);

        // Then
        fail("OrderCancelException required");
    }

    private Long createOrder() {
        User user = new User();
        user.setName("yunhong");
        Address address = new Address("seoul", "mapo", "12345");
        user.setAddress(address);
        userRepository.save(user);

        ArrayList<Pair<Long, Integer>> itemCountPairs = new ArrayList<>();

        Item item1 = new Item();
        item1.setStockQuantity(10);
        item1.setPrice(1000);
        item1.setName("item1");
        itemRepository.save(item1);
        itemCountPairs.add(new Pair<>(item1.getId(), 3));

        Item item2 = new Item();
        item2.setStockQuantity(20);
        item2.setPrice(2000);
        item2.setName("item2");
        itemRepository.save(item2);
        itemCountPairs.add(new Pair<>(item2.getId(), 7));
        return orderService.order(user.getId(), itemCountPairs);
    }

}