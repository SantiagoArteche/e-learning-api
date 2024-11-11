package com.art.e_learning.controllers;


import com.art.e_learning.models.Lecture;

import com.art.e_learning.services.interfaces.ILectureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lectures")
public class LectureController {

    private final ILectureService lectureService;

    public LectureController(ILectureService lectureService){
        this.lectureService = lectureService;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<Lecture>>> getAll(){
        Map<String, List<Lecture>> response = new HashMap<>();
        response.put("Lectures", this.lectureService.getAll());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String , Object>> getById(@PathVariable Integer id){
        Map<String, Object> response = new HashMap<>();
        Lecture findLecture = this.lectureService.getById(id);
        HttpStatus status;

        if(findLecture == null){
            response.put("Error", "Lecture with id " + id + " was not found");
            status = HttpStatus.NOT_FOUND;
        }else{
            response.put("Lecture", findLecture);
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Lecture lecture){
        Lecture newLecture = this.lectureService.create(lecture);
        Map<String, Object> response = new HashMap<>();

        response.put("Success", "Lecture created");
        response.put("Lecture", newLecture);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Integer id){
        boolean isDeleted = this.lectureService.delete(id);
        Map<String, String> response = new HashMap<>();
        HttpStatus status;
        if(!isDeleted){
            response.put("Error", "Lecture with id " + id + " was not found");
            status = HttpStatus.NOT_FOUND;
        }else{
            response.put("Success", "Lecture with id " + id + " was deleted");
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(response);
    }
}
