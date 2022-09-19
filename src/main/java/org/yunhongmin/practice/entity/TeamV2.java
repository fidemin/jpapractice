package org.yunhongmin.practice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teamV2")
public class TeamV2 {
    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @Getter @Setter
    private String name;

    @OneToMany
    @Getter @Setter
    @JoinColumn(name = "team_id_v2")
    private List<Member> members = new ArrayList<>();
}
