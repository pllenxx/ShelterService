package com.pllenxx.externalservice;

import org.springframework.stereotype.Component;
import com.pllenxx.externalservice.dto.UserDto;
import com.pllenxx.externalservice.model.User;
import com.pllenxx.externalservice.enums.Role;

import java.util.Objects;

@Component
public class UserMapper {
    public UserDto mapUserToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole().getAuthority());
        return dto;
    }

    public User mapUserDtoToUser(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(Objects.equals(dto.getRole(), "ROLE_ADMIN") ? Role.ROLE_ADMIN : Role.ROLE_USER);
        return user;
    }
}
