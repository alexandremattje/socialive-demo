package com.socialive.demo.contract;

import com.socialive.demo.controller.dto.UserDto;
import com.socialive.demo.exception.RecordAlreadyExistsException;
import com.socialive.demo.exception.RecordNotFoundException;

public interface UserService {

    UserDto getUser (String email) throws RecordNotFoundException;

    UserDto createUser(UserDto userDto) throws RecordAlreadyExistsException;

    UserDto updateUser(UserDto userDto) throws RecordNotFoundException;

    void deleteUser(String email) throws RecordNotFoundException;
}
