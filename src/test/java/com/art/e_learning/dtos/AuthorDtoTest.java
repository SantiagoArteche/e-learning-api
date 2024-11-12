package com.art.e_learning.dtos;

import com.art.e_learning.models.Author;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class AuthorDtoTest {

    private AuthorDto authorDto;
    private Author author = new Author("TestFn", "TestLn", "test@gmail.com", 27);


    @Test
    void toResponse(){
        this.authorDto = AuthorDto.toResponse(this.author);

        assertEquals(authorDto.age(), author.getAge());
        assertEquals(authorDto.firstName(), author.getFirstName());
        assertEquals(authorDto.lastName(), author.getLastName());
        assertEquals(authorDto.email(), author.getEmail());
    }

    @Test
    void toListResponse(){
        List<Author> authors = new ArrayList<>();
        authors.add(this.author);
        List<AuthorDto> authorDtos = AuthorDto.toListResponse(authors);

        assertEquals(authorDtos.size(), authors.size());
        assertInstanceOf(AuthorDto.class, authorDtos.getFirst());
        assertEquals(authorDtos.getFirst().firstName(), authors.getFirst().getFirstName());
        assertEquals(authorDtos.getFirst().lastName(), authors.getFirst().getLastName());
        assertEquals(authorDtos.getFirst().age(), authors.getFirst().getAge());
        assertEquals(authorDtos.getFirst().email(), authors.getFirst().getEmail());
    }
}
