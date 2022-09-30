package org.yunhongmin.shop.repository;

import org.springframework.stereotype.Repository;
import org.yunhongmin.shop.domain.Order;
import org.yunhongmin.shop.domain.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderItemRepository {
    @PersistenceContext
    EntityManager em;

    public void save(OrderItem orderItem) {
        em.persist(orderItem);
    }

    public List<OrderItem> findOrderItemsByOrderId(Long orderId) {
        String query = "select oi from OrderItem oi where oi.order.id = :orderId order by oi.id";
        return em.createQuery(query, OrderItem.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }


}
