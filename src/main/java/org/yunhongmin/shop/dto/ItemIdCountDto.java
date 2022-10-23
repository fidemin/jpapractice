package org.yunhongmin.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemIdCountDto {
    private Long itemId;
    private int count;
}
