package org.yunhongmin.shop.service;

import org.javatuples.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yunhongmin.shop.domain.*;
import org.yunhongmin.shop.dto.ItemIdCountDto;
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
@ContextConfiguration(locations = "classpath:testAppConfig.xml")
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
        Address address = new Address("seoul", "mapo", "12345");
        Long userId = createUser("yunhong", new Address("seoul", "mapo", "12345"));

        List<ItemIdCountDto> itemCountPairs = new ArrayList<>();
        Long item1Id = createItem("item1", 1000, 10);
        itemCountPairs.add(new ItemIdCountDto(item1Id, 3));
        Long item2Id = createItem("item2", 2000, 20);
        itemCountPairs.add(new ItemIdCountDto(item2Id, 7));

        // When
        Long orderId = orderService.order(userId, itemCountPairs);

        // Then
        Order order = orderRepository.findOne(orderId);
        Delivery delivery = order.getDelivery();

        assertEquals(OrderStatus.ORDER, order.getStatus());
        assertEquals(address, delivery.getAddress());
        assertEquals(DeliveryStatus.READY, delivery.getStatus());
        assertEquals(3 * 1000 + 7 * 2000, order.getTotalPrice());

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

    @Test
    @Transactional
    public void search() {
        // Given
        Address address = new Address("seoul", "mapo", "12345");
        Long userId = createUser("yunhong", address);

        List<ItemIdCountDto> itemIdCountDtos = new ArrayList<>();
        Long item1Id = createItem("item1", 1000, 10);
        itemIdCountDtos.add(new ItemIdCountDto(item1Id, 3));
        Long item2Id = createItem("item2", 2000, 20);
        itemIdCountDtos.add(new ItemIdCountDto(item2Id, 7));
        orderService.order(userId, itemIdCountDtos);

        ArrayList<Pair<Long, Integer>> itemCountPairs2 = new ArrayList<>();
        itemIdCountDtos.add(new ItemIdCountDto(item1Id, 3));
        Long order2Id = orderService.order(userId, itemIdCountDtos);

        orderService.cancel(order2Id);

        // When1
        OrderSearch orderSearch = new OrderSearch();
        List<Order> orders = orderService.search(orderSearch);

        // Then1
        assertEquals(2, orders.size());

        // When2
        orderSearch = new OrderSearch();
        orderSearch.setOrderStatus(OrderStatus.ORDER);
        orders = orderService.search(orderSearch);

        // Then2
        assertEquals(1, orders.size());

        // When3
        orderSearch = new OrderSearch();
        orderSearch.setMemberName("yunhong");
        orders = orderService.search(orderSearch);

        // Then3
        assertEquals(2, orders.size());

        // When4
        orderSearch = new OrderSearch();
        orderSearch.setMemberName("yunhong");
        orderSearch.setOrderStatus(OrderStatus.CANCEL);
        orders = orderService.search(orderSearch);

        // Then4
        assertEquals(1, orders.size());

        // When5
        orderSearch = new OrderSearch();
        orderSearch.setMemberName("yunhong1");
        orders = orderService.search(orderSearch);

        // Then5
        assertEquals(0, orders.size());
    }

    private Long createOrder() {
        Address address = new Address("seoul", "mapo", "12345");
        Long userId = createUser("yunhong", address);

        List<ItemIdCountDto> itemIdCountDtos = new ArrayList<>();
        Long item1Id = createItem("item1", 1000, 10);
        itemIdCountDtos.add(new ItemIdCountDto(item1Id, 3));
        Long item2Id = createItem("item2", 2000, 20);
        itemIdCountDtos.add(new ItemIdCountDto(item2Id, 7));

        return orderService.order(userId, itemIdCountDtos);
    }

    private Long createUser(String username, Address address) {
        User user = new User();
        user.setName(username);
        user.setAddress(address);
        userRepository.save(user);
        return user.getId();
    }

    private Long createItem(String name, int price, int stockQuantity) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        itemRepository.save(item);
        return item.getId();
    }

}