package org.yunhongmin.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.yunhongmin.shop.domain.Order;
import org.yunhongmin.shop.dto.ListOrderDto;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "user.name", target = "userName")
    ListOrderDto toListOrderDto(Order order);
}
