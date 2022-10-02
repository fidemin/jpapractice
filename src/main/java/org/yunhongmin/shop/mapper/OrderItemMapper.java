package org.yunhongmin.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.yunhongmin.shop.domain.OrderItem;
import org.yunhongmin.shop.dto.OrderItemDto;

@Mapper(uses = {OrderItemDto.class})
public interface OrderItemMapper {
    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    @Mapping(source = "item.name", target = "name")
    OrderItemDto toOrderItemDto(OrderItem orderItem);
}
