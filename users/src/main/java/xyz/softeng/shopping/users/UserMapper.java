package xyz.softeng.shopping.users;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserMapper {
    User fromDto(UserDto dto);

    User update(@MappingTarget User user, UserDto dto);
}
