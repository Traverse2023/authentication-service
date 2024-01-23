package com.authenticationservice.server.author;


import com.authenticationservice.server.author.models.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public Author createAuthor(Author author) {
        return authorRepository.insert(author);
    }

    public Optional<Author> getAuthorById(String id) {
        return authorRepository.findById(id);
    }

    public Optional<Author> getAuthorByEmail(String email) {
        return Optional.ofNullable(authorRepository.findByEmail(email));
    }
}
