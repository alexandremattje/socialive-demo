package com.example.demo.service;

import com.example.demo.contract.UserService;
import com.example.demo.controller.dto.UserDto;
import com.example.demo.exception.RecordAlreadyExistsException;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UserDto getUser(String email) throws RecordNotFoundException {
        return this.mapper.map(repository.findByEmail(email).orElseThrow(RecordNotFoundException::new), UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) throws RecordAlreadyExistsException {
        if (verifyEmailAlreadyExists(userDto.getEmail())) {
            throw new RecordAlreadyExistsException();
        }
        User user = this.mapper.map(userDto, User.class);
        user.setLastModified(new Date());
        return this.mapper.map(this.repository.save(user), UserDto.class);
    }

    private boolean verifyEmailAlreadyExists(String email) {
        return repository.findByEmail(email).isPresent();
    }

    @Override
    public UserDto updateUser(UserDto userDto) throws RecordNotFoundException {
        User user = this.repository.findByEmail(userDto.getEmail()).orElseThrow(RecordNotFoundException::new);
        user.setLastModified(new Date());
        user.setName(userDto.getName());
        return this.mapper.map(this.repository.save(user), UserDto.class);    }

    @Override
    public void deleteUser(String email) throws RecordNotFoundException {
        this.repository.delete(this.repository.findByEmail(email).orElseThrow(RecordNotFoundException::new));
    }
}
