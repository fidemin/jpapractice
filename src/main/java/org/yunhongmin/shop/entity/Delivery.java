package org.yunhongmin.shop.entity;

import javax.persistence.*;

@Entity
@Table(name = "deliveries")
public class Delivery extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long deliveryId;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
