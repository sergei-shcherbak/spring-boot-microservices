package org.micro.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class UserCreationDTO {

    @NotBlank(message = "Name is mandatory")
    @ToString.Include
    private String name;

    @Size(min = 10, max = 200, message
            = "Password must be between 10 and 200 characters")
    private String password;

    @Email(message = "Email should be valid")
    private String email;

}
