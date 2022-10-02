package org.yunhongmin.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.yunhongmin.shop.domain.OrderSearch;
import org.yunhongmin.shop.dto.OrderSearchDto;

@Mapper
public interface OrderSearchMapper {
    OrderSearchMapper INSTANCE = Mappers.getMapper(OrderSearchMapper.class);

    OrderSearch toOrderSearch(OrderSearchDto orderSearchDto);
}
