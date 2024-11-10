package com.art.e_learning.services.interfaces;

import com.art.e_learning.models.Author;

import java.util.List;

public interface IAuthorService {
    List<Author> getAll();
    Author getById(Integer id);
    Author getByName(String name);
    Author create(Author author);
    boolean delete(Integer id);
}
