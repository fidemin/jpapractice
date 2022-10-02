package org.yunhongmin.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemIdCountDto {
    Long itemId;
    int count;
}
