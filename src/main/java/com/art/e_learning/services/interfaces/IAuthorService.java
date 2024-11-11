package com.art.e_learning.services.interfaces;

import com.art.e_learning.dtos.AuthorDto;
import com.art.e_learning.models.Author;

import java.util.List;

public interface IAuthorService {
    List<AuthorDto> getAll();
    AuthorDto getById(Integer id);
    AuthorDto getByName(String name);
    AuthorDto create(Author author);
    boolean delete(Integer id);
}
