package com.art.e_learning.services;

import com.art.e_learning.generic.BaseService;
import com.art.e_learning.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService extends BaseService<Course> {

    public CourseService(JpaRepository<Course, Integer> repository) {
        super(repository);
    }

    @Override
    public List<Course> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Course getById(Integer id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Course create(Course course) {
        return this.repository.save(course);
    }

    @Override
    public boolean delete(Integer id) {
        if(getById(id) == null) return false;

        this.repository.deleteById(id);

        return true;
    }
}
