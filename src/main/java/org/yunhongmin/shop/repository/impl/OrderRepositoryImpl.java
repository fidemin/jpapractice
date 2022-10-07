package org.yunhongmin.shop.repository.impl;

import org.springframework.util.StringUtils;
import org.yunhongmin.shop.domain.Order;
import org.yunhongmin.shop.domain.OrderSearch;
import org.yunhongmin.shop.domain.OrderStatus;
import org.yunhongmin.shop.repository.custom.CustomOrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class OrderRepositoryImpl implements CustomOrderRepository {
    @PersistenceContext
    EntityManager em;

    @Override
    public List<Order> search(OrderSearch orderSearch) {
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
