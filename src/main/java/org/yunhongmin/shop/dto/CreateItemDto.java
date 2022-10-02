package org.yunhongmin.shop.dto;

import lombok.Data;
import lombok.Setter;

import javax.persistence.Column;

@Data
public class CreateItemDto {
    private String name;
    private int price;
    private int stockQuantity;
}
