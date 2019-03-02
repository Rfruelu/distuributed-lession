package com.lujia.mapper;

import com.lujia.domain.User;
import com.lujia.domain.UserDto;
import com.lujia.domain.UserObject;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author :lujia
 * @date :2018/12/20  9:53
 */
@Mapper(componentModel = "spring")
public interface UserConvertMapper {

    UserConvertMapper USER_CONVERT_MAPPER= Mappers.getMapper(UserConvertMapper.class);

    @Mappings({
            @Mapping(source = "name", target = "userName"),
            @Mapping(source = "age",target = "userAge"),
            @Mapping(source = "date",target = "date",dateFormat = "yyyy-mm-dd HH:MM:SS")
            }
             )
    UserDto userToUserDto(User user);

    @InheritConfiguration
    User userDtoToUser(UserDto user);

    List<UserDto> usersToUserDtos(List<User> users);


    @Mappings({
            @Mapping(source = "user.password" ,target = "password")
    })
    UserObject fromUserAndUserDto(User user,UserDto userDto);

}
