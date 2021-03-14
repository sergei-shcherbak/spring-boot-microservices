package org.micro.user.service;

import org.micro.model.User;
import org.micro.model.UserCreationDTO;
import org.micro.model.UserRegularDTO;
import org.micro.model.converter.UserConverter;
import org.micro.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final String notificationUrl;
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(@Value("${notification.url}") String notificationUrl, UserRepository userRepository,
                       UserConverter userConverter, RestTemplate restTemplate, PasswordEncoder passwordEncoder) {
        this.notificationUrl = notificationUrl;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.restTemplate = restTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUsers(final List<UserCreationDTO> users) {
        List<User> entities = users
                .stream()
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    return userConverter.convertUserCreationDTOToUser(user);
                })
                .collect(Collectors.toList());
        saveAllAndFlush(entities);
        List<UserRegularDTO> userRegularDTOList = entities
                .stream()
                .map(userConverter::convertUserToUserRegularDto)
                .collect(Collectors.toList());
        restTemplate.postForObject(notificationUrl, userRegularDTOList, String.class);
    }

    public List<UserRegularDTO> getUsersById(final List<Long> usersIds) {
        return userRepository.findAllById(usersIds)
                .stream()
                .map(userConverter::convertUserToUserRegularDto)
                .collect(Collectors.toList());
    }

    public Page<UserRegularDTO> getUsers(int page, int size) {
        Page<User> users = userRepository.findAll(PageRequest.of(page, size));
        return users.map(userConverter::convertUserToUserRegularDto);
    }

    public void updateUsers(List<UserRegularDTO> users) {
        List<User> entities = users
                .stream()
                .map(userConverter::convertUserRegularDTOToUser)
                .collect(Collectors.toList());
        userRepository.saveAll(entities);
    }

    public void deleteUsers(final List<Long> usersIds) {
        userRepository.softDeleteAllById(usersIds);
    }

    private List<User> saveAllAndFlush(final List<User> users) {
        List<User> result = userRepository.saveAll(users);
        userRepository.flush();
        return result;
    }

}
