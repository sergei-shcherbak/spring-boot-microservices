package org.micro.user.controller;

import org.micro.model.UserCreationDTO;
import org.micro.model.UserRegularDTO;
import org.micro.model.Response;
import org.micro.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersController {

    public static final String USER_LIST_MESSAGE = "The input user list cannot be empty or more than 100 records.";
    public static final String USER_IDS_LIST_MESSAGE = "The user ids list cannot be empty.";
    public static final String USER_ID_MESSAGE = "The user id should be a positive number.";
    public static final String PAGE_MESSAGE = "The page should be a positive number or zero.";
    public static final String SIZE_MESSAGE = "The size should be a positive number.";
    public static final String ADD_RESPONSE = "Users have been created.";
    public static final String UPDATE_RESPONSE = "Users have been updated.";
    public static final String DELETE_RESPONSE = "Users are deleted.";
    public static final int LIST_MIN_SIZE = 1;
    public static final int LIST_MAX_SIZE = 100;

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Response> addUsers(@Size(min = LIST_MIN_SIZE, max = LIST_MAX_SIZE, message = USER_LIST_MESSAGE)
                                             @RequestBody final List<@Valid UserCreationDTO> users) {
        userService.addUsers(users);
        return ResponseEntity.ok(new Response(ADD_RESPONSE));
    }

    @GetMapping
    public ResponseEntity<List<UserRegularDTO>> getUsersById(@NotEmpty(message = USER_IDS_LIST_MESSAGE) @RequestParam(value = "ids")
                                                                 final List<@Positive(message = USER_ID_MESSAGE) Long> usersIds) {
        return ResponseEntity.ok(userService.getUsersById(usersIds));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<UserRegularDTO>> getUsers(@PositiveOrZero(message = PAGE_MESSAGE) @RequestParam(defaultValue = "0") int page,
                                                         @Positive(message = SIZE_MESSAGE) @RequestParam(defaultValue = "100") int size) {
        return ResponseEntity.ok(userService.getUsers(page, size));
    }


    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Response> updateUsers(@Size(min = LIST_MIN_SIZE, max = LIST_MAX_SIZE, message = USER_LIST_MESSAGE)
                                                @RequestBody final List<@Valid UserRegularDTO> users) {
        userService.updateUsers(users);
        return ResponseEntity.ok(new Response(UPDATE_RESPONSE));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Response> deleteUsers(@Size(min = LIST_MIN_SIZE, max = LIST_MAX_SIZE, message = USER_LIST_MESSAGE)
                                                    @RequestParam(value = "id") final List<@Positive(message = USER_ID_MESSAGE) Long> usersIds) {
        userService.deleteUsers(usersIds);
        return ResponseEntity.ok(new Response(DELETE_RESPONSE));
    }

}
