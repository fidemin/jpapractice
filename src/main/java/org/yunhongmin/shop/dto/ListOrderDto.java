package org.yunhongmin.shop.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ListOrderDto {
    Long id;
    String userName;
    List<OrderItemDto> orderItems;
    String status;
    Date orderDateTime;
}
