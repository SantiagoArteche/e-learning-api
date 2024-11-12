package com.art.e_learning.models;

import com.art.e_learning.generic.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Course extends BaseEntity {
    @NotBlank(message = "Title is required")
    private String title;


    private String description;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "authors_courses", joinColumns = {
            @JoinColumn(name = "course_id")
    },
    inverseJoinColumns = {
            @JoinColumn(name = "author_id")
    }
    )
    @JsonManagedReference
    private List<Author> authors;

    @OneToMany(mappedBy = "course")
    @JsonBackReference
    @JsonIgnore
    private List<Section> sections;
}
