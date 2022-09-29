package org.yunhongmin.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "category_items", uniqueConstraints =
        {@UniqueConstraint(name = "udx_category_item_category_item", columnNames = {"category_id", "item_id"})})
public class CategoryItem extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "category_item_id")
    @Getter
    private Long categoryItemId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @Getter @Setter
    private Category category;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @Getter @Setter
    private Item item;
}
