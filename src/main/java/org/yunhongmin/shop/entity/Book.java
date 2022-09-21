package org.yunhongmin.shop.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "books")
@DiscriminatorValue("B")
public class Book extends Item {
    private String author;
    private String isbn;
}
