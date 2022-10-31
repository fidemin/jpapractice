package org.yunhongmin.shop.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ListUserDto {
    String id;
    String name;
    String city;
    String street;
    String zipcode;
    String createdAt;
    String modifiedAt;
}
