package org.yunhongmin.shop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@Getter
public class OrderItem extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @Setter
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @Setter
    private Order order;

    @Column(name = "order_price")
    @Setter
    private int orderPrice;

    @Setter
    private int count;
}