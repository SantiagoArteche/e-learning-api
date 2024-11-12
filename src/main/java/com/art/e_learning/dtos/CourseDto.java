package com.art.e_learning.dtos;

import com.art.e_learning.models.Author;
import com.art.e_learning.models.Course;
import com.art.e_learning.models.Section;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public record CourseDto(Integer id,
                        @NotBlank(message = "Title is required")
                        String title,
                        @NotBlank(message = "Description is required")
                        String description,
                        List<Integer> authors
) {
    public static CourseDto toResponse(Course course){
        return new CourseDto(course.getId(), course.getTitle(), course.getDescription(),
                course.getAuthors() != null ? course.getAuthors().stream().map(Author::getId).toList() : new ArrayList<>());
    }

    public static List<CourseDto> toListResponse(List<Course> courses){
        return courses.stream().map(CourseDto::toResponse).toList();
    }
}
