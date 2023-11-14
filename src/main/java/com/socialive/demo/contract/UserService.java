package com.socialive.demo.contract;

import com.socialive.demo.controller.dto.UserDto;
import com.socialive.demo.exception.RecordAlreadyExistsException;
import com.socialive.demo.exception.RecordNotFoundException;
import org.springframework.cache.annotation.CacheEvict;

public interface UserService {

    UserDto getUser (String email) throws RecordNotFoundException;

    UserDto createUser(UserDto userDto) throws RecordAlreadyExistsException;

    @CacheEvict(value = "email", key = "#userDto.email")
    UserDto updateUser(UserDto userDto) throws RecordNotFoundException;

    @CacheEvict(value = "email")
    void deleteUser(String email) throws RecordNotFoundException;
}
