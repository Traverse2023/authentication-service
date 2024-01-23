package com.authenticationservice.server.author.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Author {
    @Id
    private String id;

    @NotNull(message = "First name cannot be empty")
    private String firstName;

    @NotNull(message = "Last name cannot be empty")
    private String lastName;

    @NotNull(message = "Email cannot be empty")
    private String email;

    @Getter
    @NotNull(message = "Email cannot be empty")
    private String password;

}