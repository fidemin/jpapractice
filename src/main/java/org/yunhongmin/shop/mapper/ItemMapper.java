package org.yunhongmin.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.yunhongmin.shop.domain.Item;
import org.yunhongmin.shop.dto.CreateItemDto;
import org.yunhongmin.shop.dto.ListItemDto;

@Mapper
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    Item toEntity(CreateItemDto createItemDto);
    ListItemDto toListItemDto(Item item);
}
