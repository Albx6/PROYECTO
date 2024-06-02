package com.alba.uc.repository.springdata;

import com.alba.uc.model.Consumer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConsumerSpringDataRepository  extends JpaRepository<Consumer, Long> {

    Optional<Consumer> findByEmail(String email);

    Optional<Consumer> findByApiKey(String apiKey);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM consumer c WHERE c.id = :id", nativeQuery = true)
    void deleteById(Long id);

}
