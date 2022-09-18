package org.yunhongmin.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private long orderId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "order_datetime")
    private Date orderedDatetime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
