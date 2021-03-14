package org.micro.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class UserRegularDTO {

    @Positive(message = "The user id can't be a negative number.")
    @ToString.Include
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @ToString.Include
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @ToString.Include
    private Status status = Status.IsActive;

}
