package com.art.e_learning.services;

import com.art.e_learning.dtos.AuthorDto;
import com.art.e_learning.models.Author;
import com.art.e_learning.repositories.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthorServiceTest {

    @InjectMocks
    private AuthorService service;

    @Mock
    private AuthorRepository repository;

    List<Author> authors;
    Author author;

    @BeforeEach
    void set_up(){
        MockitoAnnotations.openMocks(this);
        this.authors = new ArrayList<>();
        this.author = new Author("TestFn", "TestLn", "TestEmail", 11);
        this.author.setId(1);
        this.authors.add(this.author);
    }

    @Test
    void get_all(){
        when(this.repository.findAll()).thenReturn(this.authors);

        List<AuthorDto> findAuthors = this.service.getAll();

        assertEquals(findAuthors.size(), this.authors.size());
        assertEquals(findAuthors.getFirst().id(), this.authors.getFirst().getId());
        assertEquals(findAuthors.getFirst().firstName(), this.authors.getFirst().getFirstName());
        assertEquals(findAuthors.getFirst().lastName(), this.authors.getFirst().getLastName());
        assertEquals(findAuthors.getFirst().email(), this.authors.getFirst().getEmail());
        assertEquals(findAuthors.getFirst().age(), this.authors.getFirst().getAge());

        verify(this.repository, times(1)).findAll();
    }

    @Test
    void get_by_id(){
        when(this.repository.findById(1)).thenReturn(Optional.of(this.author));

        AuthorDto authorDto = this.service.getById(1);

        assertEquals(authorDto.id(), this.author.getId());
        assertEquals(authorDto.firstName(), this.author.getFirstName());
        assertEquals(authorDto.lastName(), this.author.getLastName());
        assertEquals(authorDto.email(), this.author.getEmail());
        assertEquals(authorDto.age(), this.author.getAge());

        verify(this.repository, times(1)).findById(1);
    }

    @Test
    void get_by_id_not_found(){
        when(this.repository.findById(2)).thenReturn(null);


        NullPointerException msg = assertThrows(NullPointerException.class, () -> this.service.getById(2));

        assertTrue(msg.getLocalizedMessage().contains("\"com.art.e_learning.repositories.AuthorRepository.findById(Object)\" is null"));

        verify(this.repository, times(1)).findById(2);
    }

    @Test
    void get_by_name(){
        String firstName = "TestFn";
        when(this.repository.findByFirstName(firstName)).thenReturn(Optional.of(this.author));

        AuthorDto authorDto = this.service.getByName(firstName);

        assertEquals(authorDto.id(), this.author.getId());
        assertEquals(authorDto.firstName(), this.author.getFirstName());
        assertEquals(authorDto.lastName(), this.author.getLastName());
        assertEquals(authorDto.email(), this.author.getEmail());
        assertEquals(authorDto.age(), this.author.getAge());

        verify(this.repository, times(1)).findByFirstName(firstName);
    }

    @Test
    void get_by_name_not_found(){
        when(this.repository.findByFirstName("Not Found Name")).thenReturn(null);

        NullPointerException msg = assertThrows(NullPointerException.class, () -> this.service.getByName("Not Found Name"));

        assertTrue(msg.getLocalizedMessage().contains("\"com.art.e_learning.repositories.AuthorRepository.findByFirstName(String)\" is null"));

        verify(this.repository, times(1)).findByFirstName("Not Found Name");
    }

    @Test
    void create(){
        when(this.repository.findByEmail(this.author.getEmail())).thenReturn(Optional.empty());
        when(this.repository.save(any(Author.class))).thenReturn(this.author);

        AuthorDto result = this.service.create(AuthorDto.toResponse(this.author));

        assertNotNull(result);
        assertEquals(this.author.getId(), result.id());
        assertEquals(this.author.getFirstName(), result.firstName());
        assertEquals(this.author.getLastName(), result.lastName());
        assertEquals(this.author.getEmail(), result.email());
        assertEquals(this.author.getAge(), result.age());

        verify(this.repository, times(1)).findByEmail(this.author.getEmail());
        verify(this.repository, times(1)).save(any(Author.class));
    }

    @Test
    void create_email_already_in_use(){
        when(this.repository.findByEmail(this.author.getEmail())).thenReturn(Optional.ofNullable(this.author));

        AuthorDto result = this.service.create(AuthorDto.toResponse(this.author));

        assertNotNull(result);
        assertEquals(result.email(), "repeated email");

        verify(this.repository, times(1)).findByEmail(this.author.getEmail());
    }

    @Test
    void delete(){
        int idToDelete = 1;
        when(this.repository.findById(idToDelete)).thenReturn(Optional.of(this.author));

        boolean isDeleted = this.service.delete(idToDelete);

        assertTrue(isDeleted);

        verify(this.repository, times(1)).deleteById(idToDelete);
        verify(this.repository, times(1)).findById(idToDelete);
    }

    @Test
    void delete_not_found(){
        int idToDelete = 3;
        when(this.repository.findById(idToDelete)).thenReturn(Optional.empty());

        boolean isDeleted = this.service.delete(idToDelete);

        assertFalse(isDeleted);

        verify(this.repository, times(1)).findById(idToDelete);
    }
}
