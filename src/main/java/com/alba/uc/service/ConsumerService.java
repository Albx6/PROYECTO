package com.alba.uc.service;

import com.alba.uc.controller.dto.ConsumerLoginRequest;
import com.alba.uc.controller.dto.ConsumerRequest;
import com.alba.uc.controller.dto.UserRequest;
import com.alba.uc.exception.UnauthorizedException;
import com.alba.uc.model.Consumer;
import com.alba.uc.model.Token;
import com.alba.uc.model.User;
import com.alba.uc.repository.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ConsumerService {

    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private KeysService keysService;

    @Autowired
    private TokenService tokenService;

    public void register(ConsumerRequest consumerRequest) {
        Consumer consumer = new Consumer();
        consumer.setName(consumerRequest.getName());
        consumer.setEmail(consumerRequest.getEmail());
        consumer.setPassword(consumerRequest.getPassword());
        consumer.setCreationDate(LocalDateTime.now());
        consumer.setApiKey(keysService.generateKey(100));
        consumerRepository.saveConsumer(consumer);
    }

    public  String login (ConsumerLoginRequest consumerLoginRequest) {
        Consumer consumer = this.getConsumerByEmail(consumerLoginRequest.getEmail());

        if (!consumerLoginRequest.getPassword().equals(consumer.getPassword())){
          throw new UnauthorizedException("Contraseña no valida") ;

        }
        return consumer.getApiKey();
    }

    private Consumer getConsumerByEmail(String email) {
        Optional<Consumer> optionalConsumer = consumerRepository.getByEmail(email);

        if (optionalConsumer.isEmpty()){
            throw new UnauthorizedException("Consumer no encontrado.");
        }
        return optionalConsumer.get();
    }

    public void updateConsumer(ConsumerRequest updateConsumer, Long idConsumer) {
        Consumer consumer =  consumerRepository.getById(idConsumer);
        consumer.setEmail(updateConsumer.getEmail());
        consumer.setName(updateConsumer.getName());
        consumer.setPassword(updateConsumer.getPassword());
        consumerRepository.saveConsumer(consumer);
    }

    public void deleteConsumer(Long id) {
        Consumer consumer =  consumerRepository.getById(id);
        List<User> users = this.getUsers(id);
        users.forEach(u -> userService.deleteUser(u.getId()));
        consumerRepository.delete(consumer);
    }

    public void addUser(Long id, UserRequest request) {
        Consumer consumer =  consumerRepository.getById(id);
        userService.register(consumer, request);
    }

    public List<User> getUsers(Long id) {
        return userService.getByCustomerId(id);
    }

    public boolean tokenIsValid(Long id, String tokenkey) {

        boolean isValid = Boolean.FALSE;

        Optional<User> optuser = userService.getUserByToken(tokenkey);

        if (optuser.isPresent()) {
            User user = optuser.get();
            if (user.getConsumer().getId() == id) {
                Optional<Token> token = tokenService.getTokenByUserId(user.getId());
                if (token.isPresent()) {
                    isValid = !tokenService.isExpired(token.get());
                }
            }
        }

        return isValid;
    }

}
