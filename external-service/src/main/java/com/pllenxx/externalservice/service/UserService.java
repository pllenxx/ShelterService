package com.pllenxx.externalservice.service;

import com.pllenxx.externalservice.UserMapper;
import com.pllenxx.externalservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import com.pllenxx.externalservice.dto.UserDto;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public void saveUser(UserDto dto) {
        if (!repository.existsByUsername(dto.getUsername()))
            repository.save(mapper.mapUserDtoToUser(dto));
        else
            throw new BadCredentialsException("User with username " + dto.getUsername() + " already exists");
    }

}
