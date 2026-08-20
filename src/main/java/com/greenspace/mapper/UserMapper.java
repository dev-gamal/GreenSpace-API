package com.greenspace.mapper;

import com.greenspace.dto.request.UserRegistrationRequest;
import com.greenspace.dto.response.UserResponse;
import com.greenspace.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isBlocked", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "gardens", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "products", ignore = true)
    User toEntity(UserRegistrationRequest request);
}
