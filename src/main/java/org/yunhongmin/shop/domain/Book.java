package org.yunhongmin.shop.domain;

import javax.persistence.*;

@Entity
@Table(name = "books")
@DiscriminatorValue("B")
public class Book extends Item {
    private String author;
    private String isbn;
}
