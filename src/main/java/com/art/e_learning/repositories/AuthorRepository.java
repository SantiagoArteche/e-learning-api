package com.art.e_learning.repositories;

import com.art.e_learning.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByEmail(String email);
    Optional<Author> findByFirstName(String firstName);
}
