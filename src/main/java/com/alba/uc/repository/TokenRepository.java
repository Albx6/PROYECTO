package com.alba.uc.repository;

import com.alba.uc.model.Token;
import com.alba.uc.repository.springdata.TokenSpringDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TokenRepository {

    @Autowired
    private TokenSpringDataRepository tokenSpringDataRepository;

    public void saveToken(Token token) {
        tokenSpringDataRepository.save(token);
    }

    public Optional<Token> getByUserId(Long userId) {
        return tokenSpringDataRepository.findByUserId(userId);
    }

    public void delete(Token token) {
        tokenSpringDataRepository.delete(token);
    }

    public boolean isExpired(Long tokenId) {
        return tokenSpringDataRepository.isExpiredById(tokenId);
    }

}
