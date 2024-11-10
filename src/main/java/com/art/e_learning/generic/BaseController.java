package com.art.e_learning.generic;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseController<T> {

    private final BaseService<T> service;
    private final String nameClass;

    public BaseController(BaseService<T> service, String nameClass){
        this.service = service;
        this.nameClass = nameClass;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll(){
        Map<String, Object> response = new HashMap<>();
        response.put(this.nameClass + "s", this.service.getAll());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id){
        T findEntity = this.service.getById(id);
        Map<String, Object> response = new HashMap<>();
        HttpStatus status;

        if(findEntity == null){
            response.put("Error", this.nameClass + " with id " + id + " was not found");
            status = HttpStatus.NOT_FOUND;
        }else{
            response.put(this.nameClass, findEntity);
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody T entity){
        T createdEntity =  this.service.create(entity);
        Map<String, Object> response = new HashMap<>();

        response.put("Success",  this.nameClass + " created");
        response.put("Author", createdEntity);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteById(@PathVariable Integer id){
        boolean isDeleted = this.service.delete(id);
        Map<String, Object> response = new HashMap<>();
        HttpStatus status;

        if(!isDeleted){
            response.put("Error", this.nameClass + " with id " + id + " was not found");
            status = HttpStatus.NOT_FOUND;
        }else{
            response.put("Success", this.nameClass + " with id " + id + " was deleted");
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(response);
    }

}
