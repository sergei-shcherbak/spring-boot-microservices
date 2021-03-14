package org.micro.model.converter;

import org.micro.model.User;
import org.micro.model.UserCreationDTO;
import org.micro.model.UserRegularDTO;
import org.modelmapper.ModelMapper;

public class UserConverter {

    private final ModelMapper modelMapper;

    public UserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User convertUserCreationDTOToUser(UserCreationDTO user) {
        return modelMapper.map(user,User.class);
    }

    public UserRegularDTO convertUserToUserRegularDto(User user) {
        return modelMapper.map(user,UserRegularDTO.class);
    }

    public User convertUserRegularDTOToUser(UserRegularDTO user) {
        return modelMapper.map(user,User.class);
    }


}
