package org.yunhongmin.shop.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.yunhongmin.shop.domain.User;
import org.yunhongmin.shop.dto.JoinUserDto;
import org.yunhongmin.shop.dto.ListUserDto;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "city", target = "address.city")
    @Mapping(source = "street", target = "address.street")
    @Mapping(source = "zipcode", target = "address.zipcode")
    User toUser(JoinUserDto joinUserDto);

    @Mapping(source = "address.city", target = "city")
    @Mapping(source = "address.street", target = "street")
    @Mapping(source = "address.zipcode", target = "zipcode")
    ListUserDto toListUserDto(User user);
}
