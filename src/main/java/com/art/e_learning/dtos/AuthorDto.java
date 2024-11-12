package com.art.e_learning.dtos;

import com.art.e_learning.models.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AuthorDto(
        Integer id,
        @NotBlank(message = "First Name is required")
        String firstName,
        @NotBlank(message = "Last Name is required")
        String lastName,
        @NotBlank(message = "Email is required")
        String email,
        @NotNull(message = "Age is required")
        int age
) {
    public static AuthorDto toResponse(Author author){
        return new AuthorDto(author.getId(), author.getFirstName(), author.getLastName(), author.getEmail(), author.getAge());
    }

    public static List<AuthorDto> toListResponse(List<Author> authors){
        return authors.stream().map(AuthorDto::toResponse).toList();
    }
}
