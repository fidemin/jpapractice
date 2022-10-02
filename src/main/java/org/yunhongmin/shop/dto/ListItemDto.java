package org.yunhongmin.shop.dto;

import lombok.Data;

@Data
public class ListItemDto {
    private Long id;
    private String name;
    private Integer price;
    private int stockQuantity;
}
