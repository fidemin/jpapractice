package org.yunhongmin.shop.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yunhongmin.shop.exception.NotEnoughStockException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appConfig.xml")
@Transactional
public class ItemTest {
    @PersistenceContext
    EntityManager em;

    @Test
    public void addStock() {
        // Given
        Item item = new Item();
        item.setName("item 1");
        item.setPrice(1000);
        item.setStockQuantity(5);
        em.persist(item);

        // When
        item.addStock(3);

        // Then
        assertEquals(8, item.getStockQuantity());
    }

    @Test
    public void removeStockSuccess() {
        // Given
        Item item = new Item();
        item.setName("item 1");
        item.setPrice(1000);
        item.setStockQuantity(5);
        em.persist(item);

        // When
        item.removeStock(3);

        // Then
        Assert.assertEquals(2, item.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void removeStockFail() {
        // Given
        Item item = new Item();
        item.setName("item 1");
        item.setPrice(1000);
        item.setStockQuantity(5);
        em.persist(item);

        // When
        item.removeStock(6);

        // Then
        fail("NotEnoughStockException required");
    }
}