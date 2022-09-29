package org.yunhongmin.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
public class User extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Setter
    private String name;

    @Embedded
    @Setter
    private Address address;
}
