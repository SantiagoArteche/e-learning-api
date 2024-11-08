package com.art.e_learning.models;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private int age;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(insertable = false)
    private LocalDateTime lastModified;

    public Author(String firstName, String lastName, String email, int age){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }
}
