package org.yunhongmin.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.yunhongmin.shop.domain.Item;
import org.yunhongmin.shop.dto.CreateItemDto;

@Mapper
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    Item toEntity(CreateItemDto createItemDto);
}
