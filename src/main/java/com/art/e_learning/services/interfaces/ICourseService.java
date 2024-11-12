package com.art.e_learning.services.interfaces;

import com.art.e_learning.dtos.CourseDto;
import com.art.e_learning.models.Course;

import java.util.List;

public interface ICourseService {
    List<CourseDto> getAll();

    CourseDto getById(Integer id);

    CourseDto create(CourseDto course);

    boolean delete(Integer id);
}
