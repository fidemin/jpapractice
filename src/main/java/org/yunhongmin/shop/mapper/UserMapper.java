package org.yunhongmin.shop.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.yunhongmin.shop.domain.User;
import org.yunhongmin.shop.dto.JoinUserDto;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "city", target = "address.city")
    @Mapping(source = "street", target = "address.street")
    @Mapping(source = "zipcode", target = "address.zipcode")
    User toUser(JoinUserDto joinUserDto);
}
