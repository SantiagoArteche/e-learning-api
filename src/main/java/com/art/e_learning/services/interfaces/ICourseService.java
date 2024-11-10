package com.art.e_learning.services.interfaces;

import com.art.e_learning.models.Course;

import java.util.List;

public interface ICourseService {
    List<Course> getAll();

    Course getById(Integer id);

    Course create(Course course);

    boolean delete(Integer id);
}
