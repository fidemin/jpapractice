package org.yunhongmin.shop.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "albums")
@DiscriminatorValue("A")
public class Album extends Item {
    private String artist;
    private String etc;
}
