package com.art.e_learning.dtos;

import com.art.e_learning.models.Author;
import com.art.e_learning.models.Course;

import java.util.ArrayList;
import java.util.List;

public record AuthorDto(Integer id, String firstName, String lastName, String email, int age, List<Integer> coursesId) {
    public static AuthorDto toResponse(Author author){
        return new AuthorDto(author.getId(), author.getFirstName(), author.getLastName(), author.getEmail(), author.getAge(),
                author.getCourses() != null ? author.getCourses().stream().map(Course::getId).toList() : new ArrayList<>());
    }

    public static List<AuthorDto> toListResponse(List<Author> authors){
        return authors.stream().map(AuthorDto::toResponse).toList();
    }
}
