package com.alba.uc.controller;

import com.alba.uc.controller.dto.UpdateUserRequest;
import com.alba.uc.controller.dto.UserLoginRequest;
import com.alba.uc.controller.dto.UserResponse;
import com.alba.uc.model.User;
import com.alba.uc.service.AuthorizationService;
import com.alba.uc.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequest userLoginRequest) {
        return userService.login(userLoginRequest);
    }

    @GetMapping
    public UserResponse getUser(@RequestHeader("Authorization") String token) {
        Long id = authorizationService.checkUser(token);
        return modelMapper.map(userService.getById(id), UserResponse.class);
    }

    @PutMapping
    public void updateUser(@RequestHeader("Authorization") String token,
                           @RequestBody UpdateUserRequest updateUserRequest) {
        Long id = authorizationService.checkUser(token);
        userService.update(updateUserRequest, id);
    }

    @PutMapping("/disable")
    public void disableUser(@RequestHeader("Authorization") String token) {
        Long id = authorizationService.checkUser(token);
        userService.updateStatus(Boolean.TRUE, id);
    }

    @PutMapping("/enable")
    public void enableUser(@RequestHeader("Authorization") String token) {
        Long id = authorizationService.checkUserDisable(token);
        userService.updateStatus(Boolean.FALSE, id);
    }

    @PutMapping("/verified")
    public void verifiedUser(@RequestHeader("Authorization") String token) {
        Long id = authorizationService.checkUser(token);
        userService.verifiedEmail( id);
    }

    @DeleteMapping
    public void deleteUser(@RequestHeader("Authorization") String token) {
        Long id = authorizationService.checkUser(token);
        userService.deleteUser(id);
    }


}
