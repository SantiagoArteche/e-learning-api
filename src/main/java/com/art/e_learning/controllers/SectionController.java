package com.art.e_learning.controllers;


import com.art.e_learning.models.Section;
import com.art.e_learning.services.interfaces.ISectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sections")
public class SectionController {

    private final ISectionService service;

    public SectionController(ISectionService sectionService){
        this.service = sectionService;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<Section>>> getAll(){
        Map<String, List<Section>> response = new HashMap<>();
        response.put("Sections", this.service.getAll());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id){
        Section findSection = this.service.getById(id);
        Map<String, Object> response = new HashMap<>();
        HttpStatus status;

        if(findSection == null){
            response.put("Error", "Section with id " + id + " was not found");
            status = HttpStatus.NOT_FOUND;
        }else{
            response.put("Section", findSection);
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Section section){
        Section newSection = this.service.create(section);
        Map<String, Object> response = new HashMap<>();
        response.put("Success", "Section was created");
        response.put("Section", newSection);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Integer id){
        boolean isDeleted = this.service.delete(id);
        Map<String, String> response = new HashMap<>();
        HttpStatus status;
        if(!isDeleted){
            response.put("Error", "Section with id " + id + " was not found");
            status = HttpStatus.NOT_FOUND;
        }else{
            response.put("Success", "Section with id " + id + " was deleted");
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(response);
    }
}
