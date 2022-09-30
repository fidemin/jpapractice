package org.yunhongmin.shop.repository;

import org.springframework.stereotype.Repository;
import org.yunhongmin.shop.domain.Order;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class OrderRepository {
    @PersistenceContext
    EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

}
