package com.dev.digtransaction.service;

import com.dev.digtransaction.domain.user.User;
import com.dev.digtransaction.dto.UserRequest;
import com.dev.digtransaction.exception.UserNotFoundException;
import com.dev.digtransaction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User createUser(UserRequest userDTO) {
        User newUser = new User(userDTO);
        save(newUser);
        return newUser;
    }

    public void save(User user) {
        repository.save(user);
    }

    public User findUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User id " + id + " not found in database"));
    }

    public List<User> getAll() {
        return repository.findAll();
    }

}
