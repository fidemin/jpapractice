package org.yunhongmin.shop.repository.impl;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.util.StringUtils;
import org.yunhongmin.shop.domain.*;
import org.yunhongmin.shop.repository.custom.CustomOrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class OrderRepositoryImpl implements CustomOrderRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Order> search(OrderSearch orderSearch) {
        QOrder order = QOrder.order;
        QUser user = QUser.user;

        JPQLQuery query = new JPAQuery(em).from(order);

        String userName = orderSearch.getUserName();
        OrderStatus orderStatus = orderSearch.getOrderStatus();


        if (StringUtils.hasText(userName)) {
            query.leftJoin(order.user, user).where(user.name.contains(userName));
        }

        if (orderStatus != null) {
            query.where(order.status.eq(orderStatus));
        }

        List<Order> orders = query.list(order);

        orders.forEach(o -> {
            o.getOrderItems().size();
        });
        return orders;
    }
}
