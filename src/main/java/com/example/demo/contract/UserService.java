package com.example.demo.contract;

import com.example.demo.controller.dto.UserDto;
import com.example.demo.exception.RecordAlreadyExistsException;
import com.example.demo.exception.RecordNotFoundException;

public interface UserService {

    UserDto getUser (String email) throws RecordNotFoundException;

    UserDto createUser(UserDto userDto) throws RecordAlreadyExistsException;

    UserDto updateUser(UserDto userDto) throws RecordNotFoundException;

    void deleteUser(String email) throws RecordNotFoundException;
}
