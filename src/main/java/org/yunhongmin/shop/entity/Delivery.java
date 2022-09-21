package org.yunhongmin.shop.entity;

import javax.persistence.*;

@Entity
@Table(name = "deliveries")
public class Delivery extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long deliveryId;

    private String city;
    private String street;
    private String zipcode;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
