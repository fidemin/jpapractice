package org.yunhongmin.shop.repository;


import org.springframework.stereotype.Repository;
import org.yunhongmin.shop.domain.Item;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemRepository {
    @PersistenceContext
    EntityManager em;

    public void save(Item item) {
        em.persist(item);
    }

    public void update(Item item) {
        em.merge(item);
    }

    public Item findOne(Long itemId) {
        return em.find(Item.class, itemId);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
