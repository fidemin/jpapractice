package org.yunhongmin.shop.dto;

import lombok.Data;

@Data
public class CreateItemDto {
    private String name;
    private int price;
    private int stockQuantity;
}
