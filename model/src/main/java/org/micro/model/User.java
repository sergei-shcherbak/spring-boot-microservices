package org.micro.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
    @SequenceGenerator(name = "USERS_SEQ", sequenceName = "SEQUENCE_USERS", initialValue = 200)
    @ToString.Include
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @ToString.Include
    private String name;

    @Size(min = 10, max = 200, message
            = "Password must be between 10 and 200 characters")
    private String password;

    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;

    @ToString.Include
    private Status status = Status.IsActive;

}
