package org.yunhongmin.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "deliveries")
@Getter
public class Delivery extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @Embedded
    @Setter
    private Address address;

    @Enumerated(EnumType.STRING)
    @Setter
    private DeliveryStatus status;
}
