package org.yunhongmin.practice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "team1")
public class Team {
    @Id @GeneratedValue
    private Long id;

    @Getter @Setter
    private String name;
}
