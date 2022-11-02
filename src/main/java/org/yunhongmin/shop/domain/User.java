package org.yunhongmin.shop.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Audited
@AuditOverride(forClass = BaseEntity.class)
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
