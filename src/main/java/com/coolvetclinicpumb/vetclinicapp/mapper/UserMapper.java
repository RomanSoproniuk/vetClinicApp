package com.coolvetclinicpumb.vetclinicapp.mapper;

import com.coolvetclinicpumb.vetclinicapp.config.MapperConfig;
import com.coolvetclinicpumb.vetclinicapp.dto.UserResponseDto;
import com.coolvetclinicpumb.vetclinicapp.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toUserResponse(User user);
}
