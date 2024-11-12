package com.art.e_learning.controllers;

import com.art.e_learning.dtos.AuthorDto;
import com.art.e_learning.models.Author;
import com.art.e_learning.services.interfaces.IAuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final IAuthorService service;

    public AuthorController(IAuthorService authorService) {
        this.service = authorService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        Map<String, Object> response = new HashMap<>();
        response.put("Authors", this.service.getAll());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        AuthorDto findAuthor = this.service.getById(id);
        Map<String, Object> response = new HashMap<>();
        HttpStatus status;

        if (findAuthor == null) {
            response.put("Error", "Author with id " + id + " was not found");
            status = HttpStatus.NOT_FOUND;
        } else {
            response.put("Author", findAuthor);
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/first-name/{firstName}")
    public ResponseEntity<Map<String, Object>> getByName(@PathVariable String firstName) {
        AuthorDto findAuthor = this.service.getByName(firstName);
        Map<String, Object> response = new HashMap<>();
        HttpStatus status;

        if (findAuthor == null) {
            response.put("Error", "Author with name " + firstName + " was not found");
            status = HttpStatus.NOT_FOUND;
        } else {
            response.put("Author", findAuthor);
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Author author) {
        AuthorDto newAuthor = this.service.create(author);
        Map<String, Object> response = new HashMap<>();
        HttpStatus status;
        if (newAuthor.email().equals("repeated email")) {
            response.put("Error", "Email " + author.getEmail() + " already in use");
            status = HttpStatus.BAD_REQUEST;
        } else {
            response.put("Success", "Author created");
            response.put("Author", newAuthor);
            status = HttpStatus.CREATED;
        }
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        boolean isDeleted = this.service.delete(id);
        Map<String, Object> response = new HashMap<>();
        HttpStatus status;

        if (!isDeleted) {
            response.put("Error", "Author with id " + id + " was not found");
            status = HttpStatus.NOT_FOUND;
        } else {
            response.put("Success", "Author with id " + id + " was deleted");
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(response);
    }
}
