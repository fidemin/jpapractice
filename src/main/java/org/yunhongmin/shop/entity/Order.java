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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "delivery_id", unique = true)
    private Delivery delivery;

    @Column(name = "order_datetime")
    private Date orderedDatetime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
