package com.art.e_learning.generic;

import com.art.e_learning.dtos.InheritedBaseResourceDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseResourceController<T> {

    private final BaseResourceService<T> service;
    private final String nameClass;

    public BaseResourceController(BaseResourceService<T> service, String nameClass){
        this.service = service;
        this.nameClass = nameClass;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll(){
        Map<String, Object> response = new HashMap<>();

        response.put(this.nameClass + "s", this.service.getAll(this.nameClass));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id){
        Map<String, Object> response = new HashMap<>();
        HttpStatus status;
        InheritedBaseResourceDto findById = this.service.getById(id, this.nameClass);

        if(findById == null){
            response.put("Error", this.nameClass + " with id " + id + " was not found");
            status = HttpStatus.NOT_FOUND;
        }else{
            response.put(this.nameClass, findById);
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody InheritedBaseResourceDto entityDto){
        Map<String, Object> response = new HashMap<>();
        InheritedBaseResourceDto newEntity = this.service.create(entityDto, this.nameClass);
        HttpStatus status;

        if(newEntity.id() == -1){
            response.put("Error",  "Lecture with id " + entityDto.lectureId() + " was not found");
            status = HttpStatus.NOT_FOUND;
        }else{
            response.put("Success",  this.nameClass + " created");
            response.put(this.nameClass, newEntity);
            status = HttpStatus.CREATED;
        }


        return ResponseEntity.status(status).body(response);
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
