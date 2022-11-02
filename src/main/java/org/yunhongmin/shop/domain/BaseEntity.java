package org.yunhongmin.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@MappedSuperclass
@Getter @Setter
public class BaseEntity {
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "modified_at")
    private Date modifiedAt;

    @PrePersist
    void createdAt() {
        this.createdAt = this.modifiedAt = new Date();
    }

    @PreUpdate
    void updatedAt() {
        this.modifiedAt = new Date();
    }
}
