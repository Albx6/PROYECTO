package com.alba.uc.service;

import com.alba.uc.model.Token;
import com.alba.uc.model.User;
import com.alba.uc.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private KeysService keysService;

    public Optional<Token> getTokenByUserId(Long userId) {
        return tokenRepository.getByUserId(userId);
    }

    public boolean isExpired(Token token) {
        return tokenRepository.isExpired(token.getId());
    }

    public Token newToken(User user) {
        Token token = new Token();
        token.setUser(user);
        token.setToken(keysService.generateKey(100));
        token.setCreationDate(LocalDateTime.now());
        token.setExpirationDate(LocalDateTime.now().plusHours(24));
        tokenRepository.saveToken(token);
        return token;
    }

    public void deleteToken(Token token) {
        tokenRepository.delete(token);
    }

}
