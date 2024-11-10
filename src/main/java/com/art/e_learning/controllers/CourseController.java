package com.art.e_learning.controllers;

import com.art.e_learning.models.Author;
import com.art.e_learning.models.Course;
import com.art.e_learning.services.AuthorService;
import com.art.e_learning.services.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll(){
        Map<String, Object> response = new HashMap<>();
        response.put("Courses", this.courseService.getAll());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id){
        Course findCourse = this.courseService.getById(id);
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
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody Course course){
        Course newCourse =  this.courseService.create(course);
        Map<String, Object> response = new HashMap<>();
        HttpStatus status;

        response.put("Success", "Course created");
        response.put("Course", newCourse);
        status = HttpStatus.OK;

        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteById(@PathVariable Integer id){
        boolean isDeleted = this.courseService.delete(id);
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
