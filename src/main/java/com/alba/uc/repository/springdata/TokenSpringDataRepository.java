package com.alba.uc.repository.springdata;

import com.alba.uc.model.Token;
import com.alba.uc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenSpringDataRepository extends JpaRepository<Token, Long> {

    @Query("SELECT CASE WHEN t.expirationDate < CURRENT_TIMESTAMP THEN true ELSE false END FROM Token t WHERE t.id = :id")
    boolean isExpiredById(Long id);

    Optional<Token> findByUserId(Long userId);

}
