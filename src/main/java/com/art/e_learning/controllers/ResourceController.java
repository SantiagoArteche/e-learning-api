package com.art.e_learning.controllers;

import com.art.e_learning.dtos.ResourceDto;
import com.art.e_learning.models.Resource;
import com.art.e_learning.services.interfaces.IResourceService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Map<String, List<ResourceDto>>> getAll(){
        Map<String, List<ResourceDto>> response = new HashMap<>();
        response.put("Resources", this.resourceService.getAll());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id){
        ResourceDto findResource = this.resourceService.getById(id);
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

}