package org.yunhongmin.shop.domain;


import lombok.AllArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
