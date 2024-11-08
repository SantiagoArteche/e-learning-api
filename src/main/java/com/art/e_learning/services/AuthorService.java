package com.art.e_learning.services;

import com.art.e_learning.models.Author;
import com.art.e_learning.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService implements IAuthorService{

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> all() {
        return this.authorRepository.findAll();
    }

    @Override
    public Author getById(Integer id) {
        return this.authorRepository.findById(id).orElse(null);
    }

    @Override
    public Author create(Author author) {
        return this.authorRepository.save(author);
    }

    @Override
    public void delete(Integer id) {
        this.authorRepository.deleteById(id);
    }
}
