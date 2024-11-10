package com.art.e_learning.services;

import com.art.e_learning.models.Author;
import com.art.e_learning.repositories.AuthorRepository;
import com.art.e_learning.generic.BaseService;
import org.springframework.stereotype.Service;


@Service
public class AuthorService extends BaseService<Author> implements IAuthorService {

    private final AuthorRepository repository;

    public AuthorService(AuthorRepository authorRepository){
        super(authorRepository);
        this.repository = authorRepository;
    }

    @Override
    public Author create(Author author) {
        Author findByEmail = this.repository.findByEmail(author.getEmail()).orElse(null);

        if(findByEmail == null) return this.repository.save(author);

        return new Author(null, null, "repeated email", 35);
    }

    public Author getByName(String firstName) {
        return this.repository.findByFirstName(firstName).orElse(null);
    }
}
