package com.art.e_learning.models;

import com.art.e_learning.generic.BaseEntity;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Author extends BaseEntity {
    @NotBlank(message = "First Name is required")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private int age;

    @ManyToMany(mappedBy = "authors")
    private List<Course> courses;

    public Author(String firstName, String lastName, String email, int age){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }
}
