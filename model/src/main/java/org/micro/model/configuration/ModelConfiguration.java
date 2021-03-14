package org.micro.model.configuration;

import org.micro.model.converter.UserConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public UserConverter userConverter() {
        return new UserConverter(modelMapper());
    }

}
