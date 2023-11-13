package com.example.demo.controller;

import com.example.demo.contract.UserService;
import com.example.demo.controller.dto.UserDto;
import com.example.demo.exception.RecordAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes="application/json", produces = "application/json")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) throws RecordAlreadyExistsException {
        return ResponseEntity.ok(this.userService.createUser(user));
    }

    @PutMapping(consumes="application/json", produces = "application/json")
    @CacheEvict(value = "email", key = "#user.email")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) throws Throwable {
        return ResponseEntity.ok(this.userService.updateUser(user));
    }

    @DeleteMapping(path = "/{email}")
    @CacheEvict(value = "email")
    public ResponseEntity<Boolean> deleteUser(@PathVariable String email) throws Throwable {
        this.userService.deleteUser(email);
        return ResponseEntity.ok(true);
    }

    @GetMapping(path = "/{email}", produces = "application/json")
    @Cacheable(value = "email")
    public ResponseEntity<UserDto> getUserCached(@PathVariable String email) throws Throwable {
        UserDto user = this.userService.getUser(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping(path = "/{email}/httpcached", produces = "application/json")
    public ResponseEntity<UserDto> getUserHttpCached(@PathVariable String email, WebRequest webRequest) throws Throwable{
        UserDto user = this.userService.getUser(email);
        if (webRequest.checkNotModified(Integer.toString(user.hashCode()), user.getLastModified().getTime())) {
            return null;
        }
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES))
                .eTag(Integer.toString(user.hashCode()))
                .lastModified(user.getLastModified().toInstant())
                .body(user);
    }

}
