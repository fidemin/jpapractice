package org.yunhongmin.shop.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "movies")
@DiscriminatorValue("M")
public class Movie extends Item {
    private String director;
    private String actor;
}
