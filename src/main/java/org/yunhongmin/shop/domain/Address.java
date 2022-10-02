package org.yunhongmin.shop.domain;


import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter @Setter
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
