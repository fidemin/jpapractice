package org.yunhongmin.shop.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.yunhongmin.shop.exception.NotEnoughStockException;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@Table(name = "items")
@Audited
@AuditOverride(forClass = BaseEntity.class)
@Getter
public class Item extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private Integer price;

    @Column(name = "stock_quantity")
    @Setter
    private int stockQuantity;

    public void addStock(int quantity) {
        stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        if (stockQuantity < quantity) {
            throw new NotEnoughStockException("the stocks removed exceeds current stocks");
        }
        stockQuantity -= quantity;
    }
}
