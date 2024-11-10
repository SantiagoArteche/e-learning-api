package com.art.e_learning.services;

import com.art.e_learning.models.Author;
import com.art.e_learning.repositories.AuthorRepository;
import com.art.e_learning.services.interfaces.IAuthorService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthorService implements IAuthorService {

    private final AuthorRepository repository;

    public AuthorService(AuthorRepository authorRepository){
        this.repository = authorRepository;
    }

    @Override
    public List<Author> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Author getById(Integer id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Author getByName(String firstName) {
        return this.repository.findByFirstName(firstName).orElse(null);
    }

    @Override
    public Author create(Author author) {
        Author findByEmail = this.repository.findByEmail(author.getEmail()).orElse(null);

        if(findByEmail == null) return this.repository.save(author);

        return new Author(null, null, "repeated email", 35);
    }


    @Override
    public boolean delete(Integer id) {
        if(getById(id) == null) return false;

        this.repository.deleteById(id);

        return true;
    }




}
