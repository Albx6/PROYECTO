package com.alba.uc.repository;

import com.alba.uc.model.Consumer;
import com.alba.uc.repository.springdata.ConsumerSpringDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ConsumerRepository {

    @Autowired
    private ConsumerSpringDataRepository consumerSpringDataRepository;

    public void saveConsumer(Consumer consumer) {
        consumerSpringDataRepository.save(consumer);
    }

    public Optional<Consumer> getByEmail(String email) {
        return consumerSpringDataRepository.findByEmail(email);
    }

    public Optional<Consumer> getByApiKey(String apikey) {
        return consumerSpringDataRepository.findByApiKey(apikey);
    }

    public Consumer getById(Long id) {
        return consumerSpringDataRepository.findById(id).get();
    }

    public void delete(Consumer consumer) {
        consumerSpringDataRepository.deleteById(consumer.getId());
    }

}
