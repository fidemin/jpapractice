package org.yunhongmin.practice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
public class Team {
    @Id @GeneratedValue
    @Getter
    private Long id;

    @Getter @Setter
    private String name;

    @OneToMany(mappedBy = "team")
    @Getter
    private List<Member> members = new ArrayList<>();
}
