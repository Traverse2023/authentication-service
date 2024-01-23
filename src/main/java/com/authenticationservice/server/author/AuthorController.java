package com.authenticationservice.server.author;

import com.authenticationservice.server.author.models.Author;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("api/v1/author")
public class AuthorController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);


    @Autowired
    private AuthorService authorService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createAuthor(@RequestBody String requestBody) {
        logger.info("Invoking /api/v1/author/create");
        try {
            JSONObject jsonBody = new JSONObject(requestBody);
            String email = jsonBody.getString("email");
            Optional<Author> foundAuthor = authorService.getAuthorByEmail(email);
            if (foundAuthor.isPresent()) {
                logger.error("User "+ email + " already exists");
                Map<String, Object> response = Map.of(
                        "error", "User Error",
                        "message", "User already exists",
                        "author", email
                );
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(response);
            }
            Author author = new Author();
            author.setEmail(jsonBody.getString("email"));
            author.setFirstName(jsonBody.getString("firstName"));
            author.setLastName(jsonBody.getString("lastName"));
            author.setPassword(jsonBody.getString("password"));
            Optional<Author> createdAuthor = Optional.ofNullable(authorService.createAuthor(author));
            if (createdAuthor.isPresent()) {
                logger.info("Registration successful, user email: " + email);
                Map<String, Object> response = Map.of(
                        "success", true,
                        "message", "User created",
                        "author", email
                );
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
            logger.error("DB Problem, user email: " + email);
            Map<String, Object> response = Map.of(
                    "error", "Internal Server Error",
                    "message", "Problem with Saving to DB",
                    "author", email
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (JSONException e) {
            logger.error(e.getMessage());
            Map<String, Object> response = Map.of(
                    "error", "Bad Request",
                    "message", "Check your " + incorrectFields(e.getMessage()) + " field."
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    private String incorrectFields(String input) {
        int startIndex = input.indexOf("[");
        if (startIndex != -1) {
            // Find the index of the closing bracket starting from the position after the opening bracket
            int endIndex = input.indexOf("]", startIndex + 1);

            if (endIndex != -1) {
                // Extract the substring between the brackets
                String result = input.substring(startIndex + 1, endIndex);
                System.out.println("Substring between brackets: " + result);
                return result;
            } else {
                System.out.println("Closing bracket not found.");
            }
        } else {
            System.out.println("Opening bracket not found.");
        }
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody String requestBody) {
        logger.info("Invoking /api/v1/author/login");
        try {
            JSONObject jsonBody = new JSONObject(requestBody);
            String email = jsonBody.getString("email");
            String password = jsonBody.getString("password");
            Optional<Author> author = authorService.getAuthorByEmail(email);
            if (author.isPresent()) {
                Author a = author.get();
                if (password.equals(a.getPassword())) {
                    logger.info("Login successful");
                    Map<String, Object> response = Map.of(
                            "success", true,
                            "message", "Login successful",
                            "author", a
                    );
                    return ResponseEntity.ok(response);
                } else {
                    logger.error("Incorrect password");
                    Map<String, Object> response = Map.of(
                            "success", false,
                            "message", "Incorrect password"
                    );
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                }
            }
            logger.error("Author not found");
            Map<String, Object> response = Map.of(
                    "success", false,
                    "message", "Author not found"
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (JSONException e) {
            logger.error(e.getMessage());
            Map<String, Object> response = Map.of(
                    "error", "Bad Request",
                    "message", "Check your " + incorrectFields(e.getMessage()) + " field."
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
