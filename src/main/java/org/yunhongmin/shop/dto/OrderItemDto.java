package org.yunhongmin.shop.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long id;
    private String name;
    private int orderPrice;
    private int count;
}
