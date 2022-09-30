package org.yunhongmin.shop.repository;

import org.springframework.stereotype.Repository;
import org.yunhongmin.shop.domain.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class OrderItemRepository {
    @PersistenceContext
    EntityManager em;

    public void save(OrderItem orderItem) {
        em.persist(orderItem);
    }


}
