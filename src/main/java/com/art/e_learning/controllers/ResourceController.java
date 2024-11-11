package com.art.e_learning.controllers;


import com.art.e_learning.models.Resource;
import com.art.e_learning.services.interfaces.IResourceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    private final IResourceService resourceService;

    public ResourceController(IResourceService resourceService){
        this.resourceService = resourceService;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<Resource>>> getAll(){
        Map<String, List<Resource>> response = new HashMap<>();
        response.put("Resources", this.resourceService.getAll());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id){
        Resource findResource = this.resourceService.getById(id);
        Map<String, Object> response = new HashMap<>();
        HttpStatus status;

        if(findResource == null){
            response.put("Error", "Resource with id " + id + " was not found");
            status = HttpStatus.NOT_FOUND;
        }else {
            response.put("Resource", findResource);
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Resource resource){
        Resource newResource = this.resourceService.create(resource);
        Map<String, Object> response = new HashMap<>();
        response.put("Success", "Resource created");
        response.put("Resource", newResource);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Integer id){
        boolean isDeleted = this.resourceService.delete(id);
        Map<String, String> response = new HashMap<>();
        HttpStatus status;

        if(!isDeleted){
            response.put("Error", "Resource with id " + id + " was not found");
            status = HttpStatus.NOT_FOUND;
        }else {
            response.put("Success", "Resource with id " + id + " was deleted");
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(response);
    }
}
