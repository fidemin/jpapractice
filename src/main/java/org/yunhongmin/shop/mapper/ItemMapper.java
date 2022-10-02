package org.yunhongmin.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.yunhongmin.shop.domain.Item;
import org.yunhongmin.shop.dto.CreateItemDto;
import org.yunhongmin.shop.dto.DetailItemDto;
import org.yunhongmin.shop.dto.ListItemDto;
import org.yunhongmin.shop.dto.UpdateItemDto;

@Mapper
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    Item toEntity(CreateItemDto createItemDto);
    ListItemDto toListItemDto(Item item);
    DetailItemDto toDetailItemDto(Item item);


    void updateItemFromUpdateItemDto(UpdateItemDto updateItemDto, @MappingTarget Item item);
}
