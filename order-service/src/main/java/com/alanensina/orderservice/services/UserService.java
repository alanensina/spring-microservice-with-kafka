package com.alanensina.orderservice.services;

import com.alanensina.basedomains.dto.user.UserCreateRequestDTO;
import com.alanensina.basedomains.dto.user.UserCreateResponseDTO;
import com.alanensina.basedomains.dto.user.UserDTO;
import com.alanensina.basedomains.exceptions.UserErrorException;
import com.alanensina.orderservice.domains.User;
import com.alanensina.orderservice.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<UserCreateResponseDTO> create(UserCreateRequestDTO data) {

        User newUser = new User();
        newUser.setName(data.name());
        newUser.setEmail(data.email());

        try{
            newUser = userRepository.save(newUser);

            return ResponseEntity.ok(
                    new UserCreateResponseDTO(
                            newUser.getUserId(),
                            newUser.getName(),
                            newUser.getEmail()
                    )
            );
        } catch (Exception e) {
            String errorMessage = "Error to save an user. User: " + newUser + ". Error message: " + e.getMessage();
            LOGGER.error(errorMessage);
            throw new UserErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<UserDTO>> findAll() {
        try {
            List<User> users = userRepository.findAll();

            if (users.isEmpty()) {
                return ResponseEntity.ok(Collections.emptyList());
            }

            //TODO: need to fetch the user's orders
            var response = users.stream().map(
                            user -> new UserDTO(
                                    user.getUserId(),
                                    user.getName(),
                                    user.getEmail(),
                                    null))
                    .toList();

            return ResponseEntity.ok(response);
        } catch (Exception e){
            String errorMessage = "Error to get user's list. Error message: " + e.getMessage();
            LOGGER.error(errorMessage);
            throw new UserErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<UserDTO> findById(UUID userId) {
            User user = findUserById(userId);

            //TODO: need to fetch the user's orders
            return ResponseEntity.ok(new UserDTO(
                    user.getUserId(),
                    user.getName(),
                    user.getEmail(),
                    null)
            );
    }

    public User findUserById(UUID userId){
        try {
            Optional<User> opt = userRepository.findById(userId);

            if (opt.isEmpty()) {
                String errorMessage = "User not found. userId: " + userId;
                LOGGER.error(errorMessage);
                throw new UserErrorException(errorMessage, HttpStatus.BAD_REQUEST);
            }

            return opt.get();

        } catch (Exception e) {
            String errorMessage = "Error to get find an user. Error message: " + e.getMessage();
            LOGGER.error(errorMessage);
            throw new UserErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
