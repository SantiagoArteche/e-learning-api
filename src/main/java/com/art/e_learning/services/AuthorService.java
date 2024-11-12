package com.art.e_learning.services;

import com.art.e_learning.dtos.AuthorDto;
import com.art.e_learning.models.Author;
import com.art.e_learning.repositories.AuthorRepository;
import com.art.e_learning.services.interfaces.IAuthorService;
import org.springframework.stereotype.Service;
import static com.art.e_learning.dtos.AuthorDto.*;
import java.util.List;

@Service
public class AuthorService implements IAuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDto> getAll() {
        return toListResponse(this.authorRepository.findAll());
    }

    @Override
    public AuthorDto getById(Integer id) {
        Author findAuthor = this.authorRepository.findById(id).orElse(null);

        if(findAuthor == null) return null;

        return toResponse(findAuthor);
    }

    @Override
    public AuthorDto getByName(String firstName) {
        Author findAuthor = this.authorRepository.findByFirstName(firstName).orElse(null);

        if(findAuthor == null) return null;

        return toResponse(findAuthor);
    }

    @Override
    public AuthorDto create(AuthorDto authorDto) {
        Author findByEmail = this.authorRepository.findByEmail(authorDto.email()).orElse(null);
        Author newAuthor = new Author(authorDto.firstName(), authorDto.lastName(), authorDto.email(), authorDto.age());

        if(findByEmail == null) return toResponse(this.authorRepository.save(newAuthor));

        return toResponse(new Author(null, null, "repeated email", 0));
    }


    @Override
    public boolean delete(Integer id) {
        if(getById(id) == null) return false;

        this.authorRepository.deleteById(id);

        return true;
    }




}
