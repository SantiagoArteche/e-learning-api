package com.art.e_learning.controllers;

import com.art.e_learning.dtos.CourseDto;
import com.art.e_learning.models.Course;

import com.art.e_learning.services.interfaces.ICourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final ICourseService service;

    public CourseController(ICourseService courseService){
        this.service = courseService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll(){
        Map<String, Object> response = new HashMap<>();
        response.put("Courses", this.service.getAll());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id){
        CourseDto findCourse = this.service.getById(id);
        Map<String, Object> response = new HashMap<>();
        HttpStatus status;

        if(findCourse == null){
            response.put("Error", "Author with id " + id + " was not found");
            status = HttpStatus.NOT_FOUND;
        }else{
            response.put("Author", findCourse);
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(response);
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CourseDto courseDto){
        CourseDto newCourse =  this.service.create(courseDto);
        Map<String, Object> response = new HashMap<>();
        HttpStatus status;
        if(newCourse.id() == -1){
            response.put("Error", "Author with id/s " + courseDto.authors() + " not found");
            status = HttpStatus.NOT_FOUND;
        }else{
            response.put("Success", "Course created");
            response.put("Course", newCourse);
            status = HttpStatus.CREATED;
        }

        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id){
        boolean isDeleted = this.service.delete(id);
        Map<String, Object> response = new HashMap<>();
        HttpStatus status;

        if(!isDeleted){
            response.put("Error", "Course with id " + id + " was not found");
            status = HttpStatus.NOT_FOUND;
        }else{
            response.put("Success", "Course with id " + id + " was deleted");
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(response);
    }

}
