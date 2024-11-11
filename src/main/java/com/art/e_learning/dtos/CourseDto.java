package com.art.e_learning.dtos;

import com.art.e_learning.models.Author;
import com.art.e_learning.models.Course;
import com.art.e_learning.models.Section;

import java.util.ArrayList;
import java.util.List;

public record CourseDto(Integer id, String title, String description, List<Integer> authors, List<Integer> sections) {
    public static CourseDto toResponse(Course course){
        return new CourseDto(course.getId(), course.getTitle(), course.getDescription(),
                course.getAuthors() != null ? course.getAuthors().stream().map(Author::getId).toList() : new ArrayList<>(),
                course.getSections() != null ? course.getSections().stream().map(Section::getId).toList(): new ArrayList<>());
    }

    public static List<CourseDto> toListResponse(List<Course> courses){
        return courses.stream().map(CourseDto::toResponse).toList();
    }
}
