package com.alba.uc.repository;

import com.alba.uc.model.User;
import com.alba.uc.repository.springdata.UserSpringDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private UserSpringDataRepository userSpringDataRepository;

    public void saveUser(User user) {
        userSpringDataRepository.save(user);
    }

    public Optional<User> getByEmail(String email) {
        return userSpringDataRepository.findByEmail(email);
    }

    public User getById(Long id) {
        return userSpringDataRepository.findById(id).get();
    }

    public void delete(User user) {
        userSpringDataRepository.delete(user);
    }

    public List<User> getUserByIdConsumer(Long idConsumer) {
        return userSpringDataRepository.findByConsumerId(idConsumer);
    }

    public Optional<User> getByToken(String token) {
        return userSpringDataRepository.findByTokenToken(token);
    }

}
