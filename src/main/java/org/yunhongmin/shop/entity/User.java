package org.yunhongmin.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long userId;

    private String name;

    private String city;
    private String street;
    private String zipcode;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "modified_at")
    private Date modifiedAt;
}
