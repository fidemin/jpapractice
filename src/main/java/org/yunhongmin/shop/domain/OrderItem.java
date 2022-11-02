package org.yunhongmin.shop.domain;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.*;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Table(name = "order_items")
@Audited
//@Audited(targetAuditMode = NOT_AUDITED)
@AuditOverride(forClass = BaseEntity.class)
@Getter
public class OrderItem extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @Setter
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @Setter
    private Order order;

    @Column(name = "order_price")
    @Setter
    private int orderPrice;

    @Setter
    private int count;
}