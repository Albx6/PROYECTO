package com.alba.uc.controller;

import com.alba.uc.controller.dto.ConsumerLoginRequest;
import com.alba.uc.controller.dto.ConsumerRequest;
import com.alba.uc.controller.dto.UserRequest;
import com.alba.uc.controller.dto.UserResponse;
import com.alba.uc.service.AuthorizationService;
import com.alba.uc.service.ConsumerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping
    public void register(@RequestBody ConsumerRequest consumerRequest) {
        consumerService.register(consumerRequest);
    }

    @PostMapping("/login")
    public String login(@RequestBody ConsumerLoginRequest consumerLoginRequest) {
        return consumerService.login(consumerLoginRequest);
    }

    @PutMapping
    public void updateCunsumer (@RequestHeader("Authorization") String apikey,
                                @RequestBody ConsumerRequest updateConsumer) {
        Long registerId = authorizationService.checkConsumer(apikey);
        consumerService.updateConsumer(updateConsumer, registerId);
    }

    @DeleteMapping
    public void deleteConsumer(@RequestHeader("Authorization") String apikey) {
        Long registerId = authorizationService.checkConsumer(apikey);
        consumerService.deleteConsumer(registerId);
    }

    @PostMapping("/user")
    public void addUsers(@RequestHeader("Authorization") String apikey,
                         @RequestBody UserRequest userRequest) {
        Long registerId = authorizationService.checkConsumer(apikey);
        consumerService.addUser(registerId, userRequest);
    }

    @GetMapping("/user")
    public List<UserResponse> getUsers(@RequestHeader("Authorization") String apikey) {
        Long registerId = authorizationService.checkConsumer(apikey);
        return consumerService.getUsers(registerId)
                .stream()
                .map(u -> modelMapper.map(u, UserResponse.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/token/{token}/is-valid")
    public Boolean tokenValid(@RequestHeader("Authorization") String apikey, @PathVariable("token") String token) {
        Long registerId = authorizationService.checkConsumer(apikey);
        return consumerService.tokenIsValid(registerId, token);
        
    }

}
