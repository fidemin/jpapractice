package org.yunhongmin.practice.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "locker")
public class Locker {
    @Id @GeneratedValue
    @Getter
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    @Getter @Setter
    private Member member;

}
