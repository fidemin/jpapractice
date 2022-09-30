package org.yunhongmin.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
public class Order extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Setter
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", unique = true)
    @Setter
    private Delivery delivery;

    @Column(name = "order_datetime")
    @Setter
    private Date orderDateTime;

    @Enumerated(EnumType.STRING)
    @Setter
    private OrderStatus status;
}
