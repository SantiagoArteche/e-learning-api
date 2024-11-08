package com.art.e_learning.controllers;


import com.art.e_learning.models.Author;
import com.art.e_learning.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll(){
        Map<String, Object> response = new HashMap<>();
        response.put("Authors", this.authorService.all());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id){
        Author findAuthor = this.authorService.getById(id);
        Map<String, Object> response = new HashMap<>();
        HttpStatus status;

        if(findAuthor == null){
            response.put("Error", "Author with id " + id + " was not found");
            status = HttpStatus.NOT_FOUND;
        }else{
            response.put("Author", findAuthor);
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Author author){
        Author newAuthor = new Author(author.getFirstName(), author.getLastName(), author.getEmail(), author.getAge());
        Map<String, Object> response = new HashMap<>();

        newAuthor = this.authorService.create(newAuthor);

        response.put("Success", "Author created");
        response.put("Author", newAuthor);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteById(@PathVariable Integer id){
        Author findAuthor = this.authorService.getById(id);
        Map<String, Object> response = new HashMap<>();
        HttpStatus status;

        if(findAuthor == null){
            response.put("Error", "Author with id " + id + " was not found");
            status = HttpStatus.NOT_FOUND;
        }else{
            this.authorService.delete(findAuthor.getId());
            response.put("Success", "Author with id " + id + " was deleted");
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(response);
    }
}
