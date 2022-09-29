package org.yunhongmin.shop.domain;

import javax.persistence.*;

@Entity
@Table(name = "movies")
@DiscriminatorValue("M")
public class Movie extends Item {
    private String director;
    private String actor;
}
