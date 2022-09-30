package org.yunhongmin.shop.domain;

import lombok.Getter;
import lombok.Setter;
import org.yunhongmin.shop.exception.NotEnoughStockException;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@Table(name = "items")
@Getter
public class Item extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @Setter
    private String name;

    @Setter
    private int price;

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
