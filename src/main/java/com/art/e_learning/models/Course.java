package com.art.e_learning.models;

import com.art.e_learning.generic.BaseEntity;
import jakarta.persistence.*;
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
    private String title;

    private String description;

    @ManyToMany
    @JoinTable(name = "authors_courses", joinColumns = {
            @JoinColumn(name = "course_id")
    },
    inverseJoinColumns = {
            @JoinColumn(name = "author_id")
    }
    )
    private List<Author> authors;

    @OneToMany(mappedBy = "course")
    private List<Section> sections;
}
