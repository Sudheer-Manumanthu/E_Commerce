package com.sudheer.userauthenticationservice.services;

import com.sudheer.userauthenticationservice.clients.KafkaClient;
import com.sudheer.userauthenticationservice.exceptions.UserDoesNotExistsException;
import com.sudheer.userauthenticationservice.models.User;
import com.sudheer.userauthenticationservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements iUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserById(Long id) throws UserDoesNotExistsException {

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }

        throw new UserDoesNotExistsException("User does not exist with user id " + id);
    }
}
