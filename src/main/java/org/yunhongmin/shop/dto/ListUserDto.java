package org.yunhongmin.shop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ListUserDto {
    String id;
    String name;
    String city;
    String street;
    String zipcode;
    Date createdAt;
    Date modifiedAt;
}
