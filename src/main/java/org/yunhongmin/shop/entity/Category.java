package org.yunhongmin.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id @GeneratedValue
    @Column(name = "category_id")
    @Getter
    private Long categoryId;

    @Getter @Setter
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @Getter @Setter
    private Category parent;
}
