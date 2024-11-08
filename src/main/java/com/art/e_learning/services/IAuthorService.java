package com.art.e_learning.services;

import com.art.e_learning.models.Author;

import java.util.List;

public interface IAuthorService {
    List<Author> all();
    Author getById(Integer id);
    Author create(Author author);
    void delete(Integer id);
}
