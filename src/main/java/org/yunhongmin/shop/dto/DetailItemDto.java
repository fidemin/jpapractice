package org.yunhongmin.shop.dto;

import lombok.Data;

@Data
public class DetailItemDto {
    private Long id;
    private String name;
    private Integer price;
    private int stockQuantity;
}
