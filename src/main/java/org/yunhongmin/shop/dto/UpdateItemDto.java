package org.yunhongmin.shop.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UpdateItemDto {
    private Long id;
    private String name;
    private Integer price;
    private int stockQuantity;
}
