package org.yunhongmin.shop.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@Getter @Setter
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_price")
    private int orderPrice;

    private int count;
}