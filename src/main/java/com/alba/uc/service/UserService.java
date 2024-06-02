package com.alba.uc.service;

import com.alba.uc.controller.dto.UpdateUserRequest;
import com.alba.uc.controller.dto.UserLoginRequest;
import com.alba.uc.controller.dto.UserRequest;
import com.alba.uc.exception.ResourceAlreadyExistsException;
import com.alba.uc.exception.UnauthorizedException;
import com.alba.uc.model.Consumer;
import com.alba.uc.model.Token;
import com.alba.uc.model.User;
import com.alba.uc.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    public void register(Consumer consumer, UserRequest userRequest) {
         Optional<User> optUser = userRepository.getByEmail(userRequest.getEmail());

         if (optUser.isPresent()) {
             throw new ResourceAlreadyExistsException("El email ya esta registrado");
         }

         User user = new User();
         user.setConsumer(consumer);
         user.setEmail(userRequest.getEmail());
         user.setName(userRequest.getName());
         user.setLastName(userRequest.getLastName());
         user.setPassword(userRequest.getPassword());
         user.setDisabled(false);
         user.setPhotoUrl(null);
         user.setToken(null);
         user.setCreationDate(LocalDateTime.now());
         user.setVerifiedEmail(false);
         user.setAccessDate(null);

         userRepository.saveUser(user);
    }

    public List<User> getByCustomerId(Long customerId) {
        return userRepository.getUserByIdConsumer(customerId);
    }

    private User getUserByEmail(String email) {
        Optional<User> optUser = userRepository.getByEmail(email);

        if (optUser.isEmpty()) {
            throw new UnauthorizedException("Usuario no existe");
        }
        return optUser.get();
    }

    public String login(UserLoginRequest userLoginRequest) {
        User user = this.getUserByEmail(userLoginRequest.getEmail());

        if (!userLoginRequest.getPassword().equals(user.getPassword())){
            throw new UnauthorizedException("Contraseña no valida") ;
        }

        if (user.getDisabled()) {
            throw new UnauthorizedException("Usuario deshabilitado");
        }

        Optional<Token> optToken = tokenService.getTokenByUserId(user.getId());

        Token token = optToken.orElseGet(() -> tokenService.newToken(user));

        if (tokenService.isExpired(token)) {
            tokenService.deleteToken(token);
            token = tokenService.newToken(user);
        }

        user.setToken(token);
        user.setAccessDate(LocalDateTime.now());
        userRepository.saveUser(user);

        return token.getToken();
    }

    public Optional<User> getUserByToken(String token) {
        return userRepository.getByToken(token);
    }

    public User getById(Long userId) {
        return userRepository.getById(userId);
    }

    public void save(User user) {
        userRepository.saveUser(user);
    }

    public void update(UpdateUserRequest updateUserRequest, Long id) {
        User user = userRepository.getById(id);
        user.setName(updateUserRequest.getName());
        user.setLastName(updateUserRequest.getLastName());
        user.setPassword(updateUserRequest.getPassword());
        userRepository.saveUser(user);
    }

    public void updateStatus(Boolean status, Long id) {
        User user = userRepository.getById(id);
        user.setDisabled(status);
        userRepository.saveUser(user);
    }

    public void verifiedEmail(Long id) {
        User user = userRepository.getById(id);

        if (user.getVerifiedEmail()) {
            throw new ResourceAlreadyExistsException("El usuario ya tiene el correo verificado");
        }

        user.setVerifiedEmail(Boolean.TRUE);
        userRepository.saveUser(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.getById(id);

        Optional<Token> token = tokenService.getTokenByUserId(id);

        token.ifPresent(value -> tokenService.deleteToken(value));

        userRepository.delete(user);
    }

}
