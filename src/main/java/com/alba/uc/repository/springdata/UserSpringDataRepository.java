package com.alba.uc.repository.springdata;

import com.alba.uc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSpringDataRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByConsumerId(Long consumerId);

    Optional<User> findByTokenToken(String token);

}
