package com.art.e_learning.services;

import com.art.e_learning.dtos.CourseDto;
import static com.art.e_learning.dtos.CourseDto.*;
import com.art.e_learning.models.Course;
import com.art.e_learning.repositories.CourseRepository;
import com.art.e_learning.services.interfaces.ICourseService;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CourseService implements ICourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CourseDto> getAll() {
        return toListResponse(this.repository.findAll());
    }

    @Override
    public CourseDto getById(Integer id) {
        Course findCourse = this.repository.findById(id).orElse(null);

        if(findCourse == null) return null;

        return toResponse(findCourse);
    }

    @Override
    public CourseDto create(Course course) {
        return toResponse(this.repository.save(course));
    }

    @Override
    public boolean delete(Integer id) {
        if(getById(id) == null) return false;

        this.repository.deleteById(id);

        return true;
    }
}
