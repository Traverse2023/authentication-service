package com.authenticationservice.server.author;

import com.authenticationservice.server.author.models.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Author findByEmail(String email);

}
