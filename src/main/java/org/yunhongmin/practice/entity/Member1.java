package org.yunhongmin.practice.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
//@DynamicUpdate
@Table(name = "member", uniqueConstraints = {@UniqueConstraint(
        name = "udx_member_name_age", columnNames = {"username", "age"})})
public class Member1 {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 10)
    private String username;

    @Transient
    private String lastName = "";

    @Transient
    private String firstName = "";

    private String fullName;

    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type")
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;


    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Access(AccessType.PROPERTY)
    @Column(name = "full_name")
    public String getFullName() {
        return firstName + lastName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
