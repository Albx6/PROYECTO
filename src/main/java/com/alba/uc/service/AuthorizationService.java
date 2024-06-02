package com.alba.uc.service;

import com.alba.uc.exception.UnauthorizedException;
import com.alba.uc.model.Consumer;
import com.alba.uc.model.Token;
import com.alba.uc.model.User;
import com.alba.uc.repository.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthorizationService {

    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    public Long checkConsumer(String apiKey) {
        Optional<Consumer> optConsumer = consumerRepository.getByApiKey(apiKey);

        if (optConsumer.isEmpty()) {
            throw new UnauthorizedException("Usuario no autorizado");
        }

        return optConsumer.get().getId();

    }

    public Long checkUser(String tokenKey) {
        Optional<User> optUser = userService.getUserByToken(tokenKey);

        if (optUser.isEmpty() || optUser.get().getDisabled()) {
            throw new UnauthorizedException("Usuario no autorizado");
        }

        User user = optUser.get();
        Token token = user.getToken();

        if (tokenService.isExpired(token)) {
            tokenService.deleteToken(token);
            throw new UnauthorizedException("Usuario sin login.");
        }
        user.setAccessDate(LocalDateTime.now());
        userService.save(user);
        return user.getId();
    }

    public Long checkUserDisable(String tokenKey) {
        Optional<User> optUser = userService.getUserByToken(tokenKey);

        if (optUser.isEmpty()) {
            throw new UnauthorizedException("Usuario no autorizado");
        }

        User user = optUser.get();
        Token token = user.getToken();

        if (tokenService.isExpired(token)) {
            tokenService.deleteToken(token);
            throw new UnauthorizedException("Usuario sin login.");
        }
        user.setAccessDate(LocalDateTime.now());
        userService.save(user);
        return user.getId();
    }

}
