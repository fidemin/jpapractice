package org.yunhongmin.shop.repository;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.yunhongmin.shop.domain.Order;
import org.yunhongmin.shop.domain.OrderSearch;
import org.yunhongmin.shop.domain.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class OrderRepository {
    @PersistenceContext
    EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        StringBuilder builder = new StringBuilder();
        builder.append("select o from Order o join fetch o.user where 1=1");

        OrderStatus orderStatus = orderSearch.getOrderStatus();
        String memberName = orderSearch.getUserName();

        if (orderStatus != null) {
            builder.append(" and o.status = :status");
        }

        if (StringUtils.hasText(memberName)) {
            builder.append(" and o.user.name = :name");
        }

        Query query = em.createQuery(builder.toString(), Order.class);

        if (orderStatus != null) {
            query = query.setParameter("status", orderStatus);
        }

        if (StringUtils.hasText(memberName)) {
            query = query.setParameter("name", memberName);
        }

        List<Order> orders = query.getResultList();
        orders.forEach(order -> {
            order.getOrderItems().size();
        });
        return orders;
    }
}
