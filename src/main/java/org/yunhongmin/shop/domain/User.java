package org.yunhongmin.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter @Setter
@PersistenceContext(name="shop")
public class User extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long userId;

    private String name;

    @Embedded
    private Address address;


}
