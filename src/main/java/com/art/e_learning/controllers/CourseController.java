package com.art.e_learning.controllers;

import com.art.e_learning.generic.BaseController;
import com.art.e_learning.generic.BaseService;
import com.art.e_learning.models.Course;
import com.art.e_learning.services.CourseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
public class CourseController extends BaseController<Course> {

     private BaseService<Course> service;

    public CourseController(BaseService<Course> service) {
        super(service, "Course");
    }
}
