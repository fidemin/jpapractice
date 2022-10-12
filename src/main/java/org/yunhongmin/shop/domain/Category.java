package org.yunhongmin.shop.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

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

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @Getter @Setter
    private Category parent;
}
